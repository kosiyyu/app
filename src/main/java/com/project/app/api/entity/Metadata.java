package com.project.app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    public Metadata(String fullFilename, String path, List<Tag> tags){
        this.fullFilename = fullFilename;
        this.path = path;
        this.lastModification = new Date();
        this.tags = tags;
    }

    public Metadata(String fullFilename, String path){
        this.fullFilename = fullFilename;
        this.path = path;
        this.lastModification = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private String fullFilename;

    private String path;

    private Date lastModification;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;
}
