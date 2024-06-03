package com.example.demo.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;



@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment extends BaseEntity{

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @ToString.Exclude
    News news;



}
