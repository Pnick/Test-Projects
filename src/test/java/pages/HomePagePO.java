package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import driver.DriverFactory;
import utilsHelper.AttachFile;
import utilsHelper.CommonVariables;

public class HomePagePO extends LoadableComponent<HomePagePO>
{
	ExtentTest reporter = ExtentCucumberAdapter.getCurrentStep();
	CommonVariables var = new CommonVariables();
	BaseMethodsO method = new BaseMethodsO(var);

	public WebDriver driver;
	public WebDriverWait wait;

	@FindBy(xpath = "//*[contains(text(), 'CLAIM NOW')]")
	public List<WebElement> buttonClaimNow;

	@FindBy(xpath = "//*[contains(@src, 'logo')]")
	public WebElement linkLogo;

	public HomePagePO() {
		driver = DriverFactory.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		PageFactory.initElements(driver, this);
	}

	public void clickElement(String element) {

		WebElement elementToClick = null;

		switch(element) {
		case "logo":
			elementToClick = linkLogo;
			break;
		case "claim now":
			elementToClick = buttonClaimNow.get(0);
			break;
		default:
		}

		if(elementToClick!=null) {
			elementToClick.click();
		}
		else{ System.out.println("WARNING: Element '" + element + "' is not defined!"); }
	}

	public void isPageOpen() throws IOException {

		method.isloadComplete(driver);

		method.isModalDisplayed("close");

		int count = 0;
		for(int i = 0 ; i< buttonClaimNow.size(); i++) {
			if(buttonClaimNow.get(i).getAttribute("innerText").toLowerCase().equals("claim now") && buttonClaimNow.get(i).isDisplayed())
			{
				count++;
			}
		}

		if (count > 0) {
			reporter.info("INFO : Home page is opened.");
			Assert.assertTrue("[EXPECTED] Home page is opened.", true);
		} else {
			reporter.info("INFO : Home page is NOT opened.");
			Assert.fail("[ACTUAL] Home page is NOT opened.");
		}

		new AttachFile().Screenshot();
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub

	}
}
