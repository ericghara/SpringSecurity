package org.ericghara.services;

import org.ericghara.entities.Token;
import org.ericghara.entities.User;
import org.ericghara.repositories.TokenRepository;
import org.ericghara.repositories.UserRepository;
import org.ericghara.security.authentications.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DbBootstrapper {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    private final OtpService otpService;

    @Autowired
    public DbBootstrapper(UserRepository userRepository,
                          TokenRepository tokenRepository,
                          UserDetailsService userDetailsService,
                          OtpService otpService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
        this.otpService = otpService;
    }

    public void add(String username, String password, String[] authorities) {
        new dbEntry(username, password, authorities);
    }

    public class dbEntry {

        private final String username;
        private final String password;
        private final String[] authorities;

        private dbEntry(String username, String password, String[] authorities) {
            this.username = username;
            this.password = password;
            this.authorities = authorities;
            addData();
        }

        private void addUser() {
            User u = new User(username, password, authorities);
            userRepository.save(u);
        }

        private void addOtp() {
            Authentication a = new OtpAuthentication(username, password);
            otpService.generateOtp(a);
        }

        private void addToken() {
            int id = userDetailsService.getIdByUsername(username);
            Token t = new Token(id);
            tokenRepository.save(t);
        }

        public void addData() {
            addUser();
            addOtp();
            addToken();
        }
    }

}


