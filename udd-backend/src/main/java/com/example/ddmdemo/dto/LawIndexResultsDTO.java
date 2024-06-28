package com.example.ddmdemo.dto;


import com.example.ddmdemo.indexmodel.LawIndex;
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
public class LawIndexResultsDTO {
    private String downloadUrl;
    private String serverFileName;
    private String title;
    private List<String> highlight;

    public static Page<LawIndexResultsDTO> toLawIndexResultsDTO(final SearchPage<LawIndex> results, final FileService fileService) {
        List<LawIndexResultsDTO> contractIndexResultsDTOs = results.getContent().stream()
                .map(index -> LawIndexResultsDTO.fromContractIndex(index, fileService))
                .collect(Collectors.toList());
        System.out.println(contractIndexResultsDTOs);
        return new PageImpl<>(contractIndexResultsDTOs, results.getPageable(), results.getTotalElements());
    }

    private static LawIndexResultsDTO fromContractIndex(final SearchHit<LawIndex> index, final FileService fileService) {
        LawIndex lawIndex = index.getContent();
        String serverFilename = lawIndex.getServerFilename();
        return new LawIndexResultsDTO(
                fileService.loadAsResource(serverFilename),
                index.getContent().getServerFilename(),
                index.getContent().getTitle(),
                index.getHighlightField("content")
        );
    }
}
