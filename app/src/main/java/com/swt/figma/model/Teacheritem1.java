package com.swt.figma.model;

// 데이터를 담을 모듈 생성
public class Teacheritem1 {
    String name;
    String number;
    int resourceId;

    public Teacheritem1(int resourceId, String name, String number) {
        this.name = name;
        this.number = number;
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getNumber() {
        return number;
    }
    public String getName(){
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceId(int resourceId){
        this.resourceId = resourceId;
    }

}
