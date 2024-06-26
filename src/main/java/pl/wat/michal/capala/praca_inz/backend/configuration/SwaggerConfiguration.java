package pl.wat.michal.capala.praca_inz.backend.configuration;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    public Docket get(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/documents/**"))
                .apis(RequestHandlerSelectors.basePackage("pl.wat.michal.capala.praca_inz.backend"))
                .build();
    }
}
