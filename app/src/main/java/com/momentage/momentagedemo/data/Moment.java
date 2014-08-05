package com.momentage.momentagedemo.data;

public class Moment {

    private int id = 0;
    private String userName = "";
    private String momentTitle = "";
    private String momentDesc = "";
    private int photoCount = 0;
    private int videoCount = 0;
    private int audioCount = 0;
    private String profilePicURL = "";
    private String backgroundURL = "";

    public Moment() {

    }

    public Moment(int id, String userName, String momentTitle, String momentDesc,
                  int photoCount, int videoCount, int audioCount, String profilePicURL, String backgroundURL) {
        this.id = id;
        this.userName = userName;
        this.momentTitle = momentTitle;
        this.momentDesc = momentDesc;
        this.photoCount = photoCount;
        this.videoCount = videoCount;
        this.audioCount = audioCount;
        this.profilePicURL = profilePicURL;
        this.backgroundURL = backgroundURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMomentTitle() {
        return momentTitle;
    }

    public void setMomentTitle(String momentTitle) {
        this.momentTitle = momentTitle;
    }

    public String getMomentDesc() {
        return momentDesc;
    }

    public void setMomentDesc(String momentDesc) {
        this.momentDesc = momentDesc;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getAudioCount() {
        return audioCount;
    }

    public void setAudioCount(int audioCount) {
        this.audioCount = audioCount;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getBackgroundURL() {
        return backgroundURL;
    }

    public void setBackgroundURL(String backgroundURL) {
        this.backgroundURL = backgroundURL;
    }
}
