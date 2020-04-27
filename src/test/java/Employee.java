import demo.EmployeeRequest;
import demo.EmployeeResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class Employee {

    //HTTP REQUEST GET
    @Test
    public void getEmployee(){
        //instance object "response" for HTTP REQUEST "GET"
        Response response = RestAssured
                .given()
                .baseUri("http://dummy.restapiexample.com")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type","application/json")
                .header("Accept","*/*")
                .get("/v1/employees");

        //get body of response and print them
        response.getBody().prettyPrint();
        System.out.println(response.getStatusCode());
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertThat("Too Late Response",response.getTime(), Matchers.lessThan(3000L));

        //Validate if status success
        Assert.assertEquals("success",response.path("status"));

        //Validate if "Tiger Nixon is employee_name in data[0]"
        Assert.assertEquals("Tiger Nixon",response.path("data[0].employee_name"));

        //Deserializer
        // All of EmployeeResponse filled in variable employeeResponse
        EmployeeResponse employeeResponse = response.as(EmployeeResponse.class);
        // Print status from employeeResponse
        System.out.println(employeeResponse.getStatus());

        //Print employeename for data-0 on employeeResponse
        // System.out.println(employeeResponse.get(0).getEmployeeName());
    }

    //HTTP REQUEST POST
    @Test
    public void createEmployee(){
//        String requestBody ="{\n" +
//                "  \"name\": \"test\",\n" +
//                "  \"salary\": \"123\",\n" +
//                "  \"age\": \"23\"\n" +
//                "}";
        //Serialize
        //initialize employeeRequest for EmployeeRequest
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Oriani");
        employeeRequest.setAge("10");
        employeeRequest.setSalary("1000");

        //instance object "response" for HTTP REQUEST "POST"
        Response response = RestAssured
                .given()
                .baseUri("http://dummy.restapiexample.com")
                .basePath("/api")
                .log()
                .all()
                .header("Content-type","application/json")
                .header("Accept","*/*")
                .body(employeeRequest) //requestBody
                .post("/v1/create");

        //get body of response and print them
        response.getBody().prettyPrint();
    }
}
