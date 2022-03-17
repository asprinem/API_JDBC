package homework;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Spartan;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
/**
 * @author ybilgin
 * @project API_JDBC
 */

//Homework-2
//-Create one mackaroo api for name,gender,phone
//send get request to retrieve random info from that api
//use those info to send post request to spartan

public class HW_2 {

    @BeforeClass
    public void before(){
        baseURI = ConfigurationReader.get("mock_url");
    }

    @Test
    public void test(){
        Random rc = new Random();
        Response response = given().accept(ContentType.JSON)
                .queryParam("key", "4683d2b0")
                .when().get(ConfigurationReader.get("mock_url"));

        List<Map<String,Object>> allMockList = response.body().as(List.class);

        Response postResponse = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(allMockList.get(rc.nextInt(10)+1))
                .when()
                .post(ConfigurationReader.get("spartan_api_url") + "/api/spartans");

        assertEquals(postResponse.statusCode(),201);

        //get request
        int idFromPost = postResponse.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
        given().accept(ContentType.JSON)
                .pathParam("id", idFromPost)
                .when().get(ConfigurationReader.get("spartan_api_url") + "/api/spartans/{id}")
                .then().statusCode(200).log().all();
        
    }
}
