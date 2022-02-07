package org.ericghara.services;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public enum RequestHeaderKeys {
    USERNAME ("username"),
    PASSWORD ("password"),
    OTP ("otp");

    private final String key;

    RequestHeaderKeys(String key) {
        this.key = key;
    }

    public String valueIn(HttpServletRequest request) {
        return request.getHeader(key);
    }

    public boolean in(HttpServletRequest request) {
        return Objects.nonNull(valueIn(request) );
    }
}
