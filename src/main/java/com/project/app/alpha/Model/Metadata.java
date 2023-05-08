package com.project.app.alpha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "test_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    public Metadata(String fullFilename, String path){
        this.fullFilename = fullFilename;
        this.path = path;
        this.lastModification = new Date();
    }

    @MongoId
    private String id;

    private String fullFilename;

    private String path;

    private Date lastModification;
}
