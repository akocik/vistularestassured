package org.vistula.restassured.information;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;
import org.vistula.restassured.pet.Pet;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.put;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;



public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void shouldCorrectlyCreateNewObject (){
        JSONObject requestParams = new JSONObject();
        String myName = RandomStringUtils.randomAlphabetic(6);
        requestParams.put("name", myName);
        requestParams.put("nationality", "Poland");
        requestParams.put("salary", 5000);
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information" )
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo(myName))
                .body("nationality", equalTo("Poland"))
                .body("salary", equalTo(5000))
                .body("id", greaterThan(0));
    }

    @Test
    public void shouldCorrectlyDelete(){
        given().delete("/information/5")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldCorrectlyUpdate (){
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
}
