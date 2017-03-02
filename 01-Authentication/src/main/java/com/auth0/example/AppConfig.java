package com.auth0.example;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity(debug = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.domain}")
    private String domain;
    @Value(value = "${auth0.clientId}")
    private String clientId;
    @Value(value = "${auth0.clientSecret}")
    private String clientSecret;

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    /**
     * Provides Auth0 API access
     */
    @Bean
    public Auth0Client auth0Client() {
        return new Auth0Client(domain, clientId, clientSecret);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers("/ping").permitAll()
                .antMatchers("/secured/ping").authenticated()
                .antMatchers();
    }

}
