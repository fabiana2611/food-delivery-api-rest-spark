package br.api.spark.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.List;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oProductDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.Product;

public class ProductController {

	static Gson gson;
	static Sql2oProductDao productDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		productDao = new Sql2oProductDao(sql2o);
	}
	
	private ProductController() {
	}
	
	public  static void posts() {
		post("/products/new", "application/json", (req, res) -> {
			Product product = gson.fromJson(req.body(), Product.class);
			productDao.add(product);
			res.status(201);
			// res.type("application/json");
			return gson.toJson(product);
		});
		
	}
	
	public static void gets() {
		get("/products", "application/json", (req, res) -> { // accept a request in format JSON from an app
			return gson.toJson(productDao.getAll());// send it back to be displayed
		});
		
		get("/foodtypes/:id/products", "application/json", (req, res) -> {
			int foodtypeId = Integer.parseInt(req.params("id"));
			List<Product> products = productDao.getAllProductsByFoodtype(foodtypeId);
			if (products == null) {
				throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(products);
			}
		});
	}
	
	public static void deletes() {
		delete("/products/:id", (req, res) -> {
			int productId = Integer.parseInt(req.params("id"));
			productDao.deleteById(productId);
			res.status(201);
			
			return "";
		});
	}
}
