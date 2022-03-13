package apitests;

/**
 * @author ybilgin
 * @project JDBC
 */


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class jsonToJavaCollection {

    @BeforeClass
    public void beforeclass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void SpartanToMap() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //we will convert json response to java map
        Map<String,Object> jsonDataMap = response.body().as(Map.class);
        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));

        System.out.println("phone = " + phone);
    }

    @Test
    public void allSpartansToListOfMap(){
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        List<Map<String,Object>> allSpartanList = response.body().as(List.class);
        System.out.println("allSpartanList = " + allSpartanList);

        System.out.println(allSpartanList.get(1).get("name"));

        Map<String,Object> spratan3 = allSpartanList.get(2);
        System.out.println(spratan3);

    }

    @Test
    public void regionToMap(){
        Response response = when().get("http://3.87.87.218:1000/ords/hr/regions");

        assertEquals(response.statusCode(),200);

        //we de-serialize JSON response to Map
        Map<String,Object> regionMap = response.body().as(Map.class);

        System.out.println(regionMap.get("count"));
        System.out.println(regionMap.get("hasMore"));
        System.out.println(regionMap.get("items"));

        List<Map<String ,Object>> itemsList = (List<Map<String, Object>>) regionMap.get("items");

        //print first region name
        System.out.println(itemsList.get(0).get("region_name"));
    }
}
