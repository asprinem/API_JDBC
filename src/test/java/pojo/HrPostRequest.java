package pojo;

/**
 * @author ybilgin
 * @project JDBC
 */

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class HrPostRequest {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void PostRegion(){
        RegionPost regionPost = new RegionPost(6,"Australia");

        given().log().all()
                .and()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .body(regionPost)
                .when().post("/regions/")
                .then().log().all()
                .statusCode(201)
                .body("region_id",is(6));
    }
}
