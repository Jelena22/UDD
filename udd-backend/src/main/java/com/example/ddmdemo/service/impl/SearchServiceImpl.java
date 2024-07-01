package com.example.ddmdemo.service.impl;

//import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.ddmdemo.dto.*;
import com.example.ddmdemo.exceptionhandling.exception.MalformedQueryException;
import com.example.ddmdemo.indexmodel.ContractIndex;
import com.example.ddmdemo.indexmodel.LawIndex;
import com.example.ddmdemo.infrastructure.LocationClient;
import com.example.ddmdemo.service.interfaces.FileService;
import com.example.ddmdemo.service.interfaces.SearchService;
import java.util.List;

import com.example.ddmdemo.util.AdvancedSearchQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
//import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import org.springframework.stereotype.Service;

import static com.example.ddmdemo.dto.ContractIndexResultsDTO.toContractIndexResultsDTO;
import static com.example.ddmdemo.dto.LawIndexResultsDTO.toLawIndexResultsDTO;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchTemplate;
    private final FileService fileService;
    private final LocationClient locationClient;
    @Value("${geolocation.api.key}")
    private String apiKey;

    @Override
    public Page<ContractIndexResultsDTO> advancedContractSearch(List<String> expressions, Pageable pageable) {
        System.out.println(expressions.size());
        if (expressions.size() < 3 || expressions.size() % 2 == 0) {
            throw new MalformedQueryException("Search query malformed.");
        }

        var searchQueryBuilder =
            new NativeQueryBuilder().withQuery(new AdvancedSearchQueryBuilder().buildAdvancedSearchQuery(expressions))
                    .withHighlightQuery(new HighlightQuery(
                            new Highlight(List.of(
                                    new HighlightField("content", getHighlightFieldParameters())
                            )),
                            Object.class
                    ))
                .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());

        return runContractQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<LawIndexResultsDTO> advancedLawSearch(List<String> expressions, Pageable pageable) {
        System.out.println(expressions.size());
        if (expressions.size() < 3 || expressions.size() % 2 == 0) {
            throw new MalformedQueryException("Search query malformed.");
        }
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(new AdvancedSearchQueryBuilder().buildAdvancedSearchQuery(expressions))
                        .withHighlightQuery(new HighlightQuery(
                                new Highlight(List.of(
                                        new HighlightField("content", getHighlightFieldParameters())
                                )),
                                Object.class
                        ))
                        .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());
       return runLawQuery(searchQueryBuilder.build());
    }

    private Query buildSimpleSearchQuery(List<String> tokens) {
        System.out.println(tokens);
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                //b.should(sb -> sb.match(
                  //  m -> m.field("title").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.matchPhrase(m -> m.field("content").query(token)));
                //b.should(sb -> sb.match(m -> m.field("content_en").query(token)));
            });
            System.out.println(b);
            return b;
        })))._toQuery();
    }

    private Query buildAdvancedSearchQuery(List<String> operands, String operation) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            var field1 = operands.get(0).split(":")[0];
            var value1 = operands.get(0).split(":")[1];
            var field2 = operands.get(1).split(":")[0];
            var value2 = operands.get(1).split(":")[1];

            switch (operation) {
                case "AND":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.must(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "OR":
                    b.should(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.should(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "NOT":
                    b.must(sb -> sb.match(
                        m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.mustNot(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
            }

            return b;
        })))._toQuery();
    }
    @Override
    public Page<LawIndexResultsDTO> lawSearch(final List<String> keywords, final Pageable pageable) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords))
                        .withHighlightQuery(new HighlightQuery(
                                new Highlight(List.of(
                                        new HighlightField("content", getHighlightFieldParameters())
                                )),
                                Object.class
                        ))
                        .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());

        return runLawQuery(searchQueryBuilder.build());
    }

    @NotNull
    private HighlightFieldParameters getHighlightFieldParameters() {
        return HighlightFieldParameters.builder()
                .withFragmentSize(200)
                .withNumberOfFragments(1)
                .build();
    }

    private Page<LawIndexResultsDTO> runLawQuery(NativeQuery searchQuery) {
        System.out.println(searchQuery);
        var searchHits = elasticsearchTemplate.search(searchQuery, LawIndex.class,
                IndexCoordinates.of("law_index"));
        System.out.println(searchHits.getSearchHits());
        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
        System.out.println(searchHitsPaged);
        return toLawIndexResultsDTO(searchHitsPaged, fileService);
    }

    private Query buildNameAndSurnameQuery(List<String> tokens) {
        System.out.println(tokens);
        BoolQuery boolQuery = BoolQuery.of(b -> {
            tokens.forEach(token -> {
                // Match phrase query for name and surname
                b.should(m -> m.matchPhrase(mp -> mp.field("name").query(token)));
                b.should(m -> m.matchPhrase(mp -> mp.field("surname").query(token)));
                b.should(m -> m.matchPhrase(mp -> mp.field("content").query(token)));
            });
            return b;
        });

        return new Query.Builder().bool(boolQuery).build();
    }

    @Override
    public Page<ContractIndexResultsDTO> contractSearchByNameAndSurname(final List<String> keywords, final Pageable pageable) {
        System.out.println(keywords);
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildNameAndSurnameQuery(keywords))
                        .withHighlightQuery(new HighlightQuery(
                                new Highlight(List.of(
                                        new HighlightField("content", getHighlightFieldParameters())
                                )),
                                Object.class
                        ))
                        .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());

        return runContractQuery(searchQueryBuilder.build());
    }

    private Page<ContractIndexResultsDTO> runContractQuery(NativeQuery searchQuery) {
        System.out.println(searchQuery.getQuery());
        var searchHits = elasticsearchTemplate.search(searchQuery, ContractIndex.class,
                IndexCoordinates.of("contract_index"));
        System.out.println(searchHits.getSearchHits());
        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());
        System.out.println(searchHitsPaged);
        return toContractIndexResultsDTO(searchHitsPaged, fileService);
    }

    private Query buildGovermentNameAndLevelQuery(List<String> tokens) {
        System.out.println(tokens);
        BoolQuery boolQuery = BoolQuery.of(b -> {
            tokens.forEach(token -> {
                b.should(m -> m.matchPhrase(mp -> mp.field("government_name").query(token)));
                b.should(m -> m.matchPhrase(mp -> mp.field("administration_level").query(token)));
                b.should(m -> m.matchPhrase(mp -> mp.field("content").query(token)));
            });
            return b;
        });

        return new Query.Builder().bool(boolQuery).build();
    }

    @Override
    public Page<ContractIndexResultsDTO> contractSearchByGovernmentNameAndLevel(final List<String> keywords, final Pageable pageable) {
        System.out.println(keywords);
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildGovermentNameAndLevelQuery(keywords))
                        .withHighlightQuery(new HighlightQuery(
                                new Highlight(List.of(
                                        new HighlightField("content", getHighlightFieldParameters())
                                )),
                                Object.class
                        ))
                        .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());

        return runContractQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<ContractIndexResultsDTO> contractSearchByContent(final List<String> keywords, final Pageable pageable) {
        System.out.println(keywords);
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords))
                        .withHighlightQuery(new HighlightQuery(
                                new Highlight(List.of(
                                        new HighlightField("content", getHighlightFieldParameters())
                                )),
                                Object.class
                        ))
                        .withPageable(pageable);
        System.out.println(searchQueryBuilder.getQuery());

        return runContractQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<ContractIndexResultsDTO> searchContractByGeoDistance(final String city, final double distance, final Pageable pageable) {
        GeolocationResponseDTO responseDTO = locationClient.getGeolocation(apiKey, city, "json");
        String distanceInKm = distance + " kilometers";
        System.out.println(distanceInKm);
        System.out.println("Geolocation response: " + responseDTO.results());
        if (!responseDTO.results().isEmpty()) {
            NativeQueryBuilder nativeQueryBuilder = createGeolocationQueryWithHighlighting(responseDTO, distanceInKm, pageable);

            return runContractQuery(nativeQueryBuilder.build());
        }
        return Page.empty(pageable);
    }
    @NotNull
    private NativeQueryBuilder createGeolocationQueryWithHighlighting(final GeolocationResponseDTO responseDTO, final String distanceInKm, final Pageable pageable)
    {
        LocationDTO locationDTO = responseDTO.results().get(0);

        Query geoRequest = NativeQuery.builder().withQuery(query -> query
                .geoDistance(geoDistanceQuery -> geoDistanceQuery
                        .field("location").distance(distanceInKm)
                        .location(geoLocation -> geoLocation
                                .latlon(latLonGeoLocation -> latLonGeoLocation.lon(locationDTO.lon()).lat(locationDTO.lat()))
                        )
                )
        ).getQuery();

        return NativeQuery.builder()
                .withFilter(geoRequest)
                .withHighlightQuery(new HighlightQuery(
                        new Highlight(List.of(
                                new HighlightField("content", getHighlightFieldParameters())
                        )),
                        Object.class
                ))
                .withPageable(pageable);
    }

}
