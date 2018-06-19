import static spark.Spark.after;
import static spark.Spark.exception;

import java.util.HashMap;
import java.util.Map;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.google.gson.Gson;

import br.api.spark.controller.OrderController;
import br.api.spark.controller.OrderProductController;
import br.api.spark.controller.ProductController;
import br.api.spark.controller.RestaurantController;
import br.api.spark.controller.RoleController;
import br.api.spark.controller.UserController;
import br.api.spark.exception.ApiException;

public class App {

	static Connection conn;
	static Gson gson = new Gson();

	public static void main(String[] args) {

		String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
		Sql2o sql2o = new Sql2o(connectionString, "", "");
		
		RestaurantController.init(gson, sql2o);
		ProductController.init(gson, sql2o);
		OrderController.init(gson, sql2o);
		OrderProductController.init(gson, sql2o);
		UserController.init(gson, sql2o);
		RoleController.init(gson, sql2o);
		
		conn = sql2o.open();
		
		RestaurantController.posts();
		RestaurantController.gets();
		RestaurantController.deletes();
		
		ProductController.posts();
		ProductController.gets();
		ProductController.deletes();
		
		OrderController.posts();
		OrderController.gets();
		OrderController.deletes();
		
		OrderProductController.posts();
		OrderProductController.gets();
		
		UserController.posts();
		UserController.gets();
		UserController.deletes();
		
		RoleController.posts();
		RoleController.gets();
		RoleController.deletes();
		
		// FILTERS

		exception(ApiException.class, (exception, req, res) -> {
			ApiException err = (ApiException) exception;
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("status", err.getStatusCode());
			jsonMap.put("errorMessage", err.getMessage());
			res.type("application/json");
			res.status(err.getStatusCode());
			res.body(gson.toJson(jsonMap));
		});

		after((req, res) -> {
			res.type("application/json");
		});
	}

}
