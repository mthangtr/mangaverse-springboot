package com.example.demo.repositories;

import com.example.demo.models.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    @Query("SELECT c FROM Chapter c WHERE c.manga.id = :mangaId ORDER BY c.id DESC")
    List<Chapter> findTop3ByOrderByIdDesc(@Param("mangaId") int mangaId);

    @Query("SELECT c FROM Chapter c WHERE c.manga.id = :mangaId ORDER BY c.id DESC")
    List<Chapter> findByMangaId(@Param("mangaId") int mangaId);

    @Query(value = "SELECT c FROM Chapter c WHERE c.manga.id = :mangaId ORDER BY c.id DESC")
    Page<Chapter> findChaptersByMangaId(@Param("mangaId") int mangaId, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Chapter c WHERE c.manga.id = :mangaId")
    int countChapterByMangaId(int mangaId);

    Chapter findFirstByMangaIdOrderByIdAsc(int mangaId);

}
