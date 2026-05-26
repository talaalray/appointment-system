package com.appointment_management.demo.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String secret;
    private int accessExpMin;

    public void setSecret(String secret) { this.secret = secret; }
    public void setAccessExpMin(int accessExpMin) { this.accessExpMin = accessExpMin; }
}
