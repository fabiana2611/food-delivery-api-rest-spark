package br.api.spark.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.List;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oOrderProductDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.Order;
import br.api.spark.model.OrderProduct;
import br.api.spark.model.Product;

public class OrderProductController {

	static Gson gson;
	static Sql2oOrderProductDao ordertProductDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		ordertProductDao = new Sql2oOrderProductDao(sql2o);
	}
	
	private OrderProductController() {
	}
	
	public  static void posts() {
		post("/ordersproducts/new", "application/json", (req, res) -> {
			OrderProduct orderProduct = gson.fromJson(req.body(), OrderProduct.class);
			ordertProductDao.add(orderProduct);
			res.status(201);
			return gson.toJson(orderProduct);
		});
		
	}
	
	public static void gets() {
		
		get("/ordersproducts", "application/json", (req, res) -> { 
			return gson.toJson(ordertProductDao.getAll());
		});
		
		get("/orders/:id/products", "application/json", (req, res) -> { // accept a request in format JSON from an app
			int orderId = Integer.parseInt(req.params("id"));
			List<Product> productsByOrder = ordertProductDao.findAllByOrderId(orderId);
			
			if (productsByOrder == null) {
				throw new ApiException(404, String.format("No product to the order id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(productsByOrder);
			}
		});
		
		get("/products/:id/orders", "application/json", (req, res) -> { // accept a request in format JSON from an app
			int productId = Integer.parseInt(req.params("id"));
			List<Order> orderByProduct = ordertProductDao.findAllByProductId(productId);
			
			if (orderByProduct == null) {
				throw new ApiException(404, String.format("No order with product id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(orderByProduct);
			}
		});
		
	}
	
}
