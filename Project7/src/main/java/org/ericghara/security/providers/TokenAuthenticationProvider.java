package org.ericghara.security.providers;

import org.ericghara.entities.Token;
import org.ericghara.security.authentications.TokenAuthentication;
import org.ericghara.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    TokenService tokenService;

    @Autowired
    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authToken = authentication.getCredentials().toString();
        Token token = tokenService.getToken(authToken);
        tokenService.assertActive(token);
        UserDetails ud = tokenService.getUserDetails(token);
        return new TokenAuthentication(ud.getUsername(),
                                       authToken,
                                       ud.getAuthorities() );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
