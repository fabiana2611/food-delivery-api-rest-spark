package br.api.spark.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oUserDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.User;

public class UserController {

	static Gson gson;
	static Sql2oUserDao userDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		userDao = new Sql2oUserDao(sql2o);
	}
	
	private UserController() {
	}
	
	public  static void posts() {
		post("/users/new", "application/json", (req, res) -> {
			User user = gson.fromJson(req.body(), User.class);
			userDao.add(user);
			res.status(201);
			return gson.toJson(user);
		});
		
	}
	
	public static void gets() {
		get("/users", "application/json", (req, res) -> { // accept a request in format JSON from an app
			return gson.toJson(userDao.getAll());// send it back to be displayed
		});
		
		get("/users/getUser/:id", "application/json", (req, res) -> {
			int orderId = Integer.parseInt(req.params("id"));
			User user = userDao.findById(orderId);
			if (user == null) {
				throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(user);
			}
		});
	}
	
	/**
	 * Delete user by id
	 */
	public static void deletes() {
		delete("/users/deleteUser/:id", (req, res) -> {
			int productId = Integer.parseInt(req.params("id"));
			userDao.deleteById(productId);
			res.status(201);
			
			return "";
		});
	}
}
