package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginService {

    @BeforeClass
    public static void setUp(){
        baseURI = "https://api.realworld.io";
        basePath = "/api";
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();

    }

    @Test
    public void login(){
                given()
                        .spec(requestSpecification)
                        .when()
                        .body("{\n" +
                                "  \"user\": {\n" +
                                "    \"email\": \"jcam.qvision@gmail.com\",\n" +
                                "    \"password\": \"testerExtremo\"\n" +
                                "  }\n" +
                                "}")
                        .post("/users/login")
                        .then()
                        .spec(responseSpecification)
                        .body("user.username", equalTo("Donatelo"));
    }
}
