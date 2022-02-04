package org.ericghara.security.providers;

import org.ericghara.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String requestKey = authentication.getName();
        if (requestKey.equals(key) ) {
            return new CustomAuthentication(null, null, null); // these are null because we're overriding
        }
        throw new BadCredentialsException("The custom authentication key was invalid.");
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return CustomAuthentication.class.equals(authClass);
    }
}
