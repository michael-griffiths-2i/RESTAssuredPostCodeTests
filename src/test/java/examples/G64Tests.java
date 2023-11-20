package examples;

import com.tngtech.java.junit.dataprovider.DataProvider;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class G64Tests {

    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                { "us", "90210", "Beverly Hills" },
                { "us", "12345", "Schenectady" },
                { "ca", "B2R", "Waverley"},
                {"gb", "G64", "Bishopbriggs"}
        };
    }

    @Test
    public void requestUsZipCodeG64_checkStatusCode_expectHttp200() {

        given().
                when().
                get("http://zippopotam.us/GB/G64").
                then().
                assertThat().
                statusCode(200);//just means that the page has loaded
    }
    @Test
    public void expectJSON(){
        given().when().get("https://www.zippopotam.us/GB/G64").then().assertThat().contentType("application/JSON");
    }


    @Test
    public void logResponseAndDetails(){
        given().when().get("https://www.zippopotam.us/GB/G64").then().log().body();
    }

    @Test
    public void checkPlaceName(){
        given().when().get("https://www.zippopotam.us/GB/G64").then().assertThat().
                body("places[0].'place name'", equalTo("Bishopbriggs"));


    }
@Test
    public void checkPlaceNumbers(){
        given().when().get("https://www.zippopotam.us/GB/G64").then().assertThat().
                body("places.'place name'", hasSize(4));//verify that there are 4 places with this postcode
    }


}
