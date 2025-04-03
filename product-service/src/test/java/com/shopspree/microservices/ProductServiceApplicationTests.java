package com.shopspree.microservices;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}

	static{
		mongoDBContainer.start();
	}


	@Test
	void shouldCreateProduct() {
		String requestBody = """
								{
				        "name":"iPhone 15",
				        "description":"Apple smartphone",
				        "price":"50000"
				    }
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				//assert certain field for example below checking if id is not null
				.body("id", Matchers.notNullValue())
				.body("name",Matchers.equalTo("iPhone 15"))
				.body("description",Matchers.equalTo("Apple smartphone"))
				.body("price",Matchers.equalTo(50000));
	}

}


