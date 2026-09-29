package com.zee.course.auth.payment.authpaymentservice.config;


import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Sep, 2026
 */

@Configuration
public class PaymentConfig {

    @Primary
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")  // Allow all origins
                        .allowedMethods(
                                HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()
                        )
                        .allowedHeaders("*")
                        .exposedHeaders("Set-Cookie")
                        .allowCredentials(false)  // Must be false when allowedOrigins is "*"
                        .maxAge(3600);  // Cache the preflight response for 1 hour
            }
        };
    }
}
