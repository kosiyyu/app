package com.project.app.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title1")
    private String title1;
    @Column(name = "issn1")
    private String issn1;
    @Column(name = "eissn1")
    private String eissn1;
    @Column(name = "title2")
    private String title2;
    @Column(name = "issn2")
    private String issn2;
    @Column(name = "eissn2")
    private String eissn2;

    @Column(name = "points")
    private Integer points;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public Article(String title1, String issn1, String eissn1, String title2, String issn2, String eissn2, Integer points) {
        this.title1 = title1;
        this.issn1 = issn1;
        this.eissn1 = eissn1;
        this.title2 = title2;
        this.issn2 = issn2;
        this.eissn2 = eissn2;
        this.points = points;
    }
}
