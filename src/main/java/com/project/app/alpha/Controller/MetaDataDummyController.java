package com.project.app.alpha.Controller;

import com.project.app.alpha.Model.Metadata;
import com.project.app.alpha.Service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alpha")
public class MetaDataDummyController {

    private final MetadataService metadataService;

    public MetaDataDummyController(MetadataService metadataService){
        this.metadataService = metadataService;
    }

    @GetMapping
    public ResponseEntity<List<Metadata>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(metadataService.findAll());
    }

    @PostMapping("/post/dummy")
    public ResponseEntity<String> postDummy(@RequestBody Metadata metaData){
        return ResponseEntity.status(HttpStatus.CREATED).body(metadataService.add(metaData) != null ? "Success" : "Failure");
    }
}
