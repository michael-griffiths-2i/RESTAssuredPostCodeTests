package examples;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class WireMockGBTests {

    // This rule tells JUnit to start and stop the WireMock server at
    // the correct times (before and after each test case respectively).
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(9876));

    // This test case sends a GET request to the /gb/G64 endpoint and checks
    // that the 'placeName' field in the response body is 'Bishopbriggs'.
    @Test
    public void requestGBPostCodeG64_checkPlaceNameInResponseBody_expectBishopbriggs() {

        given().
                when().
                get("http://localhost:9876/gb/G64").
                then().
                assertThat().
                body("response.places.place.placeName", equalTo("Bishopbriggs"));
    }

    // This test case sends a GET request to the /gb/SW1 endpoint and checks
    // that the 'placeName' field of the third place in the response body is 'Green Park'.
    @Test
    public void requestGBPostCodeSW1_checkThirdPlaceNameInResponseBody_expectGreen_Park() {

        given().
                when().
                get("http://localhost:9876/gb/SW1").
                then().
                assertThat().
                body("response.places.place[2].placeName", equalTo("Green Park"));
    }

    // This test case sends a GET request to the /gb/G64 endpoint and checks
    // that the 'placeName' field of the first place in the response body is 'Bishopbriggs'.
    @Test
    public void requestGBZipCodeG64_checkThirdPlaceNameInResponseBody_expectKropp() {

        given().
                when().
                get("http://localhost:9876/gb/G64").
                then().
                assertThat().
                body("response.places.place[0].placeName", equalTo("Bishopbriggs"));
    }

    // This test case sends a GET request to the /gb/SW1 endpoint and checks
    // that the 'placeName' field of the last place in the response body is 'Victoria'.
    @Test
    public void requestGBPostCodeSW1_checkLastPlaceNameInResponseBody_expectVictoria() {

        given().
                when().
                get("http://localhost:9876/gb/SW1").
                then().
                assertThat().
                body("response.places.place[-1].placeName", equalTo("Victoria"));
    }

    // This test case sends a GET request to the /gb/SW1 endpoint and checks
    // that the 'latitude' field of the second place in the response body is '51.5'.
    @Test
    public void requestGBPostCodeSW1_checkLatitudeForSecondPlaceInResponseBody_expect5445() {

        given().
                when().
                get("http://localhost:9876/gb/SW1").
                then().
                assertThat().
                body("response.places.place[1].@latitude", equalTo("51.5"));
    }

    // This test case sends a GET request to the /gb/SW1 endpoint and checks
    // that the number of places with 'stateAbbreviation' field equal to 'ENG' is 5.
    @Test
    public void requestGBPostCodeSW1_checkNumberOfPlacesWithStateAbbreviationENG_expect5() {

        given().
                when().
                get("http://localhost:9876/gb/SW1").
                then().
                assertThat().
                body("response.places.place.findAll{it.stateAbbreviation=='ENG'}", hasSize(5));
    }

}
