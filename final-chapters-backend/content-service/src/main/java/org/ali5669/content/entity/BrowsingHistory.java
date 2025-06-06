package org.ali5669.content.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "browsinghistory")
public class BrowsingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "novel_id", nullable = false)
    private Long novelId;

    @Column(name = "last_read_time", nullable = false)
    private LocalDateTime lastReadTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id", insertable = false, updatable = false)
    private Novel novel;
} 