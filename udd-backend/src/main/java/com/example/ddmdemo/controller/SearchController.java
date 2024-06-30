package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.*;
import com.example.ddmdemo.indexmodel.DummyIndex;
import com.example.ddmdemo.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/simple")
    public Page<DummyIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<DummyIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

    @PostMapping("/law")
    public Page<LawIndexResultsDTO> simpleLawSearch(@RequestBody SearchQueryDTO simpleSearchQuery, Pageable pageable) {
        System.out.println(simpleSearchQuery);
        return searchService.lawSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/contract/by-name-and-surname")
    public Page<ContractIndexResultsDTO> searchContractByNameAndSurname(@RequestBody SearchQueryContractByNameAndSurnameDTO simpleSearchQuery, Pageable pageable) {
        System.out.println(simpleSearchQuery);
        List<String> tokens = Arrays.asList(simpleSearchQuery.getName(), simpleSearchQuery.getSurname());

        return searchService.contractSearchByNameAndSurname(tokens, pageable);
    }

    @PostMapping("/contract/by-government-name-and-level")
    public Page<ContractIndexResultsDTO> searchContractByGovernmentNameAndLevel(@RequestBody SearchQueryContractByGovernmentNameAnsLevelDTO simpleSearchQuery, Pageable pageable) {
        System.out.println("Usao u law search controller");
        System.out.println(simpleSearchQuery);
        List<String> tokens = Arrays.asList(simpleSearchQuery.getGovernmentName(), simpleSearchQuery.getAdministrationLevel());

        return searchService.contractSearchByGovernmentNameAndLevel(tokens, pageable);
    }

    @PostMapping("/contract/by-content")
    public Page<ContractIndexResultsDTO> searchContractByContent(@RequestBody SearchQueryDTO simpleSearchQuery, Pageable pageable) {
        return searchService.contractSearchByContent(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/contract/by-geolication")
    public Page<ContractIndexResultsDTO> searchContractByGeolocation(@RequestBody SearchByGeolocationDTO simpleSearchQuery, Pageable pageable) {
        return searchService.searchContractByGeoDistance(simpleSearchQuery.getCity(), simpleSearchQuery.getDistanceInKm(), pageable);
    }
}
