package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.Order;

public interface OrderDao {

	// create
	void add(Order order); // Q
	// read

	List<Order> getAll();

	Order findById(int id);

	// //delete
	void deleteById(int id); // see above
}
