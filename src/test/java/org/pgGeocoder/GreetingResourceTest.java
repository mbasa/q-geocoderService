package org.pgGeocoder;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testJsonOutputEndpoint() {
        given()
          .when().get("/service/geocode/json/杉並区荻窪１−１ー２")
          .then()
             .statusCode(200);
             //.body(is("Hello from RESTEasy Reactive"));
    }

}