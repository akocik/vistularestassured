package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class HomeWorkRestAssured extends RestAssuredTest {

    @Test
    public void shouldCorrectlyUpdateAllData (){
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "Poland");
        requestParams.put("salary", 5000);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("name", "Agnieszka");
        newPlayer.put("nationality", "Iceland");
        newPlayer.put("salary", 7000);

        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .put("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Agnieszka"))
                .body("nationality", equalTo("Iceland"))
                .body("salary", equalTo(7000));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldCorrectlyUpdateName () {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "Poland");
        requestParams.put("salary", 5000);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("name", "Kocik");
        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .patch("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Kocik"));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldCorrectlyUpdateSalary () {
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "Poland");
        requestParams.put("salary", 5000);
        Information player = given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);
        long userId = player.getId();
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("salary", 7000);
        given().header("Content-Type", "application/json")
                .body(newPlayer.toString())
                .patch("/information/"+userId)
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", equalTo(7000));
        given().delete("/information/"+userId)
                .then()
                .log().all()
                .statusCode(204);
    }

}
