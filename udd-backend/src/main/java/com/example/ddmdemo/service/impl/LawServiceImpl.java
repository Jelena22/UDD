package com.example.ddmdemo.service.impl;


import com.example.ddmdemo.exceptionhandling.exception.StorageException;
import com.example.ddmdemo.model.Law;
import com.example.ddmdemo.respository.LawRepository;
import com.example.ddmdemo.service.interfaces.LawService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LawServiceImpl implements LawService {

    private final LawRepository lawRepository;

    @Override
    public Law save(final MultipartFile file, final String serverFileName) {

        return lawRepository.save(new Law(detectMimeType(file), serverFileName));
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                    Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
                !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }
}
