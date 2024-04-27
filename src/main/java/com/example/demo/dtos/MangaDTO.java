package com.example.demo.dtos;

import com.example.demo.models.Category;
import com.example.demo.models.Manga;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MangaDTO {
    private int id;
    private String title;
    private String thumbnail;
    private String author;
    private long followerCount;
    private long likeCount;
    private long viewCount;
    private String description;
    private LocalDateTime releaseDate;
    private List<String> categories;
    private List<ChapterDTO> chapters;

    public MangaDTO(Manga manga) {
        this.id = manga.getId();
        this.title = manga.getTitle();
        this.thumbnail = manga.getThumbnail();
        this.author = manga.getAuthor();
        this.followerCount = manga.getFollowerCount();
        this.likeCount = manga.getLikeCount();
        this.viewCount = manga.getViewCount();
        this.description = manga.getDescription();
        this.releaseDate = manga.getReleaseDate();
        if (manga.getCategories() != null) {
            this.categories = manga.getCategories().stream()
                    .map(Category::getTitle)
                    .collect(Collectors.toList());
        } else {
            this.categories = new ArrayList<>();
        }
        if (manga.getChapters() != null) {
            this.chapters = manga.getChapters().stream()
                    .map(ChapterDTO::new)
                    .collect(Collectors.toList());
        } else {
            this.chapters = new ArrayList<>();
        }
    }
}
