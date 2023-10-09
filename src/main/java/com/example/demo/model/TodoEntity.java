package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity//TodoEntity를 엔터티로 지정하기 위한 어노테이션("TodoEntity")으로 이름 정해줄 수 있다.
@Table(name = "Todo")//테이블 이름을 지정하기 위한 어노테이션 name 지정안해주면 클래스 이름을 테이블 이름으로 간주
public class TodoEntity {
	@Id//기본키
	@GeneratedValue(generator = "system-uuid")//시퀀스 역할, system-uuid라는 밑에서 정의한 generator 사용
	@GenericGenerator(name = "system-uuid", strategy = "uuid")//hibernate 기본 제공 제너레이터 아닌 커스텀 제너레이터, 문자열 형태의 uuid를 사용하기 위해 
	private String id; //오브젝트의 아이디
	private String userId; //오브젝트를 생성한 유저의 아이디
	private String title; //todo 타이틀
	private boolean done; //실행 여부
}
