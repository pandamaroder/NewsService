package com.example.demo.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /* @Column(name = "created_at", nullable = false, insertable = true, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", insertable = false, updatable = true)
    private LocalDateTime updatedAt;*/
}
