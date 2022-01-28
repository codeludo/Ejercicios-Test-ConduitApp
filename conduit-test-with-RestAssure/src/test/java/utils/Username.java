package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Username {

    public static String username;

    @Before
    public void setUp(){
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

    public static String getUsername()
    {
        return given()
                .when()
                .body("{\n" +
                        "  \"user\": {\n" +
                        "    \"email\": \"jcam.qvision@gmail.com\",\n" +
                        "    \"password\": \"testerExtremo\"\n" +
                        "  }\n" +
                        "}")
                .post("/users/login")
                .then()
                .extract()
                .jsonPath().getString("user.username");
    }

}
