package com.kaplans.kaplansrestapiblog.dto;


import lombok.Data;

@Data
public class LoginDTO {
    private String userNameOrEMail;
    private String password;
}
