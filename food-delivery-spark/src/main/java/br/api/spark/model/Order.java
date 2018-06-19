package br.api.spark.model;

import java.time.LocalDate;

import br.api.spark.model.domain.TypeStatus;

public class Order implements Comparable<Order> {

	private int id;

	private int userId;

	private String orderAddress;

	private LocalDate orderDate;

	private Integer orderStatus;

	public Order(int userId, String orderAddress, Integer orderStatus) {
		this.userId = userId;
		this.orderAddress = orderAddress;
		this.orderStatus = orderStatus;
		orderDate = LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusDescription() {
		return TypeStatus.getByCode(orderStatus).getDescription();
	}

	public int compareTo(Order orderObject) {
		if (this.orderDate.isBefore(orderObject.orderDate)) {
			return -1; // this object was made earlier than the second object.
		} else if (this.orderDate.isAfter(orderObject.orderDate)) { // this object was made later than the second object
			return 1;
		} else {
			return 0; // they were made at the same time, which is very unlikely, but mathematically
						// not impossible.
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Order review = (Order) o;

		if (userId != review.userId)
			return false;
		if (!orderAddress.equals(review.orderAddress))
			return false;
		if (!orderDate.equals(review.orderDate))
			return false;
		return orderStatus.equals(review.orderStatus);
	}

	@Override
	public int hashCode() {
		int result = userId;
		result = 31 * result + orderAddress.hashCode();
		result = 31 * result + orderDate.hashCode();
		result = 31 * result + orderStatus.hashCode();
		return result;
	}

}
