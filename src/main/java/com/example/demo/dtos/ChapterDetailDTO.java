package com.example.demo.dtos;

import com.example.demo.models.Chapter;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChapterDetailDTO {
    private int mangaId;
    private int chapterId;
    private String mangaTitle;
    private String chapterTitle;
    private LocalDateTime releaseDate;

    public ChapterDetailDTO(Optional<Chapter> chapter) {
        this.mangaId = chapter.get().getManga().getId();
        this.chapterId = chapter.get().getId();
        this.mangaTitle = chapter.get().getManga().getTitle();
        this.chapterTitle = chapter.get().getTitle();
        this.releaseDate = chapter.get().getReleaseDate();
    }
}
