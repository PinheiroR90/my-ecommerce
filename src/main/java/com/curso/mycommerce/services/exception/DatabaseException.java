package com.curso.mycommerce.services.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg){
        super(msg);
    }
}
