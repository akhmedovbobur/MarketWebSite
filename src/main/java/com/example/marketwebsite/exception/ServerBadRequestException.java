package com.example.marketwebsite.exception;

public class ServerBadRequestException extends  RuntimeException{
    public ServerBadRequestException(String message){
        super(message);
    }
}
