package com.example.ddmdemo.service.interfaces;

//import com.example.ddmdemo.dto.LawIndexResultsDTO;
import com.example.ddmdemo.dto.ContractIndexResultsDTO;
import com.example.ddmdemo.dto.LawIndexResultsDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    Page<ContractIndexResultsDTO> advancedContractSearch(List<String> expression, Pageable pageable);

    Page<LawIndexResultsDTO> advancedLawSearch(List<String> expression, Pageable pageable);

    Page<LawIndexResultsDTO> lawSearch(final List<String> keywords, final Pageable pageable);

    Page<ContractIndexResultsDTO> contractSearchByNameAndSurname(final List<String> keywords, final Pageable pageable);
    Page<ContractIndexResultsDTO> contractSearchByGovernmentNameAndLevel(final List<String> keywords, final Pageable pageable);

    Page<ContractIndexResultsDTO> contractSearchByContent(final List<String> keywords, final Pageable pageable);

    Page<ContractIndexResultsDTO> searchContractByGeoDistance(final String city, final double distance, final Pageable pageable);
}
