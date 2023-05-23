package Pojoclass;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import Files.Payload;
public class Serailization {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setName("Manne Vamsi");
		p.setPhone_number("(+91) 8374182756");
		p.setAddress("Mulumudi, south street");
		List<String> mylist=new ArrayList<>();
		mylist.add("shoe park");
		mylist.add("shop");
		
		p.setTypes(mylist);
		p.setWebsite("http://vamsi.com");
		p.setLanguage("Telugu-TL");
		
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String Response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(p)
				.when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
				System.out.println(Response);
				
				JsonPath js=new JsonPath(Response);
				String PlaceID=js.getString("place_id");
				System.out.println(PlaceID);
	}

}
