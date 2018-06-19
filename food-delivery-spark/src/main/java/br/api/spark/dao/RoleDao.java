package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.Role;

public interface RoleDao {

	// create
	void add(Role role); // Q
	// read

	List<Role> getAll();

	Role findById(int id);

	// //delete
	void deleteById(int id); // see above
}
