package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;
import utils.RandomArticle;
import utils.Token;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;


@OrderWith(Alphanumeric.class)
public class TestArticleService {

    public static int articlesCount;

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
    public void testA_numberOfArticlesBeforePostShouldBeN(){
                given().
                        spec(requestSpecification).
                        header("authorization", "Bearer "+ Token.getToken()).
                        param("limit", 10).
                        param("offset", 0).
                        when().
                        get("/articles").
                        then().
                        body("articlesCount", equalTo(3)).
                        //spec(responseSpecification).
                        extract().
                        path("articlesCount");

        Response response =
                given().
                        spec(requestSpecification).
                        header("authorization", "Bearer " + Token.getToken()).
                        param("limit", 10).
                        param("offset", 0).
                        when().
                        get("/articles").
                        then().
                        extract().
                        response();
                        articlesCount = response.path("articlesCount");
    }

    @Test
    public void testB_postNewRandomArticle(){
        RandomArticle.setArticle();

        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                body(RandomArticle.getArticle()).
                when().
                post("/articles").
                then().
                statusCode(200);
                //spec(responseSpecification);
    }
    @Test
    public void testC_numberOfArticlesAfterPostShouldBeNplus1() {
        given().
                spec(requestSpecification).
                header("authorization", "Bearer " + Token.getToken()).
                param("limit", 10).
                param("offset", 0).
                when().
                get("/articles").
                then().
                body("articlesCount", equalTo(articlesCount+1)).
                statusCode(200);
                //spec(responseSpecification);
    }
}
