package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.RandomArticle;
import utils.Token;

import static io.restassured.RestAssured.*;

public class ArticleService {
    public int articlesCount;

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
    public void postNewRandomArticle(){
        Token.setToken();
        RandomArticle.setArticle();

        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                body(RandomArticle.getArticle()).
                when().
                post("/articles").
                then().
                spec(responseSpecification);
    }
}
