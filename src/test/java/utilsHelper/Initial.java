package utilsHelper;

public class Initial {

	public String testURL;

	public void environment() {

		String environment = "dev";

		if(System.getProperty("test.env")!=null) {
			environment = System.getProperty("test.env");
			System.out.println("System Environment = " + System.getProperty("test.env"));
		}

		System.out.println("Tests will run on " + environment + " Environment");
		CommonVariables.environment = environment;
	}

	public void initURL() {

		if(System.getProperty("test.URL")!=null) {
			testURL = System.getProperty("test.URL");
			System.out.println("System URL = " + System.getProperty("test.URL"));
		}
		else {
			testURL = PropertyUtils.getProperty("home.page");
		}
		System.out.println("Tests will run with URL : " + testURL);
		CommonVariables.baseURL = testURL;
	}

//	public void reportPathName() {
//		//get current date & time
//		String date =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm"));
//
//		//set current date & time into report name
//		ExtentProperties.INSTANCE.setReportPath("output" + File.separator + "Report_" + date + File.separator
//				+ "report.html");
//	}

}
