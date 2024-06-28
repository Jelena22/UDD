//package com.example.ddmdemo.indexmodel;
//
//
//import com.example.ddmdemo.request.ContractIndexRequest;
//import jakarta.persistence.Id;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.elasticsearch.common.geo.GeoPoint;
//import org.springframework.data.elasticsearch.annotations.*;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Document(indexName = "contract_index")
//@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
//public class ContractIndex {
//
//    @Id
//    private String id;
//
//    @Field(type = FieldType.Text, store = true, name = "name", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String name;
//
//    @Field(type = FieldType.Text, store = true, name = "surname", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String surname;
//
//    @Field(type = FieldType.Text, store = true, name = "government_name", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String governmentName;
//
//    @Field(type = FieldType.Text, store = true, name = "administration_level", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String administrationLevel;
//
//    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String content;
//
//    @Field(type = FieldType.Text, store = true, name = "address", analyzer = "rebuilt_serbian", searchAnalyzer = "rebuilt_serbian")
//    private String address;
//
//    @GeoPointField
//    @Field(store = true, name = "location")
//    private GeoPoint location;
//
//    @Field(type = FieldType.Text, store = true, name = "title", index = false)
//    private String title;
//
//    @Field(type = FieldType.Text, store = true, name = "server_filename", index = false)
//    private String serverFilename;
//
//    @Field(type = FieldType.Integer, store = true, name = "database_id", index = false)
//    private Integer databaseId;
//
//    public ContractIndex(final ContractIndexRequest contractIndexRequest) {
//        this.name = contractIndexRequest.getName();
//        this.surname = contractIndexRequest.getSurname();
//        this.governmentName = contractIndexRequest.getGovernmentName();
//        this.administrationLevel = contractIndexRequest.getAdministrationLevel();
//        this.address = contractIndexRequest.getAddress();
//        this.title = contractIndexRequest.getContractFile().getName();
//    }
//}
