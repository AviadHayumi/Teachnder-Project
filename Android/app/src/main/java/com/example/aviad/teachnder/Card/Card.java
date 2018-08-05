package com.example.aviad.teachnder.Card;

public class Card {
    private String userID;
    private String email;
    private String name;
    private String profileImageURL;
    private String about;
    private String phone;
    private String type;

    public Card(String userID, String email, String name, String profileImageURL, String about, String phone, String type) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.profileImageURL = profileImageURL;
        this.about = about;
        this.phone = phone;
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
