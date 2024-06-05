package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "categories", schema = "demo")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<News> newsList;
}
