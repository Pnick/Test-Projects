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
import utilsHelper.GetJsonValues;

public class SportsPagePO  extends LoadableComponent<SportsPagePO>{
	ExtentTest reporter = ExtentCucumberAdapter.getCurrentStep();
	CommonVariables var = new CommonVariables();
	BaseMethodsO method = new BaseMethodsO(var);
	public String parentWindow;

	public WebDriver driver;
	public WebDriverWait wait;

	@FindBy(xpath = "//*[contains(@id, 'betslip-tab')]")
	public List<WebElement> buttonBetslip;

	@FindBy(xpath = "//*[contains(@class, 'user-balance-item-amount')]")
	public WebElement balanceAmount;

	public SportsPagePO() {
		driver = DriverFactory.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
		PageFactory.initElements(driver, this);
	}

	public void isPageOpen() throws IOException {

		method.isloadComplete(driver);

		method.isModalDisplayed("close");

		int count = 0;
		for(int i = 0 ; i< buttonBetslip.size(); i++) {
			if(buttonBetslip.get(i).isDisplayed())
			{
				count++;
			}
		}

		if (count > 0) {
			reporter.info("INFO : Sports page is opened.");
			Assert.assertTrue("[EXPECTED] Sports page is opened.", true);
		} else {
			reporter.info("INFO : Sports page is NOT opened.");
			Assert.fail("[ACTUAL] Sports page is NOT opened.");
		}

		new AttachFile().Screenshot();
	}

	public void verifyDisplayedBalance() {

		String balanceHeader = balanceAmount.getText().replaceAll(".*?(-?[\\d.]+).*", "$1");;
		 
		System.out.println("'getMemberBalance' data : " + CommonVariables.jsonStat);
		String balanceServ = new GetJsonValues().findKeyValue(CommonVariables.jsonStat, "raw_amount");

		if(Double.parseDouble(balanceServ)==(Double.parseDouble(balanceHeader))) {
			System.out.println("Server balance : " + balanceServ + " = balance on Web(Header) : " + balanceHeader);
			reporter.info("INFO : Server balance : " + balanceServ + " = balance on Web(Header) : " + balanceHeader);
			Assert.assertTrue("[EXPECTED] Balance on Web is correct.", true);
		}
		else {
			System.out.println("Server balance : " + balanceServ + " is NOT equal to balance on Web(Header) : " + balanceHeader);
			reporter.info("INFO : Server balance : " + balanceServ + " is NOT equal to balance on Web(Header) : " + balanceHeader);
			Assert.fail("[ACTUAL] Web balance is Not correct : " + balanceHeader);
		}
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
