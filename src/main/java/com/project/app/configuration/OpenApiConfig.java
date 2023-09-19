package com.project.app.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "SPRING BOOT API", description = "TO DO", termsOfService = "????", contact = @Contact(name = "Karol Kos", email = "kosiyyu@gmail.com", url = "https://github.com/kosiyyu"), license = @License(), version = "1.0.0", extensions = @Extension(properties = {})))
public class OpenApiConfig {
}
