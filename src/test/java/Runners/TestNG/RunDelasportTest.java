package Runners.TestNG;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import utilsHelper.Initial;

@RunWith(Cucumber.class)
@CucumberOptions(
		features 	= 	{ "Features" },
		glue		= 	{ "testDefinitions" },
		tags 		= 	("(@SportsPage) and not @manual and not @notSupported and not @notImplemented and not @CannotBeTested"),
		plugin 	 	=	{ "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

public class RunDelasportTest extends AbstractTestNGCucumberParallelTests{

	@BeforeSuite
	public static void initTestNG() {
		Initial set = new Initial();
		set.environment();
		set.initURL();
	}

	@AfterMethod
	public static void writeExtentReport() throws IOException, InterruptedException {

	}
}
