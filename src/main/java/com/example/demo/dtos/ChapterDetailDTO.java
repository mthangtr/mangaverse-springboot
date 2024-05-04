package com.example.demo.dtos;

import com.example.demo.models.Chapter;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChapterDetailDTO {
    private String mangaTitle;
    private String chapterTitle;

    public ChapterDetailDTO(Optional<Chapter> chapter) {
        this.mangaTitle = chapter.get().getManga().getTitle();
        this.chapterTitle = chapter.get().getTitle();
    }
}
