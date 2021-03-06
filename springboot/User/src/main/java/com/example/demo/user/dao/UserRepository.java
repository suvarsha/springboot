package com.example.demo.user.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.user.pojo.User;



public interface UserRepository extends CrudRepository<User,String> {

	User findByUserNameAndPasswordAndConfirmed(String userName,String password,String confirmed);
	List<User> findByUserNameOrPassword(String userName,String password);
	Optional<User> findByEmail(String email);
	

}
