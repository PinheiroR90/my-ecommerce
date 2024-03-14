package com.curso.mycommerce.dto;

public class FieldMessege {
    private String fieldName;
    private String messeg;

    public FieldMessege(String fieldName, String messeg) {
        this.fieldName = fieldName;
        this.messeg = messeg;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMesseg() {
        return messeg;
    }
}
