package utilsHelper;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetJsonValues {

	static String result = null;

	public String findKeyValue(JSONObject jsonObj, String testKey) throws JSONException 
	{
		Iterator<?> keys = jsonObj.keys();
		String key = null;
		result=null;

		while( keys.hasNext() ) {
			key = (String)keys.next();
			if (key.equals(testKey)) {
				//System.out.println(jsonObj.get(key));
				return result = jsonObj.get(key) + ""; 
			}

			if ( jsonObj.get(key) instanceof JSONObject ) {
				//System.out.println(jsonObj.get(key));
				result = findKeyValue((JSONObject)jsonObj.get(key), testKey);
				if(result!=null)
					return result;
			}

			if ( jsonObj.get(key) instanceof JSONArray ) {
				JSONArray jar = (JSONArray)jsonObj.get(key);

				for (int i = 0; i < jar.length(); i++) {
					//System.out.println(jar.get(i));
					if(jar.get(i) instanceof String) {
						if(jar.get(i).equals(testKey)) {
							return testKey;
						}
					}
					else if(jar.get(i) instanceof Integer){
						if(jar.get(i).equals(testKey)) {
							return testKey;
						}
					}
					else if(jar.get(i) instanceof JSONArray){
						continue;
					}
					else {
						JSONObject j = jar.getJSONObject(i);
						result = findKeyValue(j, testKey);
						if(result!=null)
							return result;
					}
				}
			}
		}
		return result; 
	}
}