package com.example.ddmdemo.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
//import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class AdvancedSearchQueryBuilder {


    public Query buildAdvancedSearchQuery(List<String> expressions) {
        List<String> postfix = InfixToPostfixConverter.convertToPostfix(expressions);
        System.out.println(postfix);
        Deque<Query> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (InfixToPostfixConverter.isOperand(token)) {
                String field = token.split(":")[0];
                String value = token.split(":")[1];
                stack.push(QueryBuilders.match(m -> m.field(field).query(value))); // Koristimo matchQuery za kreiranje match upita
            } else {
                Query right = stack.pop();
                Query left = stack.pop();
                BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
                switch (token) {
                    case "AND":
                        boolQueryBuilder.must(left);
                        boolQueryBuilder.must(right);
                        break;
                    case "OR":
                        boolQueryBuilder.should(left);
                        boolQueryBuilder.should(right);
                        break;
                    case "NOT":
                        boolQueryBuilder.must(left);
                        boolQueryBuilder.mustNot(right);
                        break;
                }
                BoolQuery boolQuery = boolQueryBuilder.build();
                stack.push(Query.of(q -> q.bool(boolQuery)));
            }
        }

        return stack.pop();
    }
}
