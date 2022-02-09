package org.ericghara.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
public class Otp {

    @Id
    @Column(name="FK_Userpass_id")
    private int id;
    private String otp;
    private int attempts;

    @Column(name="grant_time")
    private Instant grantTime;

    @OneToOne(optional=true)
    @JoinColumn(name = "FK_Userpass_id")
    User user;

    public Otp() {}

    public Otp(int id, String otp) {
        this.id = id;
        this.otp = otp;
        this.attempts = 0;
        this.grantTime = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Instant getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Instant grantTime) {
        this.grantTime = grantTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
