package com.example.demo.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDTO {
    private Integer id;
    private Integer pageNumber;
    private String imageUrl;

    public PageDTO(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
