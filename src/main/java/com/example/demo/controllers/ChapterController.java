package com.example.demo.controllers;

import com.example.demo.dtos.ChapterDTO;
import com.example.demo.dtos.ChapterDetailDTO;
import com.example.demo.models.Chapter;
import com.example.demo.repositories.ChapterRepository;
import com.example.demo.services.ChapterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/chapter")
public class ChapterController {

    private final ChapterService chapterService;
    private final ChapterRepository chapterRepository;

    public ChapterController(ChapterService chapterService, ChapterRepository chapterRepository) {
        this.chapterService = chapterService;
        this.chapterRepository = chapterRepository;
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

    // http://localhost:8080/api/manga/total-chapter-number
    @GetMapping("/total-chapter-number/{id}")
    public ResponseEntity<Integer> getTotalChapterByMangaId(@PathVariable int id) {
        int totalChapter = chapterRepository.countChapterByMangaId(id);
        return ResponseEntity.ok(totalChapter);
    }

    @GetMapping("/service/all-chapter/{mangaId}")
    public ResponseEntity<List<ChapterDTO>> getAllChapterByMangaId(@PathVariable int mangaId) {
        List<ChapterDTO> chapterDTOs = chapterService.getChaptersForMangaDesc(mangaId);
        return ResponseEntity.ok(chapterDTOs);
    }

    // http://localhost:8080/api/chapter/service/detail/260
    @GetMapping("/service/detail/{chapterId}")
    public ResponseEntity<ChapterDetailDTO> getChapterDetail(@PathVariable int chapterId) {
        try {
            ChapterDetailDTO chapterDetails = chapterService.getChapterDetails(chapterId);
            return ResponseEntity.ok(chapterDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
