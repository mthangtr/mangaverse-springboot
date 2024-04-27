package com.example.demo.dtos;

import com.example.demo.models.Chapter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChapterDTO {
    private int id;
    private String title;
    private LocalDateTime releaseDate;

    public ChapterDTO(Chapter chapter){
        this.id = chapter.getId();
        this.title = chapter.getTitle();
        this.releaseDate = chapter.getReleaseDate();
    }

}
