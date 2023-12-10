package com.project.app.exception;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String msg){
        super(msg);
    }

    public AlreadyExistsException(){
        super("The specified piece already exists.");
    }
}
