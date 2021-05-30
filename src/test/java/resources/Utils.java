package resources;

import io.cucumber.gherkin.internal.com.eclipsesource.json.Json;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification reqSpec;

    public RequestSpecification requestSpecification() throws IOException {
        if(reqSpec==null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            reqSpec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON)
                    .build();
        }
        return reqSpec;
    }

    public String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("/Users/vibhavsharma/Documents/CucumberRestAssuredframework/src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }


    public String getJsonPath(Response response , String key){
        String resp = response.getBody().asString();
        JsonPath jp = new JsonPath(resp);
        return jp.get(key).toString();
    }
}
