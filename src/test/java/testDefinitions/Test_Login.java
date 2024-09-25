package testDefinitions;

import driver.SharedDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.LoginPO;
import utilsHelper.CommonVariables;


public class Test_Login {

	public LoginPO login;

	public Test_Login(SharedDriver driver, LoginPO login) {
		this.login = login;
	}
		
	@Given("the Login screen is opened")
	public void the_Login_screen_is_opened() throws Throwable {
	    
		login.openLoginPage(CommonVariables.baseURL);
	}

	@When("enter {string} and {string}")
	public void enterCredentials(String username, String password) throws Throwable {
	    
		login.enterCredentials(username, password);
	}
	
	@When("click on {string} button")
	public void clickButton(String button) throws Throwable {
	    
		login.clickLoginButton(button);
	}
}
