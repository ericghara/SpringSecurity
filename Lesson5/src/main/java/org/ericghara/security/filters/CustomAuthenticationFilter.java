package org.ericghara.security.filters;

import org.ericghara.security.authentication.CustomAuthentication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter implements ApplicationListener<ApplicationStartedEvent> {

    private AuthenticationManager authenticationManager; // note initially null

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain chain)
            throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        var auth = new CustomAuthentication(authorization);
        Authentication result;
        try {
            result = authenticationManager.authenticate(auth);
            if (result.isAuthenticated() ) {
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request, response);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /*Allows for class instantiation without initializing the authentication manager.
    * This resolves a circular dependency for the authentication manager.
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.authenticationManager = event.getApplicationContext()
                                          .getBean(AuthenticationManager.class);
    }
}
