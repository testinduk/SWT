package com.example.figma.model;

import java.util.List;

public class Board {
    public Board(){

    }

    // ------------ bulletin_com_DB --------------//
    private String name;
    private String studentNumber;
    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    private String profileUri;


    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    // ---------- bulletin_DB --------- //
    private String className;

    public List getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List deleteList) {
        this.deleteList = deleteList;
    }

    private List deleteList;
    private String profile;
    private String id;
    private String userName;
    private String title;
    private String uid;
    private String idToken;
    private String key;
    private String bulletin_image;
    private String bulletin_key;
    private String bulletin_time;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBulletin_time() {
        return bulletin_time;
    }

    public void setBulletin_time(String bulletin_time) {
        this.bulletin_time = bulletin_time;
    }

    public String getBulletin_image() {
        return bulletin_image;
    }

    public void setBulletin_image(String bulletin_image) {
        this.bulletin_image = bulletin_image;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBulletin_key() {
        return bulletin_key;
    }

    public void setBulletin_key(String bulletin_key) {
        this.bulletin_key = bulletin_key;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    // --------DataField --------- //
    private String fieldName;
    private Object fieldValue;


    public Board(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public Board(String name) {

    }


    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }





    // -------- notice_DB -------- //
    private String notice_time;
    private String notice_image;

    public String getNotice_image() {
        return notice_image;
    }

    public void setNotice_image(String notice_image) {
        this.notice_image = notice_image;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    private String IdToken;


    private String notice_key;

    public String getNotice_key() {
        return notice_key;
    }

    public void setNotice_key(String notice_key) {
        this.notice_key = notice_key;
    }

    // ----notice_com_DB


    // ----sharing_com_DB ------- //

    // -----shairng_DB ------ //

    private String sharing_image;



    //getter, setter 설정


    //값을 추가할때 쓰는 함수, sharing_writing에서 sharing_DB함수에서 사용할 것임.
    public void getUserName(String userName) {
    }

    public void getStudentNumber(String studentNumber) {
    }


    // ---- Sharing_wiriting_DB  ------- //

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


    public String getShar_key() {
        return shar_key;
    }

    public void setShar_key(String shar_key) {
        this.shar_key = shar_key;
    }


    // -----------sign_up_DB -----------//
    private String emailId;
    private String password;
    private String chpassword1;
    private String answer;

    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String sign_up_image;

    public String getSign_up_image() {
        return sign_up_image;
    }

    public void setSign_up_image(String sign_up_image) {
        this.sign_up_image = sign_up_image;
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
