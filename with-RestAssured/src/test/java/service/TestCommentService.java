package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.manipulation.Alphanumeric;
import utils.Comment;
import utils.Slug;
import utils.Token;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasSize;





@OrderWith(Alphanumeric.class)
public class TestCommentService {

    public static int commentId;
    public static int commentCounter;


    @BeforeClass
    public static void setUp(){

        baseURI = "https://api.realworld.io";
        basePath = "/api";
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
//        responseSpecification = new ResponseSpecBuilder()
//                .expectContentType(ContentType.JSON)
//                .build();
    }

    @Test
    public void testA_postNewCommentArticle(){

        Response response =
                given().
                        spec(requestSpecification).
                        header("authorization", "Bearer "+ Token.getToken()).
                        when().
                        get("/articles/"+ Slug.getSlug()+"/comments").
                        then().
                        //contentType(ContentType.JSON).
                        //spec(responseSpecification).
                        and().
                        extract().
                        response();
        List<String> comments = response.path("comments");
        commentCounter = comments.size();

        commentId =
                given().
                        spec(requestSpecification).
                        header("authorization", "Bearer "+ Token.getToken()).
                        body(Comment.getComment()).
                        when().
                        post("/articles/"+Slug.getSlug()+"/comments").
                        then().
                        //spec(responseSpecification).
                        extract().
                        path("comment.id");
    }

    @Test
    public void testB_verifyCommentsHasIncreasedByOne(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                get("/articles/"+Slug.getSlug()+"/comments").
                then().
                //spec(responseSpecification).
                assertThat().
                body("comments", hasSize(commentCounter+1));
    }

    @Test
    public void testC_deleteCommentAndShouldBeOK(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                delete("/articles/"+Slug.getSlug()+"/comments/"+commentId).
                then().
                statusCode(204);
    }
}
