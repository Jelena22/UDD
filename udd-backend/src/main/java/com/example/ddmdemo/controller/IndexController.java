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
   // @PreAuthorize("hasAuthority('INDEX_DATA')")
    public void index(@RequestParam("files") List<MultipartFile> files) {
        System.out.println("Usao u index law kontroler");
        indexingService.indexLaws(files);
    }

    @PostMapping("/contract")
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("hasAuthority('INDEX_DATA')")
    public void indexContract(@RequestParam("files") List<MultipartFile> files) {
        System.out.println("Usao u index contract kontroler");
        indexingService.indexContract(files);
    }
}
