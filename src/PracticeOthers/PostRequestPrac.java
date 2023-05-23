package PracticeOthers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
public class PostRequestPrac {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI="http://localhost:3000";
		//Post Request
		String Response=given().log().all().header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("E:\\RestAssured\\JsonPath.txt"))))
		.when().post("comments")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		System.out.println(Response);
		JsonPath js=new JsonPath(Response);
		String CommentResponse=js.getString("comment");
		System.out.println(CommentResponse);
		
	/*	//Upadate the Comment
		String UpdateComment="Eclipse Rest Assured Updated the comment6";
		given().log().all().header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"id\": 6,\r\n"
				+ "    \"name\": \"Postman Edit6\",\r\n"
				+ "    \"Email\": \"PostmanEdit6@gmail.com\",\r\n"
				+ "    \"comment\": \""+UpdateComment+"\"\r\n"
				+ "  }").
		when().put("/comments/6")
		.then().log().all().statusCode(200).body("comment", equalTo(UpdateComment));
		
		//Get the comment
		String Givenresponse=given().log().all()
		.when().get("/comments/6")
		.then().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=new JsonPath(Givenresponse);
		String ActualComment=js1.getString("comment");
		System.out.println(ActualComment);
		Assert.assertEquals(ActualComment, UpdateComment); */

	/*	//Delete added comment
		String deleteresponse=given().log().all().
		when().delete("comments/6")
		.then().log().all().statusCode(200).extract().response().asString();
		System.out.println(deleteresponse); */
	}

}
