package com.project.app.api.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "path")
    private String path;
    @Column(name = "last_modification")
    private Date lastModification;

    public Metadata(String originalFilename, String path) {
        this.originalFilename = originalFilename;
        this.path = path;
        this.lastModification = new Date();
    }
}
