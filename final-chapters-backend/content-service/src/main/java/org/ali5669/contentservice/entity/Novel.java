package org.ali5669.contentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "novel")
@Data
public class Novel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "novel_id")
    private Long id;
    
    @Column(name = "author_id")
    private Long authorId;
    
    private String title;
    
    private String summary;
    
    @Column(name = "cover_image")
    private String coverImage;
    
    private String category;
    
    private String tags;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 