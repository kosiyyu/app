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

    public Metadata(String originalFilename, String path, List<Tag> tags){
        this.originalFilename = originalFilename;

        this.path = path;
        this.lastModification = new Date();
        this.tags = tags;
    }

    public Metadata(String originalFilename, String path){
        this.originalFilename = originalFilename;
        this.path = path;
        this.lastModification = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private String originalFilename;

    private String path;

    private Date lastModification;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;
}
