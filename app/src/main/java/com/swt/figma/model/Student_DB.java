package com.swt.figma.model;

public class Student_DB {
    private String name;

    public Student_DB(){

    }

    public Student_DB(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
