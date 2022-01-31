package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;
import utils.Slug;
import utils.Token;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.CoreMatchers.equalTo;

@OrderWith(Alphanumeric.class)
public class TestFavoriteService {

    @BeforeClass
    public static void setUp(){

        baseURI = "https://api.realworld.io";
        basePath = "/api";
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void testA_validateFavoritesIsZero(){

        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                get("/articles/"+ Slug.getSlug()).
                then().
                body("article.favoritesCount", equalTo(0));

    }

    @Test
    public void testB_postFavoriteAndFavoritesCountShouldBe1(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                post("/articles/"+Slug.getSlug()+"/favorite").
                then().
                statusCode(200).
                body("article.favoritesCount", equalTo(1));
                
    }

    @Test
    public void testC_deleteFavoriteAndFavoritesCountShouldBe0(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                delete("/articles/"+Slug.getSlug()+"/favorite").
                then().
                statusCode(200).
                body("article.favoritesCount", equalTo(0));

    }

    @Test
    public void testD_deleteArticleShouldBeOK(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                delete("/articles/"+Slug.getSlug()).
                then().
                statusCode(204);

    }



}
