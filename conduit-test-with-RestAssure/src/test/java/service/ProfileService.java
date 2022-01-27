package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Token;
import utils.Username;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.Username.username;

/**
 * test class
 * ver mis articulos validar esquema.
 */
public class ProfileService {

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
    public void userProfileValidateResponseSchema(){
        Token.setToken();
        Username.setUsername();
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                get("/profiles/"+ Username.getUsername()).
                then().
                spec(responseSpecification).
                and().
                assertThat().
                body(matchesJsonSchemaInClasspath("json-schemas/profile-schema.json"));

    }


}
