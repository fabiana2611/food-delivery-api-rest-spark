package br.api.spark.dao;

import java.util.List;

import br.api.spark.model.Order;
import br.api.spark.model.OrderProduct;
import br.api.spark.model.Product;

public interface OrderProductDao {

	List<OrderProduct> getAll();
	
	void add(OrderProduct orderProduct); 

	List<Product> findAllByOrderId(int orderId);
	
	List<Order> findAllByProductId(int productId);

	void deleteByOrderId(int orderId); // see above
}
