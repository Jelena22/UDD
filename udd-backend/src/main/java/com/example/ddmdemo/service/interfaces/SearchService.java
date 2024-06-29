package com.example.ddmdemo.service.interfaces;

//import com.example.ddmdemo.dto.LawIndexResultsDTO;
import com.example.ddmdemo.dto.ContractIndexResultsDTO;
import com.example.ddmdemo.dto.LawIndexResultsDTO;
import com.example.ddmdemo.dto.SearchQueryContractByNameAndSurnameDTO;
import com.example.ddmdemo.indexmodel.DummyIndex;
import java.util.List;

import com.example.ddmdemo.indexmodel.LawIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    Page<DummyIndex> simpleSearch(List<String> keywords, Pageable pageable);

    Page<DummyIndex> advancedSearch(List<String> expression, Pageable pageable);

    Page<LawIndexResultsDTO> lawSearch(final List<String> keywords, final Pageable pageable);

    Page<ContractIndexResultsDTO> contractSearchByNameAndSurname(final List<String> keywords, final Pageable pageable);
    Page<ContractIndexResultsDTO> contractSearchByGovernmentNameAndLevel(final List<String> keywords, final Pageable pageable);

    Page<ContractIndexResultsDTO> contractSearchByContent(final List<String> keywords, final Pageable pageable);
}
