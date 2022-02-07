package org.ericghara.security.filters;

import org.ericghara.security.authentications.OtpAuthentication;
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

import static org.ericghara.services.RequestHeaderKeys.OTP;
import static org.ericghara.services.RequestHeaderKeys.USERNAME;

@Component
public class OtpAuthCheck extends UsernamePasswordAuthCheck {

    @Autowired
    @Lazy
    public OtpAuthCheck(AuthenticationManager authenticationManager, OtpService otpService) {
        super(authenticationManager, otpService);
    }

    void doAuthentication(HttpServletRequest request,
                          HttpServletResponse response,
                          Authentication authentication) {
        try {
            authentication = super.authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            onFailure(e, authentication, response);
            return;
        }
        onSuccess(authentication, response);
    }

    @Override
    public void authenticate(HttpServletRequest request,
                             HttpServletResponse response) {
        Authentication authentication = new OtpAuthentication(USERNAME.valueIn(request), OTP.valueIn(request) );
        doAuthentication(request, response, authentication);
        super.otpService.clearOtp(authentication.getName() );
    }

    @Override
    public void onFailure(AuthenticationException e, Authentication authentication, HttpServletResponse response) {
        super.onFailure(e, authentication, response);
    }

    @Override
    public void onSuccess(Authentication a, HttpServletResponse response) {
        response.addHeader("authorization", otpService.grantToken() );
    }
}
