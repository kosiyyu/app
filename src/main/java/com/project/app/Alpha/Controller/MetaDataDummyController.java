package com.project.app.Alpha.Controller;

import com.project.app.Alpha.Model.Metadata;
import com.project.app.Alpha.Service.MetadataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MetaDataDummyController {

    private final MetadataService metadataService;

    public MetaDataDummyController(MetadataService metadataService){
        this.metadataService = metadataService;
    }

    @GetMapping
    public List<Metadata> getAll(){
        return metadataService.findAll();
    }

    @PostMapping("post")
    public String getAll(@RequestBody Metadata metaData){
        return metadataService.add(metaData) != null ? "Success" : "Failure";
    }
}
