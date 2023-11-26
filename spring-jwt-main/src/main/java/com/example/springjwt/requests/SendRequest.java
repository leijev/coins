package com.example.springjwt.requests;

import lombok.Data;

@Data
public class SendRequest {
    private String receiver;
    private Integer coins;
}
