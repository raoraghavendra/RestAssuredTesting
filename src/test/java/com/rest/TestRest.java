package com.rest;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestRest {
	// PMAK-63bda235ce7710429b1947f1-e130e0e03dcb7e301743018ea140eb19f6

	@Test
	public static void test() {

		// RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		
		//POST

		String response = given().log().all().baseUri("https://rahulshettyacademy.com").queryParam("key", " qaclick123")
				.header("Content-Type", " application/json").body(payload.body()).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).header("Server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();
		System.out.println(response);

		JsonPath js = new JsonPath(response);

		String place = js.getString("place_id");

		System.out.println(place);
		
		//PUT
		
		String expected = "Test" ;

		given().log().all().baseUri("https://rahulshettyacademy.com").queryParam("key", "qaclick123")
				.header("Content-Type", " application/json")
				.body("{\r\n" + "\"place_id\":\"" + place + "\",\r\n" + "\"address\":\""+expected+"\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		//GET

		String getPlace = given().log().all().baseUri("https://rahulshettyacademy.com").queryParam("key", "qaclick123")
				.queryParam("place_id", place).when().get("maps/api/place/get/json").then().assertThat().log().all()
				.statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(getPlace);

		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, expected);

	}

}
