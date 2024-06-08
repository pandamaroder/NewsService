package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, insertable = true, updatable = false)
    @ToString.Exclude
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = true)
    @ToString.Exclude
    private LocalDateTime updatedAt;
}
