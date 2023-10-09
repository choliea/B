package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController // http관련 코드,요청,응답 매핑
public class TestController {

	@GetMapping("/testGetMapping")
	public String testGetMapping() {
		return "hello world test";
	}

	@GetMapping("test")
	public String testController() {
		return "hello world";
	}

	@GetMapping("/{id}")
	public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
		return "hello world id: " + id;
	}

	@GetMapping("/testRequestParam")
	public String testControllerRequestparam(@RequestParam(required = false) int id) {
		return "hello request Param : " + id;
	}

	@GetMapping("/testReqeustBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "hello requestBODY: " + testRequestBodyDTO.getId() + " " + testRequestBodyDTO.getMessage();
	}

	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello world im responseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}

	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("hello world im responseEntity");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(response);
	}
}
