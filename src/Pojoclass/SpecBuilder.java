package Pojoclass;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import Files.Payload;
public class SpecBuilder {

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
		
		
	RequestSpecification	req=new RequestSpecBuilder().setContentType(ContentType.JSON)
				
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key","qaclick123")
				.build();
	
ResponseSpecification resspec  = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON). build();
		
		//RestAssured.baseURI="https://rahulshettyacademy.com";
	RequestSpecification res  =given().spec(req)
				.body(p);
		 
	Response response = res.when().post("/maps/api/place/add/json")
				.then().spec(resspec).extract().response();
				System.out.println(response);
				
				String responseString=response.asString();
				System.out.println(responseString);
				JsonPath js=new JsonPath(responseString);
				String PlaceID=js.getString("place_id");
				System.out.println(PlaceID);
	}

}
