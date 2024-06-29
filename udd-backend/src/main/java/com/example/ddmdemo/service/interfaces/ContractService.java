package com.example.ddmdemo.service.interfaces;


import com.example.ddmdemo.model.Contract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ContractService {

    Contract save(final MultipartFile contractFile, final String serverFileName);
}
