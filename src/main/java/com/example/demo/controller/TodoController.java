package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;
//주로 HTTP 요청과 응답을 어떻게 넘겨받고 리턴하느냐, 외부세계와 통신하기 위한 규약을 정의
@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = service.testService();
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String> builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		try {
			//1.todoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			//2.id를 null로 초기화한다. 생성 당시에는 id가 없어야 한다
			entity.setId(null);
			//3.임시 유저 아이디를 설정해준다. 
			//인가 기능이 아직 없으므로 temporary-user만 로그인 없이 사용 가능한 애플리케이션
			//Authentication Bearer Token을 통해 받은 userId를 넘긴다.
			entity.setUserId(userId);
			//4.서비스를 이용해 Todo 엔터티를 생성한다.
			List<TodoEntity> entities = service.create(entity);
			//5.자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			//6.변환된 todoDTO리스트를 responseDTO에 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			//7.REsponseDTO를 리턴
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			//8.예외 발생시 dto 대신 error에 메시지 넣어 리턴
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO> builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		List<TodoEntity> entities = service.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(userId);
		List<TodoEntity> entities = service.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			entity.setUserId(userId);
			List<TodoEntity> entities = service.delete(entity);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO> builder().data(dtos).build();
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response= ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}
