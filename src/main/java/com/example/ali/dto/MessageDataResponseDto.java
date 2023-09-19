package com.example.ali.dto;

public class MessageDataResponseDto {
    private String msg;
    private Object data;

    public MessageDataResponseDto(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }
}
