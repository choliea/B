package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.JwtAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	//원래 WebSecurityConfigurerAdapter를 상속받아 configure 메소드를 작성해야하는데
	//deprecate돼서 사용하지 못한다. Bean 컴포넌트로 직접 등록해준다. 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//http 시큐리티 빌더
		  http.httpBasic(HttpBasicConfigurer::disable)
          .csrf(CsrfConfigurer::disable)
          .cors(Customizer.withDefaults())
          .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(authorize ->
                  authorize
                          .requestMatchers("/","/auth/**").permitAll()
                          .anyRequest().authenticated()
          ).addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
  return http.build();
	}
	
}
