package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;
//HTTP나 데이터베이스 같은 외부 컴포넌트로부터 추상화되어 온전히 비즈니스 로직에 집중
@Slf4j//로깅하는 어노테이션
@Service//1. 검증(이부분이 커지면 TodoValidator로 따로 뺄수도 있다.
//2.save():엔터티를 데이터베이스에 저장, 로그 남김
//3.findByUserId() : 저장된 엔티티를 포함하는 새 리스트를 리턴 
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public List<TodoEntity> create(final TodoEntity entity){
		validate(entity);
		
		repository.save(entity);
		
		log.info("entity id: {} is saved",entity.getId());
		
		return repository.findByUserId(entity.getUserId());
	}
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity){
		//1.저장할 엔티티가 유효한지 확인
		validate(entity);
		//2.넘겨받은 엔티티 id를 이용해 todoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트할 수 없기 때문
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		original.ifPresent(todo ->{
			//3.반환된 todoEntity가 존재하면 값을 새entity의 값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			//4.데이터베이스에 새 값을 저장
			repository.save(todo);
		});
		return retrieve(entity.getUserId());//유저의 모든 todo리스트 리턴
	}/*
	Optional과 람다식 사용*/
	/*public List<TodoEntity> update(final TodoEntity entity){
		validate(entity);
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		if(original.isPresent()) {
			final TodoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			repository.save(todo);
		}
		return retrieve(entity.getUserId());
	}*/
	public List<TodoEntity> delete(final TodoEntity entity){
		validate(entity);
		try {
			repository.delete(entity);
		}catch(Exception e) {
			//exception 발생시 id와 exception을 로깅
			log.error("error deleting entity",entity.getId(),e);
			//컨트롤러로 exception날린다.데이터베이스 내부 로직을 캡슐홯기 위해 e를 리턴하지 않고 새 exception오브젝트를 리턴
			throw new RuntimeException("error deleting entity"+entity.getId());
		}
		return retrieve(entity.getUserId());
	}
	private void validate(final TodoEntity entity) {//entity의 조작(재할당)을 막기 위해 final
		if(entity==null) {
			log.warn("entity cannot be null");
			throw new RuntimeException("entity cannot be null");
		}
		if(entity.getUserId()==null) {
			log.warn("unknown user");
			throw new RuntimeException("unknown user");
		}
	}
	
	public String testService() {
		//TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("Myfirst todo item").build();
		//TodoEntity 저장
		repository.save(entity);
		//TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
	}
}
