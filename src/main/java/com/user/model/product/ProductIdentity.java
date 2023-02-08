package com.user.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "jwt.vehicle")
public class ProductIdentity {
    private String car;
    private String bike;
}
