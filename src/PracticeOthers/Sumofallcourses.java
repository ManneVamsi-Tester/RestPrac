package PracticeOthers;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class Sumofallcourses {
	@Test
	public void sumofcourses(){
	JsonPath js=new JsonPath(Payload.CousePrice());
	int Count=js.getInt("courses.size()");
	int sum=0;
	for(int i=0;i<Count;i++)
	{
		
		int price=js.getInt("courses["+i+"].price");
		int copies=js.getInt("courses["+i+"].copies");
		int amount=price*copies;
		System.out.println(amount);
		sum=sum+amount;
	}
	System.out.println(sum);
	int purchaseamount=js.getInt("dashboard.purchaseAmount");
	Assert.assertEquals(sum, purchaseamount);
	//Hello git
}
}
