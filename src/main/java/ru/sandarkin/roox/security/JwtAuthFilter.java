package ru.sandarkin.roox.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

  /**
   * Method check request for Bearer and push it to provider.
   */
  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith("Bearer ")) {
      JwtAuthToken token = new JwtAuthToken(header.replaceAll("Bearer ", ""));
      SecurityContextHolder.getContext().setAuthentication(token);
    }
    chain.doFilter(request, response);
  }

}
