package com.example.ddmdemo.controller;

import com.example.ddmdemo.service.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;

    @PostMapping("/law")
    @ResponseStatus(HttpStatus.CREATED)
    public void index(@RequestParam("files") List<MultipartFile> files) {
        indexingService.indexLaws(files);
    }

    @PostMapping("/contract")
    @ResponseStatus(HttpStatus.CREATED)
    public void indexContract(@RequestParam("files") List<MultipartFile> files) {
        indexingService.indexContract(files);
    }
}
