package org.example.simplemq;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="FailedMessages")
@Data
public class MQEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(name="error_message")
    private String error_message;

    @Column(name="processed")
    private boolean processed=false;

    @Column(name="createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();
}
