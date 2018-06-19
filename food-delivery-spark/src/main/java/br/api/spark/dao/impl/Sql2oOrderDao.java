package br.api.spark.dao.impl;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import br.api.spark.dao.OrderDao;
import br.api.spark.dao.OrderProductDao;
import br.api.spark.model.Order;

public class Sql2oOrderDao implements OrderDao {

	private final Sql2o sql2o;
	
    public Sql2oOrderDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Order order) {
    	
        String sql = "INSERT INTO orders "
        		+ "(userId, orderAddress, orderDate, orderStatus) "
        		+ "VALUES (:userId, :orderAddress, :orderDate, :orderStatus)";
        
		try (Connection con = sql2o.open()) {
			int id = (int) con.createQuery(sql, true).bind(order).executeUpdate().getKey();
			order.setId(id);
			
		} catch (Sql2oException ex) {
			System.out.println(ex);
		}
		
    }

    @Override
    public List<Order> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM orders")
                    .executeAndFetch(Order.class);
        }
    }

    @Override
    public void deleteById(int id) {
    	
    		OrderProductDao orderProductDao = new Sql2oOrderProductDao (sql2o);
    		
    		orderProductDao.deleteByOrderId(id);
    	
    		String sql = "DELETE from orders WHERE id=:id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

	@Override
	public Order findById(int id) {
		try(Connection con = sql2o.open()){
          return con.createQuery("SELECT * FROM orders WHERE id = :id")
                  .addParameter("id", id)
                  .executeAndFetchFirst(Order.class);
		}
	}

}
