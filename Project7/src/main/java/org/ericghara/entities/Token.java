package org.ericghara.entities;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Token {

    @Id
    @Column(name="FK_Userpass_id")
    private int id;


    private String token;

    @Column(name = "grant_time")
    private Instant grantTime;

    @OneToOne(optional = true)
    @JoinColumn(name = "FK_Userpass_id")
    private User user;

    public Token() {}

    public Token(int id) {
        this.id = id;
        this.token = UUID.randomUUID().toString();
        this.grantTime = Instant.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
