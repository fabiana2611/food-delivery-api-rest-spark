package br.api.spark.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.List;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oFoodtypeDao;
import br.api.spark.dao.impl.Sql2oRestaurantDao;
import br.api.spark.dao.impl.Sql2oReviewDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.Foodtype;
import br.api.spark.model.Restaurant;
import br.api.spark.model.Review;

/**
 * Reference: https://www.learnhowtoprogram.com/java
 * 
 * @author fabiana
 *
 */
public class RestaurantController {

	static Gson gson;

	static Sql2oFoodtypeDao foodtypeDao;
	static Sql2oRestaurantDao restaurantDao;
	static Sql2oReviewDao reviewDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		restaurantDao = new Sql2oRestaurantDao(sql2o);
		foodtypeDao = new Sql2oFoodtypeDao(sql2o);
		reviewDao = new Sql2oReviewDao(sql2o);
	}

	private RestaurantController() {
		
	}

	public static void posts() {
		// CREATE

		post("/restaurants/new", "application/json", (req, res) -> {
			Restaurant restaurant = gson.fromJson(req.body(), Restaurant.class);
			restaurantDao.add(restaurant);
			res.status(201);
			return gson.toJson(restaurant);
		});

		post("/restaurants/:restaurantId/reviews/new", "application/json", (req, res) -> {
			int restaurantId = Integer.parseInt(req.params("restaurantId"));
			Review review = gson.fromJson(req.body(), Review.class);
			review.setCreatedat(); // I am new!
			review.setFormattedCreatedAt();
			review.setRestaurantId(restaurantId);
			reviewDao.add(review);
			res.status(201);
			return gson.toJson(review);
		});

		post("/foodtypes/new", "application/json", (req, res) -> {
			Foodtype foodtype = gson.fromJson(req.body(), Foodtype.class);
			foodtypeDao.add(foodtype);
			res.status(201);
			return gson.toJson(foodtype);
		});

		post("/restaurants/:restaurantId/foodtype/:foodtypeId", "application/json", (req, res) -> {
			int restaurantId = Integer.parseInt(req.params("restaurantId"));
			int foodtypeId = Integer.parseInt(req.params("foodtypeId"));
			Restaurant restaurant = restaurantDao.findById(restaurantId);
			Foodtype foodtype = foodtypeDao.findById(foodtypeId);

			if (restaurant != null && foodtype != null) {
				// both exist and can be associated - we should probably not connect things that
				// are not here.
				foodtypeDao.addFoodtypeToRestaurant(foodtype, restaurant);
				res.status(201);
				return gson.toJson(String.format("Restaurant '%s' and Foodtype '%s' have been associated",
						foodtype.getName(), restaurant.getName()));
			} else {
				throw new ApiException(404, String.format("Restaurant or Foodtype does not exist"));
			}
		});
	}
	
	public static void gets() {
		// READ

		get("/restaurants", "application/json", (req, res) -> { // accept a request in format JSON from an app
			// res.type("application/json");
			return gson.toJson(restaurantDao.getAll());// send it back to be displayed
		});

		get("/restaurants/:id", "application/json", (req, res) -> { // accept a request in format JSON from an app
			// res.type("application/json");
			int restaurantId = Integer.parseInt(req.params("id"));
			res.type("application/json");
			return gson.toJson(restaurantDao.findById(restaurantId));
		});

		get("/restaurants/:id/reviews", "application/json", (req, res) -> {
			int restaurantId = Integer.parseInt(req.params("id"));

			Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
			List<Review> allReviews;

			if (restaurantToFind == null) {
				throw new ApiException(404,
						String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
			}

			allReviews = reviewDao.getAllReviewsByRestaurant(restaurantId);

			return gson.toJson(allReviews);
		});

		get("/restaurants/:id/sortedReviews", "application/json", (req, res) -> { 
			int restaurantId = Integer.parseInt(req.params("id"));
			Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
			List<Review> allReviews;
			if (restaurantToFind == null) {
				throw new ApiException(404,
						String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
			}
			allReviews = reviewDao.getAllReviewsByRestaurantSortedNewestToOldest(restaurantId);
			return gson.toJson(allReviews);
		});

		get("/restaurants/:id/foodtypes", "application/json", (req, res) -> {
			int restaurantId = Integer.parseInt(req.params("id"));
			Restaurant restaurantToFind = restaurantDao.findById(restaurantId);
			if (restaurantToFind == null) {
				throw new ApiException(404,
						String.format("No restaurant with the id: \"%s\" exists", req.params("id")));
			} else if (restaurantDao.getAllFoodtypesByRestaurant(restaurantId).size() == 0) {
				return "{\"message\":\"I'm sorry, but no foodtypes are listed for this restaurant.\"}";
			} else {
				return gson.toJson(restaurantDao.getAllFoodtypesByRestaurant(restaurantId));
			}
		});

		get("/foodtypes/:id/restaurants", "application/json", (req, res) -> {
			int foodtypeId = Integer.parseInt(req.params("id"));
			Foodtype foodtypeToFind = foodtypeDao.findById(foodtypeId);
			if (foodtypeToFind == null) {
				throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", req.params("id")));
			} else if (foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId).size() == 0) {
				return "{\"message\":\"I'm sorry, but no restaurants are listed for this foodtype.\"}";
			} else {
				return gson.toJson(foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId));
			}
		});

		get("/foodtypes", "application/json", (req, res) -> {
			return gson.toJson(foodtypeDao.getAll());
		});

		get("/foodtypes/:id", "application/json", (req, res) -> {
			int foodtypeId = Integer.parseInt(req.params("id"));
			Foodtype foodtypeToFind = foodtypeDao.findById(foodtypeId);
			if (foodtypeToFind == null) {
				throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(foodtypeDao.getAllRestaurantsForAFoodtype(foodtypeId));
			}
		});
	}
	
	public static void deletes() {
		delete("/foodtypes/:id", (req, res) -> {
			int foodtypeId = Integer.parseInt(req.params("id"));
			foodtypeDao.deleteById(foodtypeId);
			return "Foodtype deleted.";
		});
		
		delete("/restaurants/:id", (req, res) -> {
			int restaurantId = Integer.parseInt(req.params("id"));
			restaurantDao.deleteById(restaurantId);
			return "Restaurant deleted.";
		});
		
		delete("/reviews/:id", (req, res) -> {
			int reviewsId = Integer.parseInt(req.params("id"));
			reviewDao.deleteById(reviewsId);
			return "Review deleted.";
		});
	}
}
