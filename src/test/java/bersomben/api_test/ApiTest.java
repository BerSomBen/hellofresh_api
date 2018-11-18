package bersomben.api_test;

import org.codehaus.groovy.transform.ConditionalInterruptibleASTTransformation;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.json.JSONObject;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

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
				.body("RestResponse.result.alpha2_code", is("US")).body("RestResponse.result.alpha3_code", is("USA"))
				.body("RestResponse.result.name", is("United States of America"));

	}

	@Test
	public void getSpecific_de() {
		get("http://services.groupkt.com/country/get/iso2code/DE").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", is("DE")).body("RestResponse.result.alpha3_code", is("DEU"))
				.body("RestResponse.result.name", is("Germany"));

	}

	@Test
	public void getSpecific_gb() {
		get("http://services.groupkt.com/country/get/iso2code/GB").then().statusCode(200)
				.body("RestResponse.result.alpha2_code", is("GB")).body("RestResponse.result.alpha3_code", is("GBR"))
				.body("RestResponse.result.name", is("United Kingdom of Great Britain and Northern Ireland"));

	}

	@Test
	public void getSpecific_ab() {
		get("http://services.groupkt.com/country/get/iso2code/AB").then().statusCode(200)
				.body("RestResponse.result", nullValue())
				.body("RestResponse.messages[0]", is("No matching country found for requested code [AB]."));
	}

	@Test
	public void postSpecific_TC() {
		JSONObject jsonObj = new JSONObject()
				.put("name", "Test Country")
				.put("alpha2_code", "TC")
				.put("alpha3_code","TCY");

		given()
			.contentType("application/json") // another way to specify content type
			.body(jsonObj.toString()) // use jsonObj toString method
		.when()
			.post("http://services.groupkt.com/country/post")
		.then().assertThat()
			.statusCode(200)
			.body("RestResponse.messages[0]", is("Country added or overwritten [TC]."));
	}

}
