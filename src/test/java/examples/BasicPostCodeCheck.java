package examples;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicPostCodeCheck {

    @Test
    public void requestGBpostCode_G64_expect_Bishopriggs() {
        //This test should return Bishopbriggs as the first place in the array of places
        //The information passed into the api was gb and G64
        //using log().all() outputs the entire contents of the JSON to the console
        given().
        when().
            get("http://zippopotam.us/gb/G64").
        then().
            assertThat().log().all().
            body("places[0].'place name'", equalTo("Bishopbriggs"));

    }
    @Test
    public void requestGBpostCode_G64_expect_Balmore() {
        //This test should return Balmore as the second place in the array of places
        //The information passed into the api was gb and G64
        //using log().all() outputs the entire contents of the JSON to the console
        given().
                when().
                get("http://zippopotam.us/gb/G64").
                then().
                assertThat().log().all().
                body("places[1].'place name'", equalTo("Balmore"));

    }
}
