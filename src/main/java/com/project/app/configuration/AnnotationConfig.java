package com.project.app.configuration;

import com.project.app.tools.TagValuePostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotationConfig {

    public TagValuePostProcessor tagValuePostProcessor() {
        return new TagValuePostProcessor();
    }
}
