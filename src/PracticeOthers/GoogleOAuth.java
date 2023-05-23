package PracticeOthers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojoclass.API;
import Pojoclass.Courses;
import Pojoclass.course;
import Pojoclass.webAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
public class GoogleOAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
		String Code="4%2F0AVHEtk56g2LjXP2XwDoL7q55_W0vxaS9UKUFhxaHIBjGeRL_7j0z6cqarW-YPaOs2Yg_qg";
		
		//Get token
		String response=given().urlEncodingEnabled(false)
		.queryParam("code", Code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code").log().all()
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String AccessTocken=js.get("access_token");
		System.out.println(AccessTocken);
		
		
	/*	//Get Actual Request for json path finder
		String ActualResponse=given().log().all()
		.queryParam("access_token", AccessTocken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(ActualResponse);
		JsonPath js1=new JsonPath(ActualResponse);
		String InstructorlinkedInID=js1.get("linkedIn");
		System.out.println(InstructorlinkedInID);
		String Instructorname=js1.get("instructor");
		System.out.println(Instructorname); */
		
		
		//Get Actual Request using pojo class
		Courses ActualResponse=given()
				.queryParam("access_token", AccessTocken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(Courses.class);
		System.out.println(ActualResponse.getInstructor());
		System.out.println(ActualResponse.getLinkedIn());
		System.out.println(ActualResponse.getCourses());
		course allCourses=ActualResponse.getCourses();
		System.out.println(allCourses);
		List<API> apiCourses=ActualResponse.getCourses().getApi();
		for(int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
					{
				System.out.println(apiCourses.get(i).getPrice());
					}
		}
		List<webAutomation> webautomationCourses=ActualResponse.getCourses().getWebAutomation();
		ArrayList<String> a= new ArrayList<String>();
		for(int j=0;j<webautomationCourses.size();j++)
		{
			
			a.add(webautomationCourses.get(j).getCourseTitle());
				
		}
List<String> expectedList=	Arrays.asList(courseTitles);
		
		Assert.assertTrue(a.equals(expectedList));
	}
}
