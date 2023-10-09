package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	@GetMapping("/")
	public String healthCheck() {
		return "The service is up and running...";
	}
	/*
	 "/"는 WebSecurityConfig에서 "/"와 "/auth/**"는 인증하지 않도록 설정했기 때문에 인증 없이
	 사용할 수 있다. 만약 그렇지 않으면 http 403를 리턴받아 로드 밸런서가 애플리케이션에 
	 문제가 있다고 판단한다. 
	 */
}
