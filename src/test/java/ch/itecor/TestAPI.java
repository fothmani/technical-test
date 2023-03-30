package ch.itecor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TestAPI {
	@Test
	public void technicalTestAPI() {
		// Exercise 1
		// Get the response token by sending a POST request with the url:
		// https://itecor-app.ch/api/login
		baseURI = "https://itecor-app.ch";
		
		 String jsonBody = "{" +
	                "\"username\": \"User1\"," +
	                "\"password\": \"Password1234\"" +
	        "}";

    	Response response =
    	given()
        	.header("Content-Type", "application/json")
        .and()
        	.body(jsonBody)
		.when()
			.post("/api/login");
String token = response.asString();


		// Exercise 2
		// Use the response token combined with a new post request on the url:
		// https://itecor-app.ch/api/count-seats
			jsonBody = "{" +
			        "\"office\": \"Geneva\"," +
			        "\"token\":" + token  +
			        "}";

		   response =
		   given()
				.header("Content-Type", "application/json")
		    .and()
				 .body(jsonBody)
		     .when()
		         .post("/api/count-seats");
		   
String numberOfBookableSeats = response.asString();
assertThat(numberOfBookableSeats).isEqualTo("\"The Geneva office has 10 bookable seats!\"");

	}
}
