import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasSize;

public class TagsService {

    public static List<String> tags;

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

    @Test
    public void noLoginUserRequestTags(){
                given().
                        spec(requestSpecification).
                        when().
                        get("tags").
                        then().
                        spec(responseSpecification).
                        and().
                        body("tags", hasSize(4));
    }

    @Test
    public void LoginUserRequestTags(){

        Token.setToken();
        given().
                spec(requestSpecification).
                header("authorization", "Bearer "+Token.getToken()).
                when().
                get("tags").
                then().
                spec(responseSpecification).
                assertThat().
                body("tags", hasSize(5));
    }




}
