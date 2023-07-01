package com.example.figma.model;

public class Sharing_writing_DB {
    private String profile;
    private String id;
    private int pw;
    private String userName;
    private String title;
    private String content;
    private String uid;
    private String studentNumber;
    private String idToken;
    private String key;
    private String sharing_image;
    private String shar_key;
    private String sharing_time;

    public String getSharing_time() {
        return sharing_time;
    }

    public void setSharing_time(String sharing_time) {
        this.sharing_time = sharing_time;
    }

    public String getSharing_image() {
        return sharing_image;
    }

    public void setSharing_image(String sharing_image) {
        this.sharing_image = sharing_image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdToken() {

        return idToken;
    }

    public void setIdToken(String idToken) {

        this.idToken = idToken;
    }

    public Sharing_writing_DB(){}

    public String getShar_key() {
        return shar_key;
    }

    public void setShar_key(String shar_key) {
        this.shar_key = shar_key;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
