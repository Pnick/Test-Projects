package utilsHelper;

import java.util.Optional;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v129.network.Network;
import org.openqa.selenium.devtools.v129.network.model.Request;
import org.openqa.selenium.devtools.v129.network.model.RequestId;
import org.openqa.selenium.devtools.v129.network.model.Response;

public class DevToolsHelper {

	public JSONObject json = new JSONObject();

	public void retrieveLogInfo(WebDriver driver, String data) {

		DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(),Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), requestConsumer-> {
			//Request request = requestConsumer.getRequest();
			//if(request.getUrl().contains("getMemberBalance")) {
			//System.out.println(request.getUrl());
			//}
		});

		final RequestId[] requestIds = new RequestId[1];

		devTools.addListener(Network.responseReceived(), responseConsumer -> {
			Response response = responseConsumer.getResponse();
			requestIds[0] = responseConsumer.getRequestId();

			if(response.getUrl().contains(data)) {
				System.out.println(response.getStatus() + " -> " + response.getUrl());
				String responseBody = devTools.send(Network.getResponseBody(requestIds[0])).getBody();
				if(!responseBody.isEmpty()) {
					json = new JSONObject(responseBody);
				}
				else {
					json = new JSONObject("{}");
				}
				CommonVariables.jsonStat = json;
			}
		});
	}
}