package org.ericghara.services;

import org.ericghara.entities.Otp;
import org.ericghara.repositories.OtpRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class OtpService {

    private final int OTP_LENGTH = 4;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final OtpRepository otpRepository;
    private final Random random;

    public OtpService(JpaUserDetailsService jpaUserDetailsService, OtpRepository otpRepository) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.otpRepository = otpRepository;
        random = new Random();
    }

    private String newOtp() {
        StringBuilder sb = new StringBuilder();
        random.ints(0,10).limit(OTP_LENGTH).forEach(sb::append);
        return sb.toString();
    }

    private String newToken() {
        return UUID.randomUUID().toString();
    }

    public Otp getOtp(int id) throws AuthenticationException {
        return otpRepository.findOtpById(id)
                            .orElseThrow(() -> new UsernameNotFoundException("No entry found for id: " + id) );
    }

    public Otp getOtp(String username) {
        int id = jpaUserDetailsService.getIdByUsername(username);
        return getOtp(id);
    }

    public void clearOtp(String username) {
        Otp otpEntity = getOtp(username);
        otpEntity.setOtp("");
        otpRepository.save(otpEntity);
    }

    public String grantToken() {
        //Implement stuff to store token in repository here
        return newToken();
    }

    public int otpLength() {
        return OTP_LENGTH;
    }

    public void generateOtp(Authentication validAuth) {
        String username = validAuth.getName();
        int id = jpaUserDetailsService.getIdByUsername(username);
        Otp otpEntity = new Otp();
        otpEntity.setId(id);
        otpEntity.setOtp(newOtp() );
        otpRepository.save(otpEntity);
    }
}
