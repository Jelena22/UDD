package com.example.ddmdemo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParsedContract {

    private String name;
    private String surname;
    private String governmentName;
    private String address;
    private String administrationLevel;
    private String content;
    //private MultipartFile contractFile;
}
