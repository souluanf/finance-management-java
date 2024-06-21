package dev.luanfernandes.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String openApiUrl = "/swagger-ui/index.html";
        registry.addRedirectViewController("/", openApiUrl);
        registry.addRedirectViewController("/doc", openApiUrl);
        registry.addRedirectViewController("/docs", openApiUrl);
        registry.addRedirectViewController("/swagger", openApiUrl);
        registry.addRedirectViewController("/swagger-ui", openApiUrl);
    }
}
