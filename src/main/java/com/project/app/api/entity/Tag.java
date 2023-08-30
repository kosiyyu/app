package com.project.app.api.entity;

import com.project.app.tools.TagValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    public Tag(String value) {
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    //@TagValue
    @Column(name = "value")
    String value;
}
