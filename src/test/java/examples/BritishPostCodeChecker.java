package examples;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
public class BritishPostCodeChecker {

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
            { "gb", "SW4", "Lambeth" },
            { "gb", "L1", "Liverpool" },
            { "gb", "G33", "Stepps"},
            {"gb", "G64", "Bishopbriggs"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestUsZipCode90210_checkStatusCode_expectHttp200(String countryCode, String zipCode, String placeName) {

        given().
                when().
                    pathParam("countryCode", countryCode).pathParam("zipCode",zipCode).
                get("http://zippopotam.us/{countryCode}/{zipCode").
                then().
                assertThat().
                                body("places[0].'place name'",equalTo(placeName));//just means that the page has loaded
    }

}
