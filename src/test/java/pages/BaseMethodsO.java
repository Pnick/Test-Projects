package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import driver.DriverFactory;
import utilsHelper.AttachFile;
import utilsHelper.CommonVariables;
import utilsHelper.DevToolsHelper;

public class BaseMethodsO extends LoadableComponent<BaseMethodsO> {

	ExtentTest reporter = ExtentCucumberAdapter.getCurrentStep();
	public CommonVariables var;
	public WebDriver driver;
	public WebDriverWait wait;
	WebElement buttonToClick;

	public BaseMethodsO(CommonVariables var) {
		driver = DriverFactory.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4)); // set implicit wait
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(4)); // set explicit wait
		PageFactory.initElements(driver, this);
		if (var != null) {
			this.var = var;
		} else {
			this.var = new CommonVariables();
		}
	}

	public void openPage(String page, String user) throws IOException {

		String clientURL = CommonVariables.baseURL;

		if(!page.equals("home")) {
			clientURL = CommonVariables.baseURL +"/"+ page;
		}

		openPage(driver, clientURL, user);
	}

	public void openPage(WebDriver driver, String clientURL, String user) throws IOException {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(3));

		loginClient(clientURL, user);
	}

	public void checkPageOpened(String element) throws IOException {

		isloadComplete(driver);

		if (driver.getPageSource().contains(element)) {
			reporter.info("INFO : The " + element + " page is opened.");
			Assert.assertTrue("[EXPECTED] The " + element + " page is opened.", true);
		} else {
			reporter.info("INFO : The " + element + " page is NOT opened.");
			Assert.fail("[ACTUAL] The " + element + " page is NOT opened.");
		}

		new AttachFile().Screenshot();
	}

	public void openPageViaLink(String URL, String user) throws IOException
	{
		closeAllWindowsExceptParent(driver);

		if(!URL.contains(CommonVariables.baseURL)) {

			URL = CommonVariables.baseURL + URL;
		}

		loginClient(URL, user);
	}

	public void loginClient(String clientURL, String user) throws IOException {

		LoginPO login = new LoginPO();

		login.openLoginPage(clientURL);

		login.LogIn(clientURL, user);

		new DevToolsHelper().retrieveLogInfo(driver, "getMemberBalance");
	}

	public void navigateToURL(WebDriver driver, String url) {

		if (!url.endsWith("/")) {
			url = url + "/";
		}

		if (driver.getTitle().isEmpty()) {
			try {
				driver.get(url);
			} catch (TimeoutException ex) {	}
		} else {
			driver.navigate().to(url);
		}

		if (isloadComplete(driver)) {
			System.out.println("The page '" + driver.getTitle() + "' is loaded.");
		} else {
			System.out.println("The page is NOT loaded.");
		}
	}

	public boolean isloadComplete(WebDriver driver) {
		String result, loader;
		boolean loaded = false;

		for (int i = 0; i < 10; i++) {
			result = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
			loader = String.valueOf(((JavascriptExecutor) driver)
					.executeScript("return document.getElementsByClassName('loader small-loader').length!=0"));
			if (result.equals("complete") && loader.equals("false")) {
				loaded = true;
				break;
			} else {
				needToSleep(1000);
			}
		}
		needToSleep(1000);
		return loaded;
	}

	public void isModalDisplayed(String action) {
		for(int i=0; i<10;i++) {
			if(driver.findElements(By.xpath("//*[contains(@id, 'sportsbookModal-label')]")).size()>0) {
				if(action.equals("close")) {
					try {
						driver.findElement(By.xpath("//*[contains(@id,'sportsbookModal-label')]//following-sibling::button")).click();
					}catch(Exception e) { System.out.println("WARNING: Click on Modal Close button cannot be performed!");}
				}
				break;
			}
			else {
				needToSleep(1000);
			}
		}
	}

	public WebElement setButton(String button) {
		buttonToClick = null;

		if (button.contains("LoginForm")) {
			buttonToClick = new LoginPO().buttonLoginForm;
		} else if (button.contains("LoginSubmit")) {
			buttonToClick = new LoginPO().buttonLoginSubmit;
		} else if (button.contains("Forgotten")) {
			buttonToClick = new LoginPO().labelForgottenPassword;
		} else {
			buttonToClick = driver.findElement(By.xpath("//*[contains(text(), '" + button + "')]"));
		}

		return buttonToClick;
	}

	// Write Text
	public void writeText(WebDriver driver, WebElement element, String text) {

		Actions seriesOfActions = new Actions(driver);
		element.clear();
		seriesOfActions.moveToElement(element).click().sendKeys(element, text).perform();

		if (element.isEnabled() && !element.getAttribute("value").equals(text)) {
			element.click();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].value='" + text + "';", element);

			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
			js.executeScript("arguments[0].value='';", element);
			element.sendKeys(Keys.ESCAPE);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.CONTROL + "v");
			element.sendKeys(Keys.ENTER);
		}
		System.out.println("Entered value : " + element.getAttribute("value"));
		reporter.info("INFO : Enter : " + text);
	}

	public void needToSleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void closeAllWindowsExceptParent(WebDriver driver) {

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		int tabsNumber = tabs.size();

		if (tabsNumber > 1) {

			for (int i = tabsNumber - 1; i > 0; i--) {
				String winHandle = tabs.toArray()[i].toString();
				driver.switchTo().window(winHandle);
				driver.close();
			}
			driver.switchTo().window(tabs.get(0));
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
