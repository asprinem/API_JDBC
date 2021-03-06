package apitests_2;

/**
 * @author ybilgin
 * @project JDBC
 */

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class PUTRequestDemo {

    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void test1(){
        //Create one map for the put request json body
        Map<String,Object> putRequestMap = new HashMap<>();
        putRequestMap.put("name","PutName");
        putRequestMap.put("gender","Male");
        putRequestMap.put("phone",1231312321l);

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",116)
                .and()
                .body(putRequestMap).
                when()
                .put("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

        //send get request to verify body

    }

    @Test
    public void PatchTest(){

        //Create one map for the patch request json body
        Map<String,Object> patchRequestMap = new HashMap<>();
        patchRequestMap.put("name","TJ");

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",116)
                .and()
                .body(patchRequestMap).
                when()
                .patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

    }
}
