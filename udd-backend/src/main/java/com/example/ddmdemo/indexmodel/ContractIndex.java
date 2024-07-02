package com.example.ddmdemo.indexmodel;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "new_contract_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class ContractIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, store = true, name = "name", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String name;

    @Field(type = FieldType.Text, store = true, name = "surname", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String surname;

    @Field(type = FieldType.Text, store = true, name = "government_name", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String governmentName;

    @Field(type = FieldType.Text, store = true, name = "administration_level", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String administrationLevel;

    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String content;

    @Field(type = FieldType.Text, store = true, name = "address", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String address;

    @GeoPointField
    @Field(store = true, name = "location")
    private GeoPoint location;

    @Field(type = FieldType.Text, store = true, name = "title", index = false)
    private String title;

    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
    private String serverFilename;

    @Field(type = FieldType.Integer, store = true, name = "database_id", index = false)
    private Integer databaseId;
}
