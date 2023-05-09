package com.example.figma;

public class bulletin_com_DB {

    private String name;
    private String studentNumber;
    private String content;

    private String writer;
    private String data;

    public bulletin_com_DB(String username, String content, String userId, String timeStamp){

    }
    public bulletin_com_DB(String writer, String content, String data){
        this.writer = writer;
        this.content = content;
        this.data = data;

    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;


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

}
