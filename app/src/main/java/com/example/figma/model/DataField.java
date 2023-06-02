package com.example.figma.model;

public class DataField {
    private String fieldName;
    private Object fieldValue;

    public DataField(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}

