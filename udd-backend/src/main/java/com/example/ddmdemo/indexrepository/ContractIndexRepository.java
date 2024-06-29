package com.example.ddmdemo.indexrepository;


import com.example.ddmdemo.indexmodel.ContractIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractIndexRepository extends ElasticsearchRepository<ContractIndex, String> {
}
