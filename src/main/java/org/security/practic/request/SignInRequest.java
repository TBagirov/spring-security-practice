package org.security.practic.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String username;

    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
