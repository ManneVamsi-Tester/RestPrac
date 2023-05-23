package Ecomers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Pojoclass.OrderDetails;
import Pojoclass.Orders;

public class EcommerceAPITesting {

	public static void main(String[] args) {
		
		//Login in to ecommerce website
		RequestSpecification	req=	new RequestSpecBuilder().setContentType(ContentType.JSON)
				
				.setBaseUri("https://rahulshettyacademy.com")
				.build();
	
	loginserialization LoginRequest=new loginserialization();
	LoginRequest.setUserEmail("mannevamsi98@gmail.com");
	LoginRequest.setUserPassword("Pass12345");
	
	RequestSpecification response=given().log().all().spec(req).body(LoginRequest);
	
logindeserialization loginresponse=response.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(logindeserialization.class);
System.out.println(loginresponse.getToken());
String token=loginresponse.getToken();
System.out.println(loginresponse.getUserId());
String Id=loginresponse.getUserId();

//Create Product
RequestSpecification	addproductbaseURL=	new RequestSpecBuilder()
.addHeader("Authorization", token)

.setBaseUri("https://rahulshettyacademy.com")
.build();

RequestSpecification reqaddproduct=given().log().all().spec(addproductbaseURL)
.param("productName", "Dartmoor trees")
.param("productAddedBy", Id)
.param("productCategory", "fashion")
.param("productSubCategory", "Artworks")
.param("productPrice", "200")
.param("productDescription", "Paint")
.param("productFor", "Art")
.multiPart("productImage", new File("C:\\Users\\DELL\\Downloads\\Dev data\\Dartmoor trees.jpg"));


String addresponse=reqaddproduct.when().post("/api/ecom/product/add-product")
.then().log().all().extract().response().asString();

JsonPath js=new JsonPath(addresponse);
String productID=js.get("productId");
System.out.println(productID);


//Create Orders
RequestSpecification	createbaseurl=	new RequestSpecBuilder().setContentType(ContentType.JSON)
.addHeader("Authorization", token)
.setBaseUri("https://rahulshettyacademy.com")
.build();

OrderDetails orderdetail=new OrderDetails();
orderdetail.setCountry("Australia");
orderdetail.setProductOrderedId(productID);

List<OrderDetails> orderdetaillist=new ArrayList<OrderDetails>();
orderdetaillist.add(orderdetail);

Orders order=new Orders();
order.setOrders(orderdetaillist);

RequestSpecification createorderreq =given().log().all().spec(createbaseurl).body(order);
String responseorder=createorderreq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();

System.out.println(responseorder);

//Delete Product

RequestSpecification	deletebaseurl=	new RequestSpecBuilder().setContentType(ContentType.JSON)
.addHeader("Authorization", token)
.setBaseUri("https://rahulshettyacademy.com")
.build();

RequestSpecification deleteorderreq =given().relaxedHTTPSValidation().log().all().spec(deletebaseurl).pathParam("productID", productID);

String deleteroder=deleteorderreq.when().delete("/api/ecom/product/delete-product/{productID}").then().log().all().extract().response().asString();

JsonPath js1=new JsonPath(deleteroder);
String ActualDeleteresponse=js1.get("message");
Assert.assertEquals(ActualDeleteresponse, "Product Deleted Successfully");

	}

}
