package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.TodoEntity;
//데이터베이스와 통신하며 필요한 쿼리를 날리고, 해석해 엔티티 오브젝트로 변환
@Repository//JpaRepository는  JPA 인터페이스<T,ID>T=테이블에 매핑할 엔터티 클래스, ID=PK의 타입
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	//JpaRepository는 findById, findAll, save 등 기본적인 operation interface를 제공하고 
	//구현은 스프링 데이터 JPA가 실행 시에 알아서 해준다. >>sql 쿼리 짤 필요 없다. 
	//인터페이스가 구현 클래스 없이 작동하는 이유는 
	//AOP 때문, 스프링은 methodInteceptor라는  AOP 인터페이스를 이용하는데 
	//우리가 JpaRepository의 메소드를 부를때마다 이 메서드 콜을 가로채 가로챈 메서드의 이름을 확인하고 메서드 이름을 기반으로 쿼리를 작성한다. 
	//기본적인 쿼리가 아닌 경우
	List<TodoEntity> findByUserId(String userId);//메서드 이름은 쿼리 UserId, 매게변수 String userId는 쿼리의 Where문에 들어갈 값
	//더 복잡한 쿼리는 @Query 어노테이션을 이용
	
	//이거 왜 안돼;;;
	/*
	 * @Query("SELECT * FROM Todo t WHERE t.userId = ?1")//?1은 매개변수의 순서위치
	 * List<TodoEntity> findByUserIdQuery(String userId);
	 */
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	//메소드 이름 작성법

}
