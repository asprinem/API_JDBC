package apitests_2;

/**
 * @author ybilgin
 * @project JDBC
 */
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookItAuthTest {

    @BeforeClass
    public void before() {
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMzAiLCJhdWQiOiJzdHVkZW50LXRlYW0tbGVhZGVyIn0.3YSCwcTXRcEygBm7LvBLb6_D8jU5WXjAD6E3VA9oh0o";
    @Test
    public void getAllCampuses() {

        Response response = given().header("Authorization",accessToken).
                when().get("/api/campuses");

        response.prettyPrint();
        System.out.println(response.statusCode());
    }

}