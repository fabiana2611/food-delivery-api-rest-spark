package br.api.spark.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import br.api.spark.dao.ProductDao;
import br.api.spark.model.Product;

public class Sql2oProductDao implements ProductDao {

	private final Sql2o sql2o;

    public Sql2oProductDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO products "
        		+ "(productName, productDescription, productPrice, restaurantId, foodtypeId) "
        		+ "VALUES (:productName, :productDescription, :productPrice, :restaurantId, :foodtypeId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(product)
                    .executeUpdate()
                    .getKey();
            product.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Product> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM products")
                    .executeAndFetch(Product.class);
        }
    }

	@Override
	public List<Product> getAllProductsByFoodtype(int foodtypeId) {
		List<Product> products = new ArrayList<>();

		String query = "SELECT * FROM products WHERE foodtypeId = :foodtypeId";

		try (Connection con = sql2o.open()) {
			products = con.createQuery(query)
					.addParameter("foodtypeId", foodtypeId)
					.executeAndFetch(Product.class); 
		} catch (Sql2oException ex) {
			System.out.println(ex);
		}

		return products;
	}

    @Override
    public void deleteById(int id) {
        
    	String sql = "DELETE from products WHERE id=:id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

	@Override
	public Product findById(int id) {
		try(Connection con = sql2o.open()){
          return con.createQuery("SELECT * FROM products WHERE id = :id")
                  .addParameter("id", id)
                  .executeAndFetchFirst(Product.class);
		}
	}

}
