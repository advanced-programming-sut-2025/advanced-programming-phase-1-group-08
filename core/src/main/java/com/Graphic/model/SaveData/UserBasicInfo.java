package com.Graphic.model.SaveData;

import java.util.HashMap;

public class UserBasicInfo {
    private String username;
    private String hashpass;
    private String email;
    private String nickname;
    private String gender;
    private String SecQ;
    private String SecA;

    public UserBasicInfo(String username, String hashpass, String email, String nickname, String gender,
                         String SecQ, String SecA) {
        this.username = username;
        this.hashpass = hashpass;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.SecQ = SecQ;
        this.SecA = SecA;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public String getHashpass() { return hashpass; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getGender() { return gender; }

    public void setUsername(String username) { this.username = username; }
    public void setHashpass(String hashpass) { this.hashpass = hashpass; }
    public void setEmail(String email) { this.email = email; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setGender(String gender) { this.gender = gender; }


    public String getSecQ() {
        return SecQ;
    }

    public void setSecQ(String secQ) {
        SecQ = secQ;
    }

    public String getSecA() {
        return SecA;
    }

    public void setSecA(String secA) {
        SecA = secA;
    }
}
