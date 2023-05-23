package PracticeOthers;

import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class Complexjsonparse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js=new JsonPath(Payload.CousePrice());
		// Print No of courses returned by API
		int NoofCourses=js.getInt("courses.size()");
		System.out.println("The No of Coursess are :"+NoofCourses);
		
		//Print Purchase Amount
		int puchaseamount=js.getInt("dashboard.purchaseAmount");
		System.out.println("The puchaseamount are :"+puchaseamount);
		
		//Print Title of the first course
		String firstcourse=js.getString("courses[0].title");
		System.out.println("The firstcourse title are :"+firstcourse);
		
		//Print All course titles and their respective Prices
		
		for(int i=0;i<NoofCourses;i++)
		{
			String CourseTitle=js.get("courses["+i+"].title");
			System.out.println("The Course Title is: "+CourseTitle);
			System.out.println(js.get("courses["+i+"].price").toString());
			
		}
		
		//Print no of copies sold by RPA Course
		System.out.println("Print no of copies sold by RPA course :");
		for(int i=0;i<NoofCourses;i++)
		{
			String coursetitle=js.get("courses["+i+"].title");
			if(coursetitle.equalsIgnoreCase("RPA"))
			{
				int copies=js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		
	
	}

}
