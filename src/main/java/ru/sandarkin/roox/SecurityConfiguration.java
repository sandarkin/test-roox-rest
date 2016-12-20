package ru.sandarkin.roox;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("secret").roles("USER").and()
        .withUser("admin").password("secret").roles("USER", "ADMIN");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeRequests().antMatchers("/").authenticated()
        .and()
        .authorizeRequests().antMatchers("/console/**").permitAll();
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
  }

}
