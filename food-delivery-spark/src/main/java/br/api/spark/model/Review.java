package br.api.spark.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review implements Comparable<Review> {

	private String content;
	  private String writtenBy;
	  private int rating;
	  private int id;
	  private int restaurantId; //will be used to connect Restaurant to Review (one-to-many)
	  private long createdat;
	  private String formattedCreatedAt;

	  public Review(String writtenBy, int rating, String content, int restaurantId) {
	        this.writtenBy = writtenBy;
	        this.rating = rating;
	        this.content = content;
	        this.restaurantId = restaurantId;
	        this.createdat = System.currentTimeMillis();
	        setFormattedCreatedAt();
	    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWrittenBy() {
		return writtenBy;
	}

	public void setWrittenBy(String writtenBy) {
		this.writtenBy = writtenBy;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	  
	public String getFormattedCreatedAt(){
	    Date date = new Date(createdat);
	    String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
	    SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
	    return sdf.format(date);
	}

	public void setFormattedCreatedAt(){
	    Date date = new Date(this.createdat);
	    String datePatternToUse = "MM/dd/yyyy @ K:mm a";
	    SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
	    this.formattedCreatedAt = sdf.format(date);
	}

	public long getCreatedat() {
	    return createdat;
	}

	public void setCreatedat() {
	    this.createdat = System.currentTimeMillis(); // It'll become clear soon why we need this explicit setter
	}

	public int compareTo(Review reviewObject) {
		if (this.createdat < reviewObject.createdat) {
			return -1; // this object was made earlier than the second object.
		} else if (this.createdat > reviewObject.createdat) { // this object was made later than the second object
			return 1;
		} else {
			return 0; // they were made at the same time, which is very unlikely, but mathematically
						// not impossible.
		}
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (rating != review.rating) return false;
        if (restaurantId != review.restaurantId) return false;
        if (!writtenBy.equals(review.writtenBy)) return false;
        return content.equals(review.content);
    }

    @Override
    public int hashCode() {
        int result = writtenBy.hashCode();
        result = 31 * result + rating;
        result = 31 * result + content.hashCode();
        result = 31 * result + restaurantId;
        return result;
    }

	
}
