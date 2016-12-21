package ru.sandarkin.roox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import ru.sandarkin.roox.security.JwtAuthFilter;
import ru.sandarkin.roox.security.JwtAuthProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(jwtAuthProvider());
/*    auth.inMemoryAuthentication()
        .withUser("user").password("secret").roles("USER").and()
        .withUser("admin").password("secret").roles("USER", "ADMIN");*/
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .exceptionHandling()
//        .and()
//        .authorizeRequests().antMatchers("/h2console/**").anonymous()
        .and()
        .authorizeRequests().antMatchers("/**").authenticated()
        .and()
        .addFilterBefore(jwtAuthFilter(), LogoutFilter.class);
  }

//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers("/h2console/**");
//  }

  @Bean
  public JwtAuthProvider jwtAuthProvider() {
    return new JwtAuthProvider();
  }

  @Bean
  public JwtAuthFilter jwtAuthFilter() {
    return new JwtAuthFilter();
  }




}
