package com.project.demo.timeline;

import com.project.demo.timeline.model.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private final Map<String, RoomState> rooms = new ConcurrentHashMap<>();

    private static final int  HAND_SIZE = 2;
    private static final long TURN_MS   = 60_000L;
    private static final long GAME_MS   = 20 * 60_000L;

    private List<Card> buildDeck() {
        return List.of(
                new Card("m1","Imprenta de Gutenberg",1450),
                new Card("m2","Descubrimiento de América",1492),
                new Card("m3","Independencia de EE. UU.",1776),
                new Card("m4","Primera Guerra Mundial",1914),
                new Card("m5","Llegada del Apolo 11 a la Luna",1969),
                new Card("cr1","Terremoto de Cartago",1910),
                new Card("cr2","Fundación de la UCR",1940),
                new Card("cr3","Abolición del Ejército de Costa Rica",1948),
                new Card("cr4","Colón llega a Costa Rica (4.º viaje)",1502),
                new Card("cr5","Anexión del Partido de Nicoya",1824)
        );
    }

    public RoomState createRoom() {
        String id = UUID.randomUUID().toString().substring(0, 6);
        var r = new RoomState(id);
        rooms.put(id, r);
        return r;
    }

    public RoomState get(String id) {
        return rooms.computeIfAbsent(id, RoomState::new);
    }

    private <T> T withLock(RoomState r, java.util.concurrent.Callable<T> body) {
        synchronized (r) {
            try { return body.call(); }
            catch (RuntimeException e) { throw e; }
            catch (Exception e) { throw new RuntimeException(e); }
        }
    }

    private Map<String,Object> snapshotUnsafe(RoomState r){
        Map<String,Object> s = new HashMap<>();
        s.put("roomId", r.id);
        s.put("phase", r.phase);
        s.put("timeline", new ArrayList<>(r.timeline));           // copia
        s.put("turnOrder", new ArrayList<>(r.turnOrder));         // copia
        s.put("currentPlayerId", r.currentPlayerId());
        s.put("turnDurationMs", r.turnDurationMs);
        s.put("gameDurationMs", r.gameDurationMs);
        s.put("turnDeadlineEpochMs", r.turnDeadlineEpochMs);
        s.put("gameDeadlineEpochMs", r.gameDeadlineEpochMs);
        s.put("paused", r.paused);
        s.put("winnerId", r.winnerId);
        s.put("players", r.players.values().stream().map(ps -> Map.of(
                "userId", ps.userId,
                "name", ps.name,
                "hand", new ArrayList<>(ps.hand)                     // copia
        )).toList());
        return s;
    }

    public Map<String,Object> snapshot(RoomState r){
        return withLock(r, () -> snapshotUnsafe(r));
    }


    public Map<String,Object> join(String roomId, Principal p){
        var r = get(roomId);
        return withLock(r, () -> {
            var uid = p.getName();
            r.players.computeIfAbsent(uid, id -> {
                var ps = new PlayerState(id, id);
                if (r.hostId == null) r.hostId = id;
                return ps;
            });
            return snapshotUnsafe(r);
        });
    }

    public Map<String,Object> play(String roomId, Principal p, String cardId, int position){
        var r = get(roomId);
        return withLock(r, () -> {
            if (!"RUNNING".equals(r.phase)) throw new RuntimeException("No iniciado");
            if (!Objects.equals(r.currentPlayerId(), p.getName())) throw new RuntimeException("No es tu turno");

            var player = r.players.get(p.getName());
            var opt = player.hand.stream().filter(c -> c.id().equals(cardId)).findFirst();
            if (opt.isEmpty()) throw new RuntimeException("Carta inválida");
            var card = opt.get();

            var tentative = new ArrayList<>(r.timeline);
            int pos = position;
            if (pos < 0 || pos > tentative.size()) pos = tentative.size();
            tentative.add(pos, card);
            boolean ok = isSorted(tentative);

            if (ok) {
                r.timeline.add(pos, card);
                player.hand.remove(card);
            } else {
                player.hand.remove(card);
                r.discard.add(card);
            }
            if (!r.deck.isEmpty()) player.hand.add(r.deck.pollFirst());

            r.currentTurnIdx = (r.currentTurnIdx + 1) % r.turnOrder.size();
            r.turnDeadlineEpochMs = System.currentTimeMillis() + r.turnDurationMs;

            if (player.hand.isEmpty()) {
                r.phase = "FINISHED";
                r.winnerId = player.userId;
            } else {
                long now = System.currentTimeMillis();
                if (r.gameDeadlineEpochMs > 0 && now >= r.gameDeadlineEpochMs) {
                    decideWinnerByFewestCards(r);
                }
            }

            return Map.of(
                    "correct", ok,
                    "played", Map.of("position", pos),
                    "state", snapshotUnsafe(r)
            );
        });
    }

    public Map<String,Object> start(String roomId, Principal p){
        var r = get(roomId);
        return withLock(r, () -> {
            if (p != null && !Objects.equals(r.hostId, p.getName())) {
                throw new RuntimeException("Solo host");
            }
            if (r.players.size() < 2 || r.players.size() > 4) {
                throw new RuntimeException("Jugadores 2..4");
            }

            var deck = new ArrayList<>(buildDeck());
            Collections.shuffle(deck);
            r.deck.clear();
            deck.forEach(r.deck::add);

            r.timeline.clear();
            r.timeline.add(r.deck.pollFirst());

            r.turnOrder.clear();
            r.players.values().forEach(ps -> {
                r.turnOrder.add(ps.userId);
                ps.hand.clear();
                for (int i = 0; i < HAND_SIZE && !r.deck.isEmpty(); i++) {
                    ps.hand.add(r.deck.pollFirst());
                }
            });

            r.currentTurnIdx = 0;
            r.phase = "RUNNING";
            long now = System.currentTimeMillis();
            r.turnDurationMs = TURN_MS;
            r.gameDurationMs = GAME_MS;
            r.turnDeadlineEpochMs = now + TURN_MS;
            r.gameDeadlineEpochMs = now + GAME_MS;

            return snapshotUnsafe(r);
        });
    }


    public Map<String,Object> timeout(String roomId){
        var r = get(roomId);
        return withLock(r, () -> {
            if (!"RUNNING".equals(r.phase)) return snapshotUnsafe(r);
            long now = System.currentTimeMillis();
            if (r.turnDeadlineEpochMs > 0 && now < r.turnDeadlineEpochMs) return snapshotUnsafe(r); // aún no expira

            r.currentTurnIdx = (r.currentTurnIdx + 1) % r.turnOrder.size();
            r.turnDeadlineEpochMs = now + r.turnDurationMs;

            if (r.gameDeadlineEpochMs > 0 && now >= r.gameDeadlineEpochMs) {
                decideWinnerByFewestCards(r);
            }
            return snapshotUnsafe(r);
        });
    }

    /* ====== Helpers ====== */

    private void decideWinnerByFewestCards(RoomState r){
        var win = r.players.values().stream()
                .min(Comparator.<PlayerState>comparingInt(ps -> ps.hand.size())
                        .thenComparingInt(ps -> r.turnOrder.indexOf(ps.userId)))
                .map(ps -> ps.userId)
                .orElse(null);
        r.phase = "FINISHED";
        r.winnerId = win;
    }

    private boolean isSorted(List<Card> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).year() > list.get(i).year()) return false;
        }
        return true;
    }
}
