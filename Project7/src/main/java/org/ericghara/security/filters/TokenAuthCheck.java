package org.ericghara.security.filters;

import org.ericghara.security.authentications.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.ericghara.services.RequestHeaderKeys.AUTH_TOKEN;

@Component
public class TokenAuthCheck
        implements AuthCheck<HttpServletRequest,
                Authentication,
                HttpServletResponse> {

    AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    public TokenAuthCheck(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    Authentication doAuthentication(HttpServletResponse response,
                                    Authentication a) {
        Authentication validAuth;
        try {
            validAuth = authenticationManager.authenticate(a);
        } catch (BadCredentialsException e) {
            onFailure(e, a, response);
            return null;
        }
        return validAuth;
    }


    @Override
    public Authentication authenticate(HttpServletRequest request, HttpServletResponse response) {
        Authentication a = new TokenAuthentication(AUTH_TOKEN.valueIn(request) );
        return doAuthentication(response, a);
    }

    @Override
    public void onFailure(AuthenticationException exception, Authentication authentication, HttpServletResponse response) {
        System.out.println(exception.getMessage() );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        throw new BadCredentialsException("An Invalid or expired token was provided", exception);
    }

    @Override
    public void onSuccess(Authentication authentication, HttpServletResponse response) throws RuntimeException {
        throw new RuntimeException("Not implemented, do not call");
    }
}
