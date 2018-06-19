package br.api.spark.model;

public class OrderProduct {

	private int id;

	private int orderId;

	private int productId;

	private Integer quantity;

	public OrderProduct(int orderId, int productId, Integer quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOrderProductId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OrderProduct review = (OrderProduct) o;

		if (quantity != review.quantity)
			return false;
		if ( productId != review.productId)
			return false;
		if (orderId != review.orderId)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = orderId;
		result = 31 * result + productId ;
		result = 31 * result + quantity;
		return result;
	}
}
