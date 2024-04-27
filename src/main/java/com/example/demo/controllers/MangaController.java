package com.example.demo.controllers;

import com.example.demo.dtos.ChapterDTO;
import com.example.demo.dtos.MangaDTO;
import com.example.demo.models.Chapter;
import com.example.demo.models.Manga;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.MangaRepository;
import com.example.demo.services.ChapterService;
import com.example.demo.services.MangaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    private final MangaService mangaService;

    private final ChapterService chapterService;
    private final MangaRepository repository;

    public MangaController(MangaRepository repository, MangaService mangaService, ChapterService chapterService) {
        this.repository = repository;
        this.mangaService = mangaService;
        this.chapterService = chapterService;
    }

    //http://localhost:8080/api/manga/all
    @GetMapping("/all")
    List<MangaDTO> getAllManga() {
        return mangaService.getAllMangaDesc();
    }

    //Get top 5 most liked manga descending (for carousel homepage)
    @GetMapping("/service/home/top12/most-liked")
    public ResponseEntity<List<MangaDTO>> getTop12MostLikedManga() {
        List<MangaDTO> mangas = mangaService.getTop12MostLikedManga();
        mangas.stream().map(manga -> {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
            return manga;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(mangas);
    }

    //Get top 5 most viewed manga descending (for sidebar homepage)
    @GetMapping("/service/sidebar/top12/most-viewed")
    public ResponseEntity<List<MangaDTO>> getTop12MostViewedManga() {
        List<MangaDTO> mangas = mangaService.getTop12MostViewedManga();
        mangas.stream().map(manga -> {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
            return manga;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(mangas);
    }

    //Get all mangas with 3 latest chapters (for homepage)
    @GetMapping("service/home/all")
    public ResponseEntity<List<MangaDTO>> getAllMangaWith3LatestChapters() {
        List<MangaDTO> mangas = mangaService.getAllMangaDesc();
        mangas.stream().map(manga -> {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
            return manga;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(mangas);
    }

    //Get all manga with categories and chapters
    @GetMapping("/service/all")
    public ResponseEntity<List<MangaDTO>> getDetailedMangas(){
        List<MangaDTO> mangas = mangaService.getAllMangaWithCategories();
        mangas.stream().map(manga -> {
            List<ChapterDTO> chapters = chapterService.getChaptersForMangaDesc(manga.getId());
            manga.setChapters(chapters);
            return manga;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(mangas);
    }

    //Get manga by id with categories and chapters
    @GetMapping("/service/detail/{id}")
    public ResponseEntity<MangaDTO> getDetailedMangaById(@PathVariable int id){
        MangaDTO manga = mangaService.getMangaByIdWithCategories(id);
        List<ChapterDTO> chapters = chapterService.getChaptersForMangaDesc(id);
        manga.setChapters(chapters);
        return ResponseEntity.ok(manga);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertManga(@RequestBody Manga newManga) {
        try {
            if(repository.existsByTitle(newManga.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject("400", "Manga already exists", null)
                );
            }
            Manga savedManga = repository.save(newManga);
            return ResponseEntity.ok(new ResponseObject("200", "Manga inserted successfully", savedManga));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("500", "Error inserting manga", null)
            );
        }
    }

    // http://localhost:8080/api/manga/total-manga-number
    @GetMapping("/total-manga-number")
    public ResponseEntity<Integer> getTotalMangaNumber() {
        int totalManga = repository.countTotalManga();
        return ResponseEntity.ok(totalManga);
    }

    // http://localhost:8080/api/manga/sorted-by-latest-chapter?page=1
    @GetMapping("/sorted-by-latest-chapter")
    public ResponseEntity<List<MangaDTO>> getMangaSortedByLatestChapter(
            @RequestParam(defaultValue = "0") int page
    ) {
        int size = 8;
        List<MangaDTO> mangas = mangaService.getMangaOrderedByLatestChapter(page, size);
        mangas.stream().map(manga -> {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
            return manga;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(mangas);
    }

}
