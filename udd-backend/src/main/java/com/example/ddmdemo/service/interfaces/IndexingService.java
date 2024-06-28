package com.example.ddmdemo.service.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IndexingService {

    String indexDocument(MultipartFile documentFile);

    void indexLaws(final List<MultipartFile> files);
}
