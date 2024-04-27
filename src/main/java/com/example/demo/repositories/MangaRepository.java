package com.example.demo.repositories;

import com.example.demo.models.Manga;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Integer> {

    List<Manga> findAllByOrderByIdDesc();

    @EntityGraph(attributePaths = {"categories"})
    List<Manga> findAllBy();


    List<Manga> findTop12ByOrderByLikeCountDesc();

    List<Manga> findTop12ByOrderByViewCountDesc();

    @EntityGraph(attributePaths = {"categories"})
    @Query("SELECT m FROM Manga m WHERE m.id = :id")
    Manga findByIdWithCate(int id);

    @Query("SELECT COUNT(m) > 0 FROM Manga m WHERE m.title LIKE %:title%")
    boolean existsByTitle(String title);

    @Query("SELECT m FROM Manga m JOIN Chapter c ON m.id = c.manga.id GROUP BY m.id ORDER BY MAX(c.releaseDate) DESC")
    Page<Manga> findAllMangaOrderByLatestChapter(Pageable pageable);

    @Query("SELECT COUNT(m) FROM Manga m")
    int countTotalManga();
}
