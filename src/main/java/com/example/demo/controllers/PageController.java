package com.example.demo.controllers;

import com.example.demo.dtos.PageDTO;
import com.example.demo.repositories.PageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/page")
public class PageController {

    private final PageRepository pageRepository;

    public PageController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }


    // http://localhost:8080/api/page/service/page/260
    @GetMapping("/service/page/{chapterId}")
    public List<PageDTO> getPageByChapterId(@PathVariable Integer chapterId) {
        List<PageDTO> pages = pageRepository.findPageDetailsByChapterId(chapterId);
        return pages;
    }

}
