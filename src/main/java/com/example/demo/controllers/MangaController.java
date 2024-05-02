package com.example.demo.controllers;

import com.example.demo.dtos.ChapterDTO;
import com.example.demo.dtos.MangaDTO;
import com.example.demo.models.Chapter;
import com.example.demo.models.Manga;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.ChapterRepository;
import com.example.demo.repositories.MangaRepository;
import com.example.demo.services.ChapterService;
import com.example.demo.services.MangaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manga")
public class MangaController {

    private final MangaService mangaService;

    private final ChapterService chapterService;
    private final MangaRepository repository;
    private final ChapterRepository chapterRepository;

    public MangaController(MangaRepository repository,
                           MangaService mangaService,
                           ChapterService chapterService,
                           ChapterRepository chapterRepository) {
        this.repository = repository;
        this.mangaService = mangaService;
        this.chapterService = chapterService;
        this.chapterRepository = chapterRepository;
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
        for (MangaDTO manga : mangas) {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
        }
        return ResponseEntity.ok(mangas);
    }

    //Get top 5 most viewed manga descending (for sidebar homepage)
    @GetMapping("/service/sidebar/top12/most-viewed")
    public ResponseEntity<List<MangaDTO>> getTop12MostViewedManga() {
        List<MangaDTO> mangas = mangaService.getTop12MostViewedManga();
        for (MangaDTO manga : mangas) {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
        }
        return ResponseEntity.ok(mangas);
    }

    //Get all mangas with 3 latest chapters (for homepage)
    @GetMapping("service/home/all")
    public ResponseEntity<List<MangaDTO>> getAllMangaWith3LatestChapters() {
        List<MangaDTO> mangas = mangaService.getAllMangaDesc();
        for (MangaDTO manga : mangas) {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
        }
        return ResponseEntity.ok(mangas);
    }

    //Get all manga with categories and chapters
    @GetMapping("/service/all")
    public ResponseEntity<List<MangaDTO>> getDetailedMangas(){
        List<MangaDTO> mangas = mangaService.getAllMangaWithCategories();
        for (MangaDTO manga : mangas) {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
        }
        return ResponseEntity.ok(mangas);
    }

    //Get manga by id with categories and chapters
    // http://localhost:8080/api/manga/service/detail/1
    @GetMapping("/service/detail/{id}")
    public ResponseEntity<MangaDTO> getDetailedMangaById(@PathVariable int id){
        MangaDTO manga = mangaService.getMangaByIdWithCategories(id);
        return ResponseEntity.ok(manga);
    }


    // http://localhost:8080/api/manga/service/detail/1/chapters
    @GetMapping("/service/detail/{id}/chapters")
    public ResponseEntity<List<ChapterDTO>> getChaptersByMangaId(@PathVariable int id,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Chapter> chapters = chapterRepository.findChaptersByMangaId(id, pageable);
        List<ChapterDTO> chapterDTOs = chapters.getContent().stream()
                .map(ChapterDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chapterDTOs);
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

    // http://localhost:8080/api/manga/home?page=1
    @GetMapping("/home")
    public ResponseEntity<List<MangaDTO>> getMangaSortedByLatestChapter(
            @RequestParam(defaultValue = "1") int page
    ) {
        int size = 8;
        page = page - 1;
        List<MangaDTO> mangas = mangaService.getMangaOrderedByLatestChapter(page, size);
        for (MangaDTO manga : mangas) {
            List<ChapterDTO> chapters = chapterService.get3LatestChaptersForManga(manga.getId());
            manga.setChapters(chapters);
        }
        return ResponseEntity.ok(mangas);
    }

}
