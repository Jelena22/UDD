package com.example.ddmdemo.indexmodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "law_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class LawIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String content;

//    @Field(type = FieldType.Text, store = true, name = "content_en", analyzer = "english", searchAnalyzer = "english")
//    private String contentEn;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @Field(type = FieldType.Text, store = true, name = "title", index = false)
    private String title;

    @Field(type = FieldType.Integer, store = true, name = "database_id", index = false)
    private Integer databaseId;
}
