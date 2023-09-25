package com.moredevs.psychclinic.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Psychology Clinic 'la vie' API")
                        .version(appVersion)
                        .description(
"""
This is the second version of a API for interacting with a Psychology Clinic. This is a study project for the SpringBoot module of the +Devs2Blu 2023 program. \n
Current [GitHub Repository](https://github.com/tiagospeckart/la-vie-spring) can be found Here, as well the [Project's Wiki](https://github.com/tiagospeckart/la-vie-spring/wiki). \n
Previous version [GitHub Repository](https://github.com/tiagospeckart/la-vie-backend) can be found here, as well as the [Postman Documentation](https://documenter.getpostman.com/view/24470850/2s8YzTU2ZV).
"""
                        )
                        .license(new License().name("MIT").url("https://opensource.org/license/mit/"))
                        .contact(new Contact()
                                .name("Tiago Martins Speckart")
                                .email("tiagospeckart@gmail.com")
                                .url("https://www.speckart.dev/")));
    }

}
