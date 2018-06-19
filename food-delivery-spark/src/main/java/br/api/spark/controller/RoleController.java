package br.api.spark.controller;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.dao.impl.Sql2oRoleDao;
import br.api.spark.exception.ApiException;
import br.api.spark.model.Role;

public class RoleController {

	static Gson gson;
	static Sql2oRoleDao roleDao;
	
	public static void init(Gson gsonParam, Sql2o sql2o) {
		gson = gsonParam;
		roleDao = new Sql2oRoleDao(sql2o);
	}
	
	private RoleController() {
	}
	
	public  static void posts() {
		post("/roles/new", "application/json", (req, res) -> {
			Role role = gson.fromJson(req.body(), Role.class);
			roleDao.add(role);
			res.status(201);
			return gson.toJson(role);
		});
		
	}
	
	public static void gets() {
		get("/roles", "application/json", (req, res) -> { // accept a request in format JSON from an app
			return gson.toJson(roleDao.getAll());// send it back to be displayed
		});
		
		get("/roles/:id", "application/json", (req, res) -> {
			int orderId = Integer.parseInt(req.params("id"));
			Role role = roleDao.findById(orderId);
			if (role == null) {
				throw new ApiException(404, String.format("No role with the id: \"%s\" exists", req.params("id")));
			} else {
				return gson.toJson(role);
			}
		});
	}
	
	public static void deletes() {
		delete("/roles/:id", (req, res) -> {
			int productId = Integer.parseInt(req.params("id"));
			roleDao.deleteById(productId);
			res.status(201);
			
			return "";
		});
	}
}
