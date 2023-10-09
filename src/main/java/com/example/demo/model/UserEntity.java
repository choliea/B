package com.example.demo.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator( name="system-uuid", strategy = "uuid")
	private String id; // 유저에게 고유하게 부여되는 id
	
	@Column(nullable=false)
	private String username; // 아이디로 사용할 유저네임, 이메일 혹은 문자열
	 
	private String password;
	
	private String role; //어드민, 일반유저
	
	private String authProvider; // OAuth에서 사용할 유저 정보 제공자 : github
}
