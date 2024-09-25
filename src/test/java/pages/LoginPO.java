package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import driver.DriverFactory;
import utilsHelper.CommonVariables;
import utilsHelper.PropertyUtils;

public class LoginPO extends LoadableComponent<LoginPO>{

	public WebDriver driver;
	public WebDriverWait wait; // define Explicit wait for specific conditions(example: ExpectedConditions.elementToBeClickable)
	public CommonVariables var = new CommonVariables();
	BaseMethodsO method = new BaseMethodsO(var);
	ExtentTest reporter = ExtentCucumberAdapter.getCurrentStep();

	@FindBy(xpath = "//*[@type='button' and contains(text(), 'Log In')]")
	public WebElement buttonLoginForm;

	@FindBy(xpath = "//*[@type='submit' and contains(text(), 'Log In')]")
	public WebElement buttonLoginSubmit;

	@FindBy(xpath = "//*[contains(text(), 'Forgotten Password')]")
	public WebElement labelForgottenPassword;

	@FindBy(xpath = "//*[contains(@id, 'login_form[username]')]")
	public WebElement inputUsername;

	@FindBy(xpath = "//*[contains(@id, 'login-modal-password-input')]")
	public WebElement inputPassword;

	public LoginPO() {
		driver = DriverFactory.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));	// set implicit wait
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(4)); 		// set explicit wait
		PageFactory.initElements(driver, this);
	}

	public void openLoginPage(String loginURL) throws IOException {

		driver.manage().deleteAllCookies();
		method.navigateToURL(driver, loginURL);

		if (buttonLoginForm.isDisplayed()) {
			buttonLoginForm.click();
		}
	}

	public void enterCredentials(String username, String password) {

		method.isloadComplete(driver);
		method.needToSleep(1000);

		if(username.contains("valid.username") && !username.contains("invalid")) {
			method.writeText(driver, inputUsername, PropertyUtils.getProperty(CommonVariables.environment + "." + username));
		}
		else if(username.equals("invalid.username")) {
			method.writeText(driver, inputUsername, PropertyUtils.getProperty("invalid.username"));
		}
		else if(username.equals("inactive.username")) {
			method.writeText(driver, inputUsername, PropertyUtils.getProperty("inactive.username"));
		}
		else {
			method.writeText(driver, inputUsername, username);
		}

		if(password.contains("valid.password") && !password.contains("invalid")) {
			method.writeText(driver, inputPassword, PropertyUtils.getProperty(CommonVariables.environment + "." + password));
		}
		else if(password.equals("invalid.password")) {
			method.writeText(driver, inputPassword, PropertyUtils.getProperty("invalid.password"));
		}
		else {
			method.writeText(driver, inputPassword, password);
		}
	}

	public void LogIn(String clientURL, String user) {

		if(buttonLoginSubmit.isDisplayed()) {

			enterCredentials(user, "valid.password");

			buttonLoginSubmit.click();
		}
		method.isloadComplete(driver);
	}

	public void clickLoginButton(String button) {

		if(button.equals("LoginForm") && buttonLoginForm.isDisplayed()) {
			buttonLoginForm.click();
		}
		else if(button.equals("LoginSubmit") && buttonLoginSubmit.isDisplayed()) {
			buttonLoginSubmit.click();
		}
		method.isloadComplete(driver);
	}


	Set<Cookie> allCookies;
	public void closeBrowser() {

		allCookies = driver.manage().getCookies();
		driver.close();
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
