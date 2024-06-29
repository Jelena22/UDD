package com.example.ddmdemo.dto;


import com.example.ddmdemo.indexmodel.ContractIndex;
import com.example.ddmdemo.service.interfaces.FileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractIndexResultsDTO {
    private String name;
    private String surname;
    private String governmentName;
    private String address;
    private String administrationLevel;
    private String downloadUrl;
    private String serverFileName;
    private String title;
    private List<String> highlight;

    public static Page<ContractIndexResultsDTO> toContractIndexResultsDTO(final SearchPage<ContractIndex> results, final FileService fileService) {
        List<ContractIndexResultsDTO> contractIndexResultsDTOs = results.getContent().stream()
                .map(contractIndex -> ContractIndexResultsDTO.fromContractIndex(contractIndex, fileService))
                .collect(Collectors.toList());

        return new PageImpl<>(contractIndexResultsDTOs, results.getPageable(), results.getTotalElements());
    }

    private static ContractIndexResultsDTO fromContractIndex(final SearchHit<ContractIndex> contractIndex, final FileService fileService) {
        return new ContractIndexResultsDTO(
                contractIndex.getContent().getName(),
                contractIndex.getContent().getSurname(),
                contractIndex.getContent().getGovernmentName(),
                contractIndex.getContent().getAddress(),
                contractIndex.getContent().getAdministrationLevel(),
                fileService.loadAsResource(contractIndex.getContent().getServerFilename()),
                contractIndex.getContent().getServerFilename(),
                contractIndex.getContent().getTitle(),
                contractIndex.getHighlightField("content")
        );
    }
}
