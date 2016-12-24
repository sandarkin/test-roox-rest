package ru.sandarkin.roox.config;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import ru.sandarkin.roox.security.JwtAuthFilter;
import ru.sandarkin.roox.security.JwtAuthProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer realm=\"roox\""))
        .and()
        .authorizeRequests().antMatchers("/**").authenticated()
        .and()
        .addFilterBefore(jwtAuthFilter(), LogoutFilter.class);
  }

  @Bean
  public JwtAuthProvider jwtAuthProvider() {
    return new JwtAuthProvider();
  }

  @Bean
  public JwtAuthFilter jwtAuthFilter() {
    return new JwtAuthFilter();
  }

  @Bean
  EvaluationContextExtension securityExtension() {
    return new SecurityEvaluationContextExtension();
  }

}
