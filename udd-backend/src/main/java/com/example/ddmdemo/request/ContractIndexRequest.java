package com.example.ddmdemo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractIndexRequest {

    private String name;
    private String surname;
    private String governmentName;
    private String address;
    private String administrationLevel;
    private String content;
    private MultipartFile contractFile;
}
