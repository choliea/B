package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	//DTO에는 보안상의 이유로 userId를 포함하지 않는다. 
	//스프링 시큐리티를 이용해 인증이 가능
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {//처리한 후 다시 유저한테 보내는 entity를 dto로 바꿔서 보내줌
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	public static TodoEntity toEntity(final TodoDTO dto) {//dto로 요청을 받아서 entity로 바꿔 서비스 처리하고 
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone()).build();
		
	}
}
