package br.api.spark.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import br.api.spark.dao.OrderProductDao;
import br.api.spark.model.Order;
import br.api.spark.model.OrderProduct;
import br.api.spark.model.Product;

public class Sql2oOrderProductDao implements OrderProductDao {

	private final Sql2o sql2o;

    public Sql2oOrderProductDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }
    
    @Override
	public List<OrderProduct> getAll(){
    	 try(Connection con = sql2o.open()){
             return con.createQuery("SELECT * FROM orders_products")
                     .executeAndFetch(OrderProduct.class);
         }
    }
    
	@Override
	public void add(OrderProduct orderProduct) {
		 
		String sql = "INSERT INTO orders_products (orderId, productId, quantity) "
	        		+ "VALUES (:orderId, :productId, :quantity)";
		
		 try (Connection con = sql2o.open()) {
			 	con.createQuery(sql)
				.addParameter("orderId", orderProduct.getOrderId())
				.addParameter("productId", orderProduct.getProductId())
				.addParameter("quantity", orderProduct.getQuantity())
				.executeUpdate();
			
		} catch (Sql2oException ex) {
			System.out.println(ex);
		}

	}

	@Override
	public List<Product> findAllByOrderId(int orderId) {
		
		List<Product> products = new ArrayList<>();
		
		 String joinQuery = "SELECT productId FROM orders_products WHERE orderId = :orderId";

		try (Connection con = sql2o.open()) {

			List<Integer> allProductsIds = con.createQuery(joinQuery).addParameter("orderId", orderId)
					.executeAndFetch(Integer.class);

			for (Integer productId : allProductsIds) {

				String productQuery = "SELECT * FROM products WHERE id = :productId";

				products.add(con.createQuery(productQuery).addParameter("productId", productId)
						.executeAndFetchFirst(Product.class));
			}

		} catch (Sql2oException ex) {
			System.out.println(ex);
		}
		
		
		return products;
	}

	@Override
	public List<Order> findAllByProductId(int productId) {
		
		List<Order> products = new ArrayList<>();
		
		 String joinQuery = "SELECT orderId FROM orders_products WHERE productId = :productId";

		try (Connection con = sql2o.open()) {

			List<Integer> allOrdersIds = con.createQuery(joinQuery).addParameter("productId", productId)
					.executeAndFetch(Integer.class);

			for (Integer orderId : allOrdersIds) {

				String orderQuery = "SELECT * FROM orders WHERE id = :orderId";

				products.add(con.createQuery(orderQuery).addParameter("orderId", orderId)
						.executeAndFetchFirst(Order.class));
			}

		} catch (Sql2oException ex) {
			System.out.println(ex);
		}
		
		
		return products;
	}

	@Override
	public void deleteByOrderId(int orderId) {
		
		String sql = "DELETE from orders_products WHERE orderId=:orderId";

		try (Connection con = sql2o.open()) {
			
			con.createQuery(sql)
				.addParameter("orderId", orderId).executeUpdate();
			
		} catch (Sql2oException ex) {
			System.out.println(ex);
		}

	}

}
