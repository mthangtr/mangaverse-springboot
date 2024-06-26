package com.example.demo.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDTO {
    Integer id;
    Integer chapterId;
    Integer pageNumber;
    String imageUrl;
}
