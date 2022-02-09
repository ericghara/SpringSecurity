package org.ericghara.configuration;

import org.ericghara.security.CsrfTokenLoggerFilter;
import org.ericghara.security.CustomCsrfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomCsrfRepository customCsrfRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        super.configure(http);
        //http.csrf().disable(); // globally disable
        http.csrf(c -> {
           c.ignoringAntMatchers("/csrfdisabled/*"); // ignore csrf for some endpoints
           c.csrfTokenRepository(customCsrfRepository);
        });
        http.addFilterAfter(new CsrfTokenLoggerFilter(),
                CsrfFilter.class);
    }
}
