package com.example.ddmdemo.service.interfaces;


import com.example.ddmdemo.model.Law;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface LawService {

    Law save(final MultipartFile contractFile, final String serverFileName);
}
