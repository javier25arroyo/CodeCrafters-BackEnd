package com.project.demo.rest.game;

import com.project.demo.rest.game.CrosswordStatRequest;
import com.project.demo.rest.game.CrosswordSummaryResponse;
import com.project.demo.service.CrosswordStatService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/stats/crossword")
public class CrosswordStatController {

    private final CrosswordStatService service;

    public CrosswordStatController(CrosswordStatService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CrosswordStatRequest req, Authentication auth) {
        if (req.puzzleId == null || req.difficulty == null || req.startedAt == null || req.finishedAt == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }
        return ResponseEntity.ok(service.save(req, auth));
    }

    @GetMapping("/summary")
    public ResponseEntity<CrosswordSummaryResponse> summary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(service.summary(from, to));
    }
}
