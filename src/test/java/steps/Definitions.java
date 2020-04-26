package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import com.github.cucumberlocust4j.pretzel.performance.LocustOperations;

import serviceobjects.ForcedAnswer;

public class Definitions {

	LocustOperations locustOperations = new LocustOperations();
	ForcedAnswer forcedAnswer = new ForcedAnswer();

	@Given("^Multiple users are requesting for a forced answer$")
	public void multiple_users_are_requesting_for_a_forced_answer(DataTable arg1) throws Throwable {
		locustOperations.executePerformanceTask(arg1);
	}

	@Then("^The answer is returned within the expected time$")
	public void the_answer_is_returned_within_the_expected_time(DataTable testData) throws Throwable {
		Assert.assertFalse(locustOperations.checkMaxResponseTime(testData));
	}


	@When("^a forced (.+) is requested$")
	public void aForcedAnswerTypeIsRequested(String answerType) {
		forcedAnswer.aForcedAnswerTypeIsRequested(answerType);
	}

	@Then("^the corresponding (.+) is returned$")
	public void theCorrespondingAnswerTypeIsReturned(String answerType) {
	}
}
