package utilsHelper;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonVariables {

	public static 	String 						baseURL;
	public static	String						environment;
	public static	boolean						headless;
	public static	boolean						newDriver = false;
	public static	String						browserName 	= "";
	public static 	String 						browserVersion 	= "";
	public static	JSONObject					jsonStat;

	public 			WebElement 					element;
	public			WebDriverWait 				wait;
}
