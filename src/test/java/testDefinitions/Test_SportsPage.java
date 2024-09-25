package testDefinitions;

import driver.SharedDriver;
import io.cucumber.java.en.Then;
import pages.SportsPagePO;

/**
 * The Class Test_HomePage.
 */
public class Test_SportsPage {

	/** The home page. */
	public SportsPagePO sportsPage;

	/**
	 * Instantiates a new test home page.
	 *
	 * @param driver   the driver
	 * @param homePage the home page
	 */
	public Test_SportsPage(SharedDriver driver, SportsPagePO sportsPage) {
		this.sportsPage = sportsPage;
	}
	/**
	 * Verify page.
	 *
	 * @throws Throwable the throwable
	 */
	@Then("sports page is displayed")
	public void verifyPage() throws Throwable {
		this.sportsPage.isPageOpen();
	}
	/**
	 * Verify page.
	 *
	 * @throws Throwable the throwable
	 */
	@Then("player balance on web is correct")
	public void verifyDisplayedBalance() throws Throwable {
		this.sportsPage.verifyDisplayedBalance();
	}
	
}

