package com.example.ddmdemo.indexrepository;


import com.example.ddmdemo.indexmodel.LawIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawIndexRepository extends ElasticsearchRepository<LawIndex, String> {
}
