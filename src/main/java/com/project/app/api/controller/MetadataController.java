package com.project.app.api.controller;

import com.project.app.api.entity.Metadata;
import com.project.app.api.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${API_V1}")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService){
        this.metadataService = metadataService;
    }

    @GetMapping("/metadata/download")
    public ResponseEntity getMetadata(){
        List<Metadata> metadataList = metadataService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(metadataList);
    }
}
