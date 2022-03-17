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

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

/**
 * @author ybilgin
 * @project API_JDBC
 */

//Homework-1
//1-Create csv file from mackaroo website, which includes name,gender,phone
//2-Download excel file
//3- using testng data provider and apache poi create data driven posting from spartan

public class HW_1 {


    @BeforeClass
    public void beforeclass() {

        baseURI = ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object[][] addSpartan() {
        ExcelUtil spartans = new ExcelUtil("src/test/resources/MOCK_DATA.xlsx", "data");
        String[][] dataArray = spartans.getDataArrayWithoutFirstRow();
        return dataArray;
    }

    @Test(dataProvider = "addSpartan")
    public void testSpartan(String name, String gender, String phone) {

        BigDecimal bdPhone = new BigDecimal(phone);

        Spartan newSpartan = new Spartan();
        newSpartan.setName(name);
        newSpartan.setGender(gender);
        newSpartan.setPhone(bdPhone.longValue());

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(newSpartan)
                .when()
                .post("/api/spartans");

        assertEquals(response.statusCode(), 201);

        //get request
        int idFromPost = response.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
        given().accept(ContentType.JSON)
                .pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();

    }
}
