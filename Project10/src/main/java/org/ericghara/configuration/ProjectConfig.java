package org.ericghara.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // just for demo
        http.authorizeRequests()
            .anyRequest()
            .permitAll();

        http.cors( c -> {
            CorsConfigurationSource cs = r -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedOrigins(List.of("http://localhost:1337","http://127.0.0.1:1337")); // can specify in a properties file
                cc.setAllowedMethods(List.of("GET","POST"));
                return cc;
            };
            c.configurationSource(cs);
        });
    }
}
