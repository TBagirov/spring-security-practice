package org.security.practic.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignInResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;

    public SignInResponse(String jwtToken, String username, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
    }
}
