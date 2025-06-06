package org.ali5669.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chapter", catalog = "novel")
@Data
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long id;
    
    @Column(name = "novel_id")
    private Long novelId;
    
    private String title;
    
    private String content;
    
    @Column(name = "order")
    private Integer order;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 