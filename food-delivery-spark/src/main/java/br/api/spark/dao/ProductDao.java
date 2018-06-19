package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.Product;

public interface ProductDao {

	 //create
    void add(Product product); // Q

    //read
    List<Product> getAll(); // we may need this in the future. Even though it does not 100% match a specific user story, it should be implemented.

    List<Product> getAllProductsByFoodtype(int foodtypeId); //E we will implement this NOW :)
    
    Product findById(int id);
//
//    //update
//    //omit for now
//
//    //delete
    void deleteById(int id); //see above
}
