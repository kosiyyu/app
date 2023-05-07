package com.project.app.Alpha.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "test_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestData {

    public TestData(String fullFilename, String path){
        this.fullFilename = fullFilename;
        this.path = path;
    }

    @MongoId
    private String id;

    @JsonProperty("full_filename")
    private String fullFilename;

    private String path;
}
