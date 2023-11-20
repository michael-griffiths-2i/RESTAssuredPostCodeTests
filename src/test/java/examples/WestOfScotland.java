package examples;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(DataProviderRunner.class)
public class WestOfScotland {

    // Data provider for zip codes and corresponding places
    @DataProvider
    public static Object[][] postCodesAndPlaces() {
        return new Object[][]{
                {"GB", "G33", "Stepps"},
                {"GB", "ML1", "Bellside"},
                {"GB", "G66", "Waterside"},
                {"GB", "G64", "Bishopbriggs"}
        };
    }

    // Test to log the response body for each postal code
    @Test
    @UseDataProvider("postCodesAndPlaces")
    public void logResponseBodyForPostalCode(String country, String postalCode, String place) {
        given().when().
                pathParam("postalCode", postalCode).
                get("https://www.zippopotam.us/GB/{postalCode}").then().log().body();
    }

    // Request specification to be used throughout the tests
    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://api.zippopotam.us").build();
    }

    // Response specification to be used throughout the tests
    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {
        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

    // Test to check if the status code for each postal code is 200
    @Test
    @UseDataProvider("postCodesAndPlaces")
    public void checkStatusCodeForPostalCode(String country, String postalCode, String place) {
        given().
                spec(requestSpec).
                when().
                pathParam("country", country).
                pathParam("postalCode", postalCode).
                get("{country}/{postalCode}").then().assertThat().statusCode(200);
    }

    // Test to check if the place name in the response body matches the expected place name
    @Test
    @UseDataProvider("postCodesAndPlaces")
    public void checkPlaceNameInResponseBody(String country, String postalCode, String place) {
        given().
                spec(requestSpec).
                when().
                pathParam("country", country).
                pathParam("postalCode", postalCode).
                get("http://zippopotam.us/{country}/{postalCode}").
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("places[0].'place name'", equalTo(place));
    }
}