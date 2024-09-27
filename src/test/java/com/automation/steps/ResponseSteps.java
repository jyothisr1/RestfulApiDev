package com.automation.steps;

import com.automation.pojo.AddObjectPojo;
import com.automation.utils.ConfigReader;
import com.automation.utils.RestAssuredUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.Assert;

public class ResponseSteps {
    @Then("verify status code is {int}")
    public void verify_status_code_is(int statusCode) {
        Assert.assertEquals(statusCode, RestAssuredUtils.post().getStatusCode());
    }

    @And("verify list is not null")
    public void verifyListIsNotNull() {
        Assert.assertFalse(RestAssuredUtils.getResponse().toString().isEmpty());

//        System.out.println(RestAssuredUtils.get().getBody().toString().charAt(1));
    }

    @And("user stores created booking id into {string};")
    public void userStoresCreatedBookingIdInto(String key) {
        ConfigReader.setConfig(key,RestAssuredUtils.getResponse().jsonPath().getString("id"));

    }

    @And("verify booking id is not empty")
    public void verifyBookingIdIsNotEmpty() {
        String bookingId=RestAssuredUtils.getResponse().jsonPath().getString("id");
        Assert.assertTrue(!bookingId.isEmpty());
    }


    @And("store token value to {string}")
    public void storeTokenValueTo(String key) {
        String token=RestAssuredUtils.getResponse().jsonPath().getString("token");
        ConfigReader.setConfig(key,token);
    }

    @And("verify response is same as request passed in post call")
    public void verifyResponseIsSameAsRequestPassedInPostCall() throws JsonProcessingException {
        String jsonFolderPath= ConfigReader.getConfig("json.folder.path");
        String jsonBody=RestAssuredUtils.getData(jsonFolderPath+"add_object.json");
        ObjectMapper om=new ObjectMapper();
        AddObjectPojo actual=om.readValue(jsonBody, AddObjectPojo.class);
        RestAssuredUtils.setBodyUsingPojo(actual);
        AddObjectPojo expected=new AddObjectPojo();
        expected=RestAssuredUtils.getResponse().as(AddObjectPojo.class);
        Assert.assertTrue(actual.equals(expected));

    }

    @And("verify response is matching with the {string}")
    public void verifyResponseIsMatchingWithThe(String fileName) {
        RestAssuredUtils.getResponse().then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("json/add_object_schema.json"));
    }
}
