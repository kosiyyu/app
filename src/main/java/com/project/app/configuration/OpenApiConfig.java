package com.project.app.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(title = "scijourdex api", description = "This documentation provides endpoints for managing and retrieving data. Happy using.", termsOfService = "????", contact = @Contact(name = "Karol Kos", email = "kosiyyu@gmail.com", url = "https://github.com/kosiyyu/scijourdex-backend"), license = @License(), version = "1.0.0", extensions = @Extension(properties = {})))
public class OpenApiConfig {
}
