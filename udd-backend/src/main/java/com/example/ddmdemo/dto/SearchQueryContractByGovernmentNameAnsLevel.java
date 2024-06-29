package com.example.ddmdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryContractByGovernmentNameAnsLevel {
    private String governmentName;
    private String administrationLevel;
}
