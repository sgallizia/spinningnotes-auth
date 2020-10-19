package it.spinningnotes.auth.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;

import it.spinningnotes.auth.entities.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByEmail(String email);

	

}
