package driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.service.ExtentService;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilsHelper.CheckEnvironment;
import utilsHelper.CommonVariables;

/**
 * The Class SharedDriver.
 */
public class SharedDriver {

	/** The browser type. */
	String browserType;

	/**
	 * Sets the browser.
	 */
	public void setBrowser() {

		browserType = System.getProperty("browser");

		if (browserType == null || browserType.equals(null) || browserType.equals("null") || browserType.isEmpty()) {
			browserType = "chrome";
		}
	}

	/**
	 * Gets the browser.
	 *
	 * @return the browser
	 */
	public String getBrowser() {
		setBrowser();
		System.out.println("Tests will run on a " + browserType + " browser" );
		return browserType;
	}

	/**
	 * Instantiates a new shared driver.
	 */
	public SharedDriver() {

		String hubLink = "remoteIP/wd/hub";
		if (DriverFactory.getDriver() == null || CommonVariables.newDriver) {
			WebDriver driver = null;

			switch (getBrowser()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeDriverService service = new ChromeDriverService.Builder().build();
				driver = new ChromeDriver(service, setAdditionalChromeSettings());			    
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "remoteChrome":
				try {
					driver = new RemoteWebDriver(new URL(hubLink), getChromeCapabilities());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				break;
			}
			driver.manage().window().setPosition(new Point(0, 0));
			driver.manage().window().setSize(new Dimension(1500, 900));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

			DriverFactory.addDriver(driver);

			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();

			if (CommonVariables.browserName == null || CommonVariables.browserName.equals(null)
					|| CommonVariables.browserName.equals("null") || CommonVariables.browserName.isEmpty()
					|| CommonVariables.browserName == "") {
				CommonVariables.browserName = cap.getBrowserName();
				CommonVariables.browserVersion = cap.getBrowserVersion();
				//get system info and write on report page
				ExtentReports report = ExtentService.getInstance();
				report.setSystemInfo("User Name", System.getProperty("user.name"));
				report.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
				report.setSystemInfo("Machine", System.getProperty("os.name") + " / " + System.getProperty("os.version"));
				report.setSystemInfo("Java Version", System.getProperty("java.version"));
				report.setSystemInfo("Browser", CommonVariables.browserName);
				report.setSystemInfo("Driver Version", CommonVariables.browserVersion);
			}
		}
	}

	/**
	 * Gets the chrome capabilities.
	 *
	 * @return the chrome capabilities
	 */
	private DesiredCapabilities getChromeCapabilities() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browserType);
		capabilities.setPlatform(Platform.valueOf(new CheckEnvironment().getOS().name()));

		return capabilities;
	}

	/**
	 * Sets the additional chrome settings.
	 *
	 * @return the chrome options
	 */
	private ChromeOptions setAdditionalChromeSettings() {

		ChromeOptions options = new ChromeOptions();

		CommonVariables.headless = false;

		if (new CheckEnvironment().getOS().name().equals("LINUX")) {
			// headless options
			CommonVariables.headless = true;
			options.addArguments("--headless --disable-gpu");
			options.addArguments("--headless=new");
		}

		options.addArguments("--window-size=1380x1000");
		options.addArguments("--disable-search-engine-choice-screen");

		options.addArguments("--whitelisted-ips");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");
		options.addArguments("--enable-accessibility-tab-switcher");
		options.addArguments("--enable-contextual-search");
		options.addArguments("--verbose");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--dns-prefetch-disable");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-default-apps");

		return options;
	}
}
