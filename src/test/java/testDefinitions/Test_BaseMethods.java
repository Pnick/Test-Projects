package testDefinitions;

import driver.SharedDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.BaseMethodsO;

public class Test_BaseMethods {

	protected BaseMethodsO method;
	
	public Test_BaseMethods(SharedDriver driver, BaseMethodsO method) {
		this.method = method;
	}
	
	@Given("the {string} page is opened with user {string}")
	public void openPage(String page, String user) throws Throwable {
		method.openPage(page, user);
	}
	
	@Given("page via {string} is opened with user {string}")
	public void openPageViaLink(String url, String user) throws Throwable {
		openPageViaLink(url, user);
	}
	
	@Then("a {string} page is displayed")
	public void checkPage(String page) throws Throwable {

		method.checkPageOpened(page);
	}
}
