package PracticeOthers;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Login of Jira API
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session=new SessionFilter();
		String response=given().log().all().header("content-type", "application/json").body("{ \"username\": \"mannevamsi98\", \"password\": \"Vamsi@456\" }").filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().statusCode(200).extract().response().asString();
		JsonPath js=new JsonPath(response);
		String name=js.getString("session.name");
		System.out.println(name);
		String actualJSESSIONID=js.getString("session.value");
		System.out.println(actualJSESSIONID);

		//Add comment API
		//header("Cookie", ""+name+"="+actualJSESSIONID+"")
		String expectedMessage ="Hi How are you?";
		String addCommentResponse=given().log().all().header("content-type", "application/json").filter(session).body("{\r\n"
				+ "    \"body\" : \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\":{\r\n"
				+ "    \"type\" : \"role\",\r\n"
				+ "    \"value\" : \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "}")
		.when().post("/rest/api/2/issue/RSA-1/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js1=new JsonPath(addCommentResponse);
		 
		String commentId= js1.getString("id");
		
		//Add Attachment
		given().log().all().header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").
		pathParam("key", "RSA-1").filter(session).multiPart("file", new File("C:\\Users\\DELL\\Downloads\\PngItem_3973657.png"))
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get Issue
		 
		String issueDetails=given().filter(session).pathParam("key", "RSA-1")
		 
		.queryParam("fields", "comment")
		 
		.log().all().when().get("/rest/api/2/issue/{key}").then()
		 
		.log().all().extract().response().asString();
		 
		System.out.println(issueDetails);
		 
		JsonPath js11 =new JsonPath(issueDetails);
		 
		int commentsCount=js11.getInt("fields.comment.comments.size()");
		 
		for(int i=0;i<commentsCount;i++)
		 
		{
		 
		String commentIdIssue =js11.get("fields.comment.comments["+i+"].id").toString();
		 
		if (commentIdIssue.equalsIgnoreCase(commentId))
		 
		{
		 
		String message= js11.get("fields.comment.comments["+i+"].body").toString();
		 
		System.out.println(message);
		 
		Assert.assertEquals(message, expectedMessage);
		 
		}
		 
		}
	}

}
