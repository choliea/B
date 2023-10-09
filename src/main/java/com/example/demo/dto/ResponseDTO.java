package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> { //HTTP 응답으로 사용할 DTO 
	private String error;
	private List<T> data;//다른 모델의 DTO도 담을 수 있게 제네릭
	//보통 여러개의 데이터를 리스트에 담아 처리하기 때문에 리스트 
}
