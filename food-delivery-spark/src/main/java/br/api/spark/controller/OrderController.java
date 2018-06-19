package br.api.spark.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oOrderDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.Order;

public class OrderController {

	static Gson gson;
	static Sql2oOrderDao ordertDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		ordertDao = new Sql2oOrderDao(sql2o);
	}
	
	private OrderController() {
	}
	
	public  static void posts() {
		post("/orders/new", "application/json", (req, res) -> {
			Order order = gson.fromJson(req.body(), Order.class);
			ordertDao.add(order);
			res.status(201);
			// res.type("application/json");
			return gson.toJson(order);
		});
		
	}
	
	public static void gets() {
		get("/orders", "application/json", (req, res) -> { // accept a request in format JSON from an app
			return gson.toJson(ordertDao.getAll());// send it back to be displayed
		});
		
		get("/orders/:id", "application/json", (req, res) -> {
			int orderId = Integer.parseInt(req.params("id"));
			Order order = ordertDao.findById(orderId);
			if (order == null) {
				throw new ApiException(404, String.format("No order with the id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(order);
			}
		});
	}
	
	public static void deletes() {
		delete("/orders/:id", (req, res) -> {
			int productId = Integer.parseInt(req.params("id"));
			ordertDao.deleteById(productId);
			res.status(201);
			
			return "";
		});
	}
}
