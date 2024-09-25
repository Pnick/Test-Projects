package Runners.JUnit;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utilsHelper.Initial;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "Features" },
				 glue	  = { "testDefinitions" 	 },
				 tags 	  = ("(not @manual or not @notSupported or not @notImplemented or not @CannotBeTested) and (@SportsPage)"),
				 plugin   = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

public class RunnerDelasportTest {

	@BeforeClass
	public static void initJUnit() {
		Initial set = new Initial();
		set.environment();
		set.initURL();
	}

	@AfterClass
	public static void writeExtentReportJUnit() throws IOException, InterruptedException {

	}
}
