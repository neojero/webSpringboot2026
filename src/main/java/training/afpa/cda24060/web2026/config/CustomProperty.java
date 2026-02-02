package training.afpa.cda24060.web2026.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "training.afpa.cda24060.webdemo") // mapper le namespace
@Data
public class CustomProperty {

    private String apiURL;
}
