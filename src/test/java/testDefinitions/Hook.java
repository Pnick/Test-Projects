package testDefinitions;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook {

	public static Scenario scenario;
	
	@Before
	public void before(Scenario scenario) {

		System.out.println("************************************************************************************************************");
		System.out.println("Starting scenario : " + scenario.getName());
		System.out.println("************************************************************************************************************");
		
		Hook.scenario=scenario;
	}

	@After
	public void aftercase(Scenario scenario) throws IOException {

		if (scenario != null && scenario.isFailed()) { 
			byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", "Error");
					}
				
		DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		System.out.println("============================================================================================================");
		System.out.println("End of scenario : " + scenario.getName() + " -> Status - " + scenario.getStatus());
		System.out.println("============================================================================================================");
	}
}
