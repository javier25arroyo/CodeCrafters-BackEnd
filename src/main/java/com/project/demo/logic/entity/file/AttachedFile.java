package com.project.demo.logic.entity.file;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "attached_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(length = 50)
    private String type;

    private LocalDateTime date;

    @Column(name = "related_entity", length = 50)
    private String relatedEntity;

    @Column(name = "entity_id")
    private Integer entityId;

    

    
}
