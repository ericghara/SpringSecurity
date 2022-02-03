package org.ericghara.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Implement authentication logic here
        // 1. if the request is authenticated, then you should return Authentication
        // 2. if the request is not authenticated, then you should throw
        // 3. if the authentication isn't supported, then by this authentication provider then return null,
        //    (signals authentication provider to try to authenticate using a different method.)
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials() );

        UserDetails u = userDetailsService.loadUserByUsername(username);

        if (u != null) {
            if (passwordEncoder.matches(password, u.getPassword() ) ) {
                return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities() );
            }
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    // Determines if authType is the type of UsernamePasswordAuthenticationToken
    // which tells the authentication provider that this is the correct class to use
    public boolean supports(Class<?> authType) {
        return UsernamePasswordAuthenticationToken.class.equals(authType);
    }
}
