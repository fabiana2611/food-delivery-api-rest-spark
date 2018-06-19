package restaurant.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import br.api.spark.dao.impl.Sql2oRestaurantDao;
import br.api.spark.dao.impl.Sql2oReviewDao;
import br.api.spark.model.Restaurant;
import br.api.spark.model.Review;

public class Sql2oReviewDaoTest {

	private Connection conn;
	private Sql2oReviewDao reviewDao;
	private Sql2oRestaurantDao restaurantDao;

	@Before
	public void setUp() throws Exception {
		String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
		Sql2o sql2o = new Sql2o(connectionString, "", "");
		reviewDao = new Sql2oReviewDao(sql2o);
		restaurantDao = new Sql2oRestaurantDao(sql2o);
		conn = sql2o.open();
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}

	@Test
	public void addingReviewSetsId() throws Exception {
		Restaurant testRestaurant = setupRestaurant();
		restaurantDao.add(testRestaurant);
		Review testReview = new Review("Captain Kirk", 3, "foodcoma!", testRestaurant.getId());
		int originalReviewId = testReview.getId();
		reviewDao.add(testReview);
		assertNotEquals(originalReviewId, testReview.getId());
	}

	@Test
	public void deleteById() throws Exception {
	}

	@Test
	public void getAllReviewsByRestaurant() throws Exception {
		Restaurant testRestaurant = setupRestaurant();
		restaurantDao.add(testRestaurant);
		Review testReview = new Review("Captain Kirk", 3, "foodcoma!", testRestaurant.getId());
		reviewDao.add(testReview);
		assertEquals(1, reviewDao.getAllReviewsByRestaurant(testRestaurant.getId()).size());
	}

	@Test
	public void timeStampIsReturnedCorrectly() throws Exception {
		Restaurant testRestaurant = setupRestaurant();
		restaurantDao.add(testRestaurant);
		Review testReview = new Review("Captain Kirk", 3, "foodcoma!", testRestaurant.getId());
		reviewDao.add(testReview);

		long creationTime = testReview.getCreatedat();
		long savedTime = reviewDao.getAll().get(0).getCreatedat();
		String formattedCreationTime = testReview.getFormattedCreatedAt();
		String formattedSavedTime = reviewDao.getAll().get(0).getFormattedCreatedAt();
		assertEquals(formattedCreationTime, formattedSavedTime);
		assertEquals(creationTime, reviewDao.getAll().get(0).getCreatedat());
	}

	@Test
	public void reviewsAreReturnedInCorrectOrder() throws Exception {
		Restaurant testRestaurant = setupRestaurant();
		restaurantDao.add(testRestaurant);
		Review testReview = new Review("Captain Kirk", 3, "foodcoma!", testRestaurant.getId());
		reviewDao.add(testReview);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		Review testSecondReview = new Review("Mr Spock", 1, "passable", testRestaurant.getId());
		reviewDao.add(testSecondReview);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		Review testThirdReview = new Review("Scotty", 4, "bloody good grub!", testRestaurant.getId());
		reviewDao.add(testThirdReview);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		Review testFourthReview = new Review("Sulu", 2, "I prefer home cooking", testRestaurant.getId());
		reviewDao.add(testFourthReview);
		assertEquals(4, reviewDao.getAllReviewsByRestaurant(testRestaurant.getId()).size());
		assertEquals("I prefer home cooking",
				reviewDao.getAllReviewsByRestaurantSortedNewestToOldest(testRestaurant.getId()).get(0).getContent());
	}

	// helpers

	public Restaurant setupRestaurant() {
		return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com",
				"hellofishy@fishwitch.com");
	}
}
