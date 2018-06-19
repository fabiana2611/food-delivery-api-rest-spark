package br.api.spark.model;

public class Product {

	private int id;
	private String productName;
	private String productDescription;
	private Double productPrice;
    private int restaurantId;
    private int foodtypeId;
	
    public Product(String productName, String productDescription, Double productPrice, int restaurantId, int foodtypeId) {
	    	this.productName = productName;
	    	this.productDescription = productDescription;
	    	this.productPrice = productPrice;
	    	this.restaurantId = restaurantId;
	    	this.foodtypeId = foodtypeId;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public int getFoodtypeId() {
		return foodtypeId;
	}

	public void setFoodtypeId(int foodtypeId) {
		this.foodtypeId = foodtypeId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product that = (Product) o;

        if (id != that.id) return false;
        if (!productName.equals(that.productName)) return false;
        if (!productDescription.equals(that.productDescription)) return false;
        if (!productPrice.equals(that.productPrice)) return false;
        if (restaurantId  != that.restaurantId) return false;
        if (foodtypeId  != that.foodtypeId) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = productName.hashCode();
        result = 31 * result + productDescription.hashCode();
        result = 31 * result + productPrice.hashCode();
        result = 31 * result + restaurantId;
        result = 31 * result + foodtypeId;
        result = 31 * result + id;
        return result;
    }

}
