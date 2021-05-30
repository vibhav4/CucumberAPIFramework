package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.Assert.assertEquals;

public class StepDefination extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    static String placeId;
    JsonPath jp;
    TestDataBuild testData = new TestDataBuild();


    @Given("Add place payload with folloing details :")
    public void add_place_payload_with_folloing_details(io.cucumber.datatable.DataTable dataTable) throws IOException {
        System.out.println("************I am here************");
        List<Map<String,String>> data = dataTable.asMaps();
        Map<String,String> dataMap = data.get(0);
        res = given()
                .spec(requestSpecification())
                .body(testData.addPlacePayload(dataMap.get("name"),dataMap.get("language"),dataMap.get("address")));

    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource , String method) {

        String testuri = APIResources.valueOf(resource).getResource();

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        switch (method.toUpperCase()){
            case "POST":
                response =res.when().post(testuri);
                break;
            case "GET":
                response =res.when().get(testuri);
                break;
            case "DELETE":
                response =res.when().delete(testuri);
                break;
        }

    }
    @Then("APi call is success with code {int}")
    public void a_pi_call_is_success_with_code(Integer int1) {
        assertEquals(Optional.of(response.getStatusCode()), Optional.of(int1));
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        assertEquals(getJsonPath(response, key),value);
    }

    @And("verify place id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedname, String resource) throws IOException {
        placeId = getJsonPath(response, "place_id");
        res = given()
                .spec(requestSpecification()).queryParam("place_id",placeId);
        user_calls_with_post_http_request(resource, "GET");
        String actualname = getJsonPath(response , "name");
        assertEquals(actualname , expectedname);

    }

    @Given("delete place payload with following details :")
    public void delete_place_payload_with_following_details(io.cucumber.datatable.DataTable dataTable) throws IOException {

        res = given().spec(requestSpecification()).body(testData.deletePlacePayload(placeId));
    }
}
