package org.ericghara.security.filters;

import org.ericghara.security.authentications.UsernamePasswordAuthentication;
import org.ericghara.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.ericghara.services.RequestHeaderKeys.USERNAME;
import static org.ericghara.services.RequestHeaderKeys.PASSWORD;

@Component
public class UsernamePasswordAuthCheck
        implements AuthCheck<HttpServletRequest,
                             Authentication,
                             HttpServletResponse> {

    final AuthenticationManager authenticationManager;
    final OtpService otpService;

    @Autowired
    @Lazy
    public UsernamePasswordAuthCheck(AuthenticationManager authenticationManager, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.otpService = otpService;
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
        onSuccess(a, response);
        return  validAuth;
    }

    @Override
    public Authentication authenticate(HttpServletRequest request,
                             HttpServletResponse response) {
        Authentication a = new UsernamePasswordAuthentication(USERNAME.valueIn(request), PASSWORD.valueIn(request) );
        return doAuthentication(response, a);
    }

    @Override
    public void onFailure(AuthenticationException e, Authentication a, HttpServletResponse response) {
        System.out.println(e.getMessage() );
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void onSuccess(Authentication a, HttpServletResponse response) {
        otpService.generateOtp(a);
    }
}
