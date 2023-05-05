package com.project.app.Alpha.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestData {
    @Id
    String fullFilename;
    String path;
}
