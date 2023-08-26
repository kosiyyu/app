package com.project.app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    public Article(String title1, String issn1, String eissn1, String title2, String issn2, String eissn2) {
        this.title1 = title1;
        this.issn1 = issn1;
        this.eissn1 = eissn1;
        this.title2 = title2;
        this.issn2 = issn2;
        this.eissn2 = eissn2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title1;

    private String issn1;

    private String eissn1;

    private String title2;

    private String issn2;

    private String eissn2;
}
