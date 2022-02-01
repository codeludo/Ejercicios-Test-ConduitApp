package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Token;
import utils.Username;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

/**
 * test class
 * ver mis articulos validar esquema.
 */
public class TestProfileService {

    @BeforeClass
    public static void setUp(){
        baseURI = "https://api.realworld.io";
        basePath = "/api";
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
//        responseSpecification = new ResponseSpecBuilder().
//                expectStatusCode(200).
//                expectContentType(ContentType.JSON).
//                build();
    }

    @Test
    public void userProfileValidateResponseSchema(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                get("/profiles/"+ Username.getUsername()).
                then().
                //spec(responseSpecification).
                and().
                assertThat().
                body(matchesJsonSchemaInClasspath("json-schemas/profile-schema.json"));

    }


}
