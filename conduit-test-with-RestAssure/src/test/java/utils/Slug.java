package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;

import static io.restassured.RestAssured.*;

public class Slug {

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

    public static String getSlug() {

        return given().
                        spec(requestSpecification).
                        header("authorization", "Bearer " + Token.getToken()).
                        param("limit", 10).
                        param("offset", 0).
                        when().
                        get("/articles").
                        then().
                        extract().
                        path("articles[0].slug");
    }

}
