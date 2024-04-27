package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name ="manga")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "author")
    private String author;

    @Column(name = "follower_count")
    private long followerCount;

    @Column(name = "like_count")
    private long likeCount;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @OneToMany(mappedBy = "manga")
    private List<Chapter> chapters;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "manga_category",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
