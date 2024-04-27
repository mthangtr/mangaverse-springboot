package com.example.demo.services;

import com.example.demo.dtos.MangaDTO;
import com.example.demo.models.Manga;
import com.example.demo.repositories.MangaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;

    public MangaService(MangaRepository mangaRepository) {
        this.mangaRepository = mangaRepository;
    }

    public List<MangaDTO> getAllMangaWithCategories() {
        return mangaRepository.findAllBy().stream()
                .map(MangaDTO::new)
                .collect(Collectors.toList());
    }

    public List<MangaDTO> getAllMangaDesc() {
        List<Manga> mangas = mangaRepository.findAllByOrderByIdDesc();
        return mangas.stream().map(MangaDTO::new)
                .collect(Collectors.toList());
    }

    public List<MangaDTO> getTop12MostLikedManga() {
        List<Manga> mangas = mangaRepository.findTop12ByOrderByLikeCountDesc();
        return mangas.stream().map(MangaDTO::new)
                .collect(Collectors.toList());
    }

    public List<MangaDTO> getTop12MostViewedManga() {
        List<Manga> mangas = mangaRepository.findTop12ByOrderByViewCountDesc();
        return mangas.stream().map(MangaDTO::new)
                .collect(Collectors.toList());
    }

    public MangaDTO getMangaByIdWithCategories(int id) {
        Manga manga = mangaRepository.findByIdWithCate(id);
        System.out.println(manga);
        if (manga == null) {
            return null;
        }
        return new MangaDTO(manga);
    }

    public List<MangaDTO> getMangaOrderedByLatestChapter(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        Page<Manga> mangas = mangaRepository.findAllMangaOrderByLatestChapter(pageable);
        return mangas.stream().map(MangaDTO::new).collect(Collectors.toList());
    }

}
