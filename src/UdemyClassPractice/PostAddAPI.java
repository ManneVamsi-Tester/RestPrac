package UdemyClassPractice;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import Files.Payload;
public class PostAddAPI {

	public static void main(String[] args) {
		//Add place
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		System.out.println(Response);
		
		JsonPath js=new JsonPath(Response);
		String PlaceID=js.getString("place_id");
		System.out.println(PlaceID);
		
		//Update the place
		String newaddress="South Street, Test";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+PlaceID+"\",\r\n"
				+ "\"address\":\""+newaddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get the place
		String GetplaceResponse=given().log().all().queryParam("key", "qaclick123").
		queryParam("place_id", PlaceID)
		.when().get("maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(GetplaceResponse);
		String ActualAddress=js1.getString("address");
		System.out.println(ActualAddress);
		Assert.assertEquals(ActualAddress, newaddress);

	
		
	}

}
