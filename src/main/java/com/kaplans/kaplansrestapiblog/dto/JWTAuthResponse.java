package com.kaplans.kaplansrestapiblog.dto;



public class JWTAuthResponse {
    private final String accessToken;
    private String tokenType="Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
