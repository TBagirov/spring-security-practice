package org.security.practic.service;


import org.security.practic.jwt.JwtUtils;
import org.security.practic.request.SignInRequest;
import org.security.practic.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtAuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  public SignInResponse signIn(SignInRequest signInRequest) {
    Authentication authentication = null;

    try {
      authentication = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(
                      signInRequest.getUsername(),
                      signInRequest.getPassword())
              );
    } catch (AuthenticationException ex) {
      Map<String, Object> map = new HashMap<>();
      map.put("message", ex.getMessage());
      map.put("status", false);
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtUtils.generateTokenFromUsername(userDetails);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

    return new SignInResponse(token, userDetails.getUsername(), roles);
  }
}
