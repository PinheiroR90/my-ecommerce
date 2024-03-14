package com.curso.mycommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidateError extends CustomError{
    private List<FieldMessege> errors = new ArrayList<>();

    public ValidateError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessege> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessege(fieldName,message));
    }
}
