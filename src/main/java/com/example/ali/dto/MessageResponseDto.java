package com.example.ali.dto;

public class MessageResponseDto {

    private String msg;
    private Object data;

    public MessageResponseDto(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
}

