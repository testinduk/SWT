package com.example.figma.model;

public class sharing_DB {
//테이블이라고 생각하고, 테이블에 들어갈 속성값을 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름

    private String profile;
    private String title;
    private String content;
    private String uid;
    private String userName;
    private String studentNumber;
    private String sharing_image;
//    private String timestamp;
//    String title; //제목
//    String content; //내용
//    String uid;
//    String timestamp;

    public sharing_DB(String title, String content){}

    //getter, setter 설정
    public String getProfile(){
        return profile;
    }

    public void setProfile(String profile){
        this.profile = profile;

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
    public String getUid(){

        return uid;
    }

    public void setUid(String uid){

        this.uid = uid;
    }

//    public String getTimestamp(){
//
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp){
//
//        this.timestamp = timestamp;
//    }

    //값을 추가할때 쓰는 함수, sharing_writing에서 sharing_DB함수에서 사용할 것임.
    public sharing_DB(String title, String content, String uid){
        this.title = title;
        this.content = content;
        this.uid = uid;
    }

    public void setUserName(String userName) {
    }

    public void setStudentNumber(String studentNumber) {
    }

    public void getUserName(String userName) {
    }

    public void getStudentNumber(String studentNumber) {
    }
}