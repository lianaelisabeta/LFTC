package com.company.exceptions;

public class MyExc extends RuntimeException {
    private String message;
    public MyExc(String message){
        this.message=message;
    }
    public String getMessage(){
     return message;
    }
}
