package apitests_2;

/**
 * @author ybilgin
 * @project API_JDBC
 */


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Spartan;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

import static io.restassured.RestAssured.baseURI;

public class SpartanFlow {

    int id;

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test(priority = 1)
    public void POSTNewSpartan(){
        Spartan spartan = new Spartan();
        spartan.setName("ysn");
        spartan.setGender("Male");
        spartan.setPhone(1231231231);

        Response response = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartan)
                .when()
                .post("/api/spartans");

        assertEquals(response.statusCode(),201);

        id = response.path("data.id");
    }

    @Test(priority = 2)
    public void PUTExistingSpartan(){

        Map<String,Object> putSpartans = new HashMap<>();
        putSpartans.put("name","blgnysn");
        putSpartans.put("gender","Male");
        putSpartans.put("phone",1231312321l);

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",id)
                .and()
                .body(putSpartans).
                when()
                .put("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);
    }

    @Test(priority = 3)
    public void PATCHExistingSpartan(){

        Map<String,Object> patchSpartans = new HashMap<>();
        patchSpartans.put("name","ysnblgn");


        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",id)
                .and()
                .body(patchSpartans)
                .when()
                .patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);
    }

    @Test(priority = 4)
    public void GETThatSpartan(){

        given().accept(ContentType.JSON)
                .pathParam("id",id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();
    }

    @Test(priority = 5)
    public void DELETEThatSpartan(){

        given().pathParam("id",id)
                .when()
                .delete("/api/spartans/{id}")
                .then()
                .statusCode(204).log().all();
    }

}
