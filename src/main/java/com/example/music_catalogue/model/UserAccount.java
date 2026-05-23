package com.example.music_catalogue.model;

public class UserAccount {
    private int id;
    private String username;
    private String passwordHash;

    public UserAccount() {
    }

    public UserAccount(int id, String username, String passwordHash) {
        this.id           = id;
        this.username     = username;
        this.passwordHash = passwordHash;
    }

    public int    getId()                    { return id; }
    public void   setId(int id)              { this.id = id; }
    public String getUsername()              { return username; }
    public void   setUsername(String u)      { this.username = u; }
    public String getPasswordHash()          { return passwordHash; }
    public void   setPasswordHash(String h)  { this.passwordHash = h; }
}
