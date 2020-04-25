package serviceobjects;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class ForcedAnswer {

    private Response response;

    public ForcedAnswer() {

    }


    public void aForcedAnswerTypeIsRequested(String answerType) {
        RestAssured.baseURI = "https://yesno.wtf/api";
        RequestSpecification httpRequest = RestAssured.given();
        this.response = httpRequest.request(Method.GET, "/?force="+answerType);
        JsonPath answer = response.getBody().jsonPath();
        System.out.println(answer.prettyPrint());
        Assert.assertEquals("Correct answer returned", answerType , answer.getString("answer"));
    }

    public long getResponseTime() {
        return response.getTime();
    }


}
