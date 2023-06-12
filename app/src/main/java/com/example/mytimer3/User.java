package com.example.mytimer3;

public class User {
    private String username;
    private String password;
    private long cumulativeTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cumulativeTime = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getCumulativeTime() {
        return cumulativeTime;
    }

    public void addToCumulativeTime(long elapsedTime) {
        cumulativeTime += elapsedTime;
    }
}
