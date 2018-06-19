package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.Review;

public interface ReviewDao {

	//create
    void add(Review review); //G

    //read
    List<Review> getAllReviewsByRestaurant(int restaurantId); // J
    List<Review> getAll();
    List<Review> getAllReviewsByRestaurantSortedNewestToOldest(int restaurantId);

    //update
    //omit for now

    //delete
    void deleteById(int id); //O
}
