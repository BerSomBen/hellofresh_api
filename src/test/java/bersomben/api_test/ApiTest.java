package bersomben.api_test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

public class ApiTest {

	@Test
	public void countryGetAll_HasISO2_USDEGB() {
		get("http://services.groupkt.com/country/get/all").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", hasItems("US", "GB", "DE"))
				.body("RestResponse.result.alpha3_code", hasItems("USA", "GBR", "DEU"));

	}

	@Test
	public void getSpecific_us() {
		get("http://services.groupkt.com/country/get/iso2code/US").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", Matchers.equalTo("US"))
				.body("RestResponse.result.alpha3_code", Matchers.equalTo("USA"))
				.body("RestResponse.result.name", Matchers.equalTo("United States of America"));

	}

	@Test
	public void getSpecific_de() {
		get("http://services.groupkt.com/country/get/iso2code/DE").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", Matchers.equalTo("DE"))
				.body("RestResponse.result.alpha3_code", Matchers.equalTo("DEU"))
				.body("RestResponse.result.name", Matchers.equalTo("Germany"));

	}

	@Test
	public void getSpecific_gb() {
		get("http://services.groupkt.com/country/get/iso2code/GB").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", Matchers.equalTo("GB"))
				.body("RestResponse.result.alpha3_code", Matchers.equalTo("GBR")).body("RestResponse.result.name",
						Matchers.equalTo("United Kingdom of Great Britain and Northern Ireland"));

	}

	@Test
	public void getSpecific_ab() {
		get("http://services.groupkt.com/country/get/iso2code/AB").then().statusCode(200)
				.body("RestResponse.result", nullValue()).body("RestResponse.messages[0]",
						Matchers.equalTo("No matching country found for requested code [AB]."));
	}

	@Test
	public void postSpecific_TC() {
		JSONObject jsonObj = new JSONObject().put("name", "Test Country").put("alpha2_code", "TC").put("alpha3_code",
				"TCY");

		given().contentType("application/json") // another way to specify content type
				.body(jsonObj.toString()) // use jsonObj toString method
				.when().post("http://services.groupkt.com/country/post").then().assertThat().statusCode(200)
				.body("RestResponse.messages[0]", Matchers.equalTo("Country added or overwritten [TC]."));
	}

}
