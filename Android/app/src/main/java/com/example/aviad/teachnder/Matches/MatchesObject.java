package com.example.aviad.teachnder.Matches;

public class MatchesObject {


    private String userID;
    private String userName;
    private String profileImageUrl;

    public MatchesObject(String userID, String userName, String profileImageUrl) {
        this.userID = userID;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}