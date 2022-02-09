package org.ericghara.configuration;

import org.ericghara.security.filters.TokenAuthFilter;
import org.ericghara.security.filters.UsernamePasswordAuthFilter;
import org.ericghara.security.providers.OtpAuthenticationProvider;
import org.ericghara.security.providers.TokenAuthenticationProvider;
import org.ericghara.security.providers.UsernamePasswordAuthProvider;
import org.ericghara.services.DbBootstrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthFilter usernamePasswordAuthFilter;

    @Autowired
    private TokenAuthFilter tokenAuthFilter;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthProvider)
            .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterAt(usernamePasswordAuthFilter,
                BasicAuthenticationFilter.class)
            .addFilterAt(tokenAuthFilter,
                BasicAuthenticationFilter.class);
    }

    @EventListener
    @Autowired
    private void applicationStartedEvent(DbBootstrapper dbBootstrapper) {
        dbBootstrapper.add("erica", "pass", new String[] {"read"});
    }
}
