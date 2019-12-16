package org.vistula.restassured.pet;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class PetPostGetScenarioTest extends RestAssuredTest {

    @Test
    public void shouldCorrectlyGetPet(){
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE);
        String petName =  RandomStringUtils.randomAlphabetic(6);
        requestParams.put("id", value);
        requestParams.put("name", petName);
        createNewPet(requestParams);
        getNewPet(value);

        deleteNewPet(value);

    }

    private void deleteNewPet(int value) {
        given().delete("/pet/"+value)
                .then()
                .log().all()
                .statusCode(204);
    }

    private void getNewPet(int value) {
        given().get("/pet/"+value)
                .then()
                .log().all()
                .statusCode(200);
    }

    private void createNewPet(JSONObject requestParams) {
        given().header("Content-Type", "application/json")
           .body(requestParams.toString())
           .post("/pet")
           .then()
           .log().all()
           .statusCode(201);
    }
}
