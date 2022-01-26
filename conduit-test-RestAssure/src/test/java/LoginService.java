import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginService {

    @Before
    public void setUp(){
        baseURI = "https://api.realworld.io";
        basePath = "/api";
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void login(){
                given()
                        .when()
                        .body("{\n" +
                                "  \"user\": {\n" +
                                "    \"email\": \"jcam.qvision@gmail.com\",\n" +
                                "    \"password\": \"testerExtremo\"\n" +
                                "  }\n" +
                                "}")
                        .post("/users/login")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body("user.username", equalTo("Donatelo"));
    }
}
