package PracticeOthers;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.nio.file.Files;

public class DynamicJson {
	
	//Add Book
	@Test(dataProvider = "AddBooks")
	public void Addbook(String isbn, String aisle){
	RestAssured.baseURI="https://rahulshettyacademy.com/";
	String response=given().log().all().header("Content-Type", "application/json")
	.body(Payload.Addbook(isbn,aisle))
	.when().post("/Library/Addbook.php")
	.then().log().all().statusCode(200).extract().response().asString();
	JsonPath js=new JsonPath(response);
	String ActualMSG=js.get("Msg");
	String ExpetedMSG="successfully added";
	Assert.assertEquals(ActualMSG, ExpetedMSG);
	String ActualID=js.get("ID");
	System.out.println(ActualID);
	
	
	}
	@Test(dataProvider="AddBooks")
	  public void f2(String isbn, String aisle) {
		
		  RestAssured.baseURI = "https://rahulshettyacademy.com/";
		  
		  String res=  given().log().all().header("Content-Type", "application/json")
				  .body(Payload.getDeleteBody(isbn, aisle))
				  .when().post("/Library/DeleteBook.php")
				  .then().log().all().statusCode(200).extract().response().asString();

	}
	
	@DataProvider
	public Object[][] AddBooks() {
		return new Object[][] {{"FB","111"},{"SB","222"},{"TB","333"}};
	}

}
