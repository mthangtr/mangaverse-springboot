package com.example.demo.controllers;

import com.example.demo.dtos.PageDTO;
import com.example.demo.dtos.PageInsertDTO;
import com.example.demo.exeptions.CustomException;
import com.example.demo.models.Chapter;
import com.example.demo.models.Page;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.ChapterRepository;
import com.example.demo.repositories.PageRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/page")
public class PageController {

    private final PageRepository pageRepository;
    private final ChapterRepository chapterRepository;

    public PageController(PageRepository pageRepository,
                          ChapterRepository chapterRepository) {
        this.pageRepository = pageRepository;
        this.chapterRepository = chapterRepository;
    }

    //Used
    // http://localhost:8080/api/page/service/page/260
    @GetMapping("/service/page/{chapterId}")
    public List<PageDTO> getPageByChapterId(@PathVariable Integer chapterId) {
        return pageRepository.findPageDetailsByChapterId(chapterId);
    }

    @PostMapping("/service/page/insert")
    @Transactional
    public ResponseEntity<ResponseObject> insertPage(@RequestBody PageInsertDTO PageInsertDTO) {
        try {
            if(PageInsertDTO.getChapterId() == null || PageInsertDTO.getPages() == null){
                throw new CustomException("Chapter id or pages is null", HttpStatus.BAD_REQUEST);
            }
            if(chapterRepository.findById(PageInsertDTO.getChapterId()).isEmpty()){
                throw new CustomException("Chapter id is not exist", HttpStatus.BAD_REQUEST);
            }
            Chapter chapter = chapterRepository.findById(PageInsertDTO.getChapterId()).orElseThrow(() -> new CustomException("Chapter not found", HttpStatus.NOT_FOUND));
            for (PageDTO pageDTO : PageInsertDTO.getPages()) {
                Page page = new Page();
                page.setChapter(chapter);
                page.setPageNumber(pageDTO.getPageNumber());
                page.setUrl(pageDTO.getImageUrl());
                System.out.println(page);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Insert page successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("500", "Insert page unsuccessfully", null));
        }
    }

}
