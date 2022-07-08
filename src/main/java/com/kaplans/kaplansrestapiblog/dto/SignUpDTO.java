package com.kaplans.kaplansrestapiblog.dto;


import lombok.Data;

@Data
public class SignUpDTO {
    private String userName;
    private String eMail;
    private String password;
}
