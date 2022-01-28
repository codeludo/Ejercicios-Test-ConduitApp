package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.RandomArticle;
import utils.Slug;
import utils.Token;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Clase que permite las siguientes peticiones
 * leer y actualizar articulos
 * dar like o quitarlos
 * realizar comentarios y borrarlos.
 *
 * #1 obtener primer articulo (creado anteriormente)
 * #2 validar el esquema
 * #3 actualizar articulo
 * #4 verificar campo actualizado
 * #5 postear un nuevo comentario
 * #6 verificar comentarios+1
 * #7 eliminar comentario
 * #8 verificar comentarios-1
 * #9 postear un like para el articulo
 * #10 verificar likes+1
 * #11 eliminar like
 * #12 verificar likes-1
 * #13 eliminar articulo
 *
 */
public class SlugService {

    public static int commentId;
    public static int commentCounter;
    public static int favoriteCounter;

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
    public void validateArticleSlugResponseSchema(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                get("/articles/"+ Slug.getSlug()).
                then().
                spec(responseSpecification).
                and().
                assertThat().
                body(matchesJsonSchemaInClasspath("json-schemas/article-schema.json"));
    }

    @Test
    public void updateArticleAndShouldBeOK(){
        RandomArticle.setUpdateArticle();
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                body(RandomArticle.getUpdateArticle()).
                when().
                put("/articles/"+Slug.getSlug()).
                then().
                spec(responseSpecification).
                and().
                assertThat().
                body("article.title", equalTo(RandomArticle.getTitle())).
                body("article.description", equalTo(RandomArticle.getDescription())).
                body("article.tagList", equalTo(RandomArticle.getTags()));
    }

    @Test
    public void postNewCommentArticle(){

        Response response =
                given().
                        spec(requestSpecification).
                        header("authorization", "Bearer "+ Token.getToken()).
                        when().
                        get("/articles/"+Slug.getSlug()+"/comments").
                        then().
                        spec(responseSpecification).
                        assertThat().
                        body("comments", hasSize(0)).
                        and().
                        extract().
                        response();
        List<String> comments = response.path("comments");
        commentCounter = comments.size();

         commentId =
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                body(RandomArticle.getComment()).
                when().
                post("/articles/"+Slug.getSlug()+"/comments").
                then().
                spec(responseSpecification).
                extract().
                path("comment.id");
    }

    @Test
    public void verifyCommentsHasIncreasedByOne(){
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+ Token.getToken()).
                when().
                get("/articles/"+Slug.getSlug()+"/comments").
                then().
                spec(responseSpecification).
                assertThat().
                body("comments", hasSize(commentCounter+1));
    }

    @Test
    public void deleteCommentAndShouldBeOK(){
        
    }
}
