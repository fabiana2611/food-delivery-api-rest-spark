package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.User;

public interface UserDao {

	// create
	void add(User user); // Q
	// read

	List<User> getAll();

	User findById(int id);

	// //delete
	void deleteById(int id); // see above
}
