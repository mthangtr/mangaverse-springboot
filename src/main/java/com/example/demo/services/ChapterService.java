package com.example.demo.services;

import com.example.demo.dtos.ChapterDTO;
import com.example.demo.dtos.ChapterDetailDTO;
import com.example.demo.models.Chapter;
import com.example.demo.repositories.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public List<ChapterDTO> get3LatestChaptersForManga(int mangaId) {
        List<Chapter> chapters = chapterRepository.findTop3ByOrderByIdDesc(mangaId);
        return chapters.stream().map(ChapterDTO::new)
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<ChapterDTO> getChaptersForMangaDesc(int mangaId) {
        List<Chapter> chapters = chapterRepository.findByMangaId(mangaId);
        return chapters.stream().map(ChapterDTO::new)
                .collect(Collectors.toList());
    }

    public ChapterDetailDTO getChapterDetails(int chapterId) {
       Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        return new ChapterDetailDTO(chapter);
    }

    public ChapterDTO getFirstChapterByMangaId(int mangaId) {
        Chapter chapter = chapterRepository.findFirstByMangaIdOrderByIdAsc(mangaId);
        return new ChapterDTO(chapter);
    }
}
