package com.example.demo.repositories;

import com.example.demo.dtos.PageDTO;
import com.example.demo.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {

    @Query("SELECT new com.example.demo.dtos.PageDTO(p.id, p.chapter.id, p.pageNumber, p.url) FROM Page p WHERE p.chapter.id = :chapterId ORDER BY p.pageNumber")
    List<PageDTO> findPageDetailsByChapterId(int chapterId);
}
