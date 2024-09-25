package testDefinitions;

import driver.SharedDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePagePO;

/**
 * The Class Test_HomePage.
 */
public class Test_HomePage {

	/** The home page. */
	public HomePagePO homePage;

	/**
	 * Instantiates a new test home page.
	 *
	 * @param driver   the driver
	 * @param homePage the home page
	 */
	public Test_HomePage(SharedDriver driver, HomePagePO homePage) {
		this.homePage = homePage;
	}

	/**
	 * Click logo.
	 *
	 * @throws Throwable the throwable
	 */
	@When("click {string} button")
	public void clickElement(String element) throws Throwable {
		homePage.clickElement(element);
	}
	
	/**
	 * Verify page.
	 *
	 * @throws Throwable the throwable
	 */
	@Then("home page is displayed")
	public void verifyPage() throws Throwable {
		this.homePage.isPageOpen();
	}
}

