package com.example.figma;
// 로그인 정보 DB
//학번 넣기

public class sign_up_DB {
    private String emailId;
    private String password;
    private String idToken;
    private String userName;
    private String studentNumber;

    private String sign_up_image;

    public String getSign_up_image() {
        return sign_up_image;
    }

    public void setSign_up_image(String sign_up_image) {
        this.sign_up_image = sign_up_image;
    }


    public sign_up_DB(){ }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}