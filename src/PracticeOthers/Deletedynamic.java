package PracticeOthers;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Deletedynamic {
	@Test(dataProvider = "DeleteBooks")
	//Delete Book
	public void DeleteBook(String iD) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String DeleteResponse=given().log().all().header("Content-Length", "application/json")
		.body(Payload.DeleteBookMultile(iD))
		.when().post("Library/DeleteBook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=new JsonPath(DeleteResponse);
		String ActualdeleteResponse=js1.get("msg");
		String ExpectedResponse="book is successfully deleted";
		Assert.assertEquals(ActualdeleteResponse, ExpectedResponse);
	}
	@DataProvider
	public Object[][] DeleteBooks() {
		return new Object[][] {{"FB111"},{"SB222"},{"TB333"}};
	}

}
