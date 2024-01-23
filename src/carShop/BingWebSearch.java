package carShop;

import java.net.*;
import java.io.*;
import org.json.*;

public class BingWebSearch {
	
	private static String contentUrl;
	
    public static String searchFor(String query) throws Exception {
        String subscriptionKey = "0716e7f8327840deb515c03ae6a8e182";
        String url = "https://api.bing.microsoft.com//v7.0/images/search";
        
        URL obj = new URL(url + "?q=" + URLEncoder.encode(query, "UTF-8") + "&count=1");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("GET");
        con.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        JSONObject jsonObj = new JSONObject(response.toString());
        JSONArray results = jsonObj.getJSONArray("value");
        
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            contentUrl = result.getString("contentUrl");
            System.out.println("Content URL: " + contentUrl);
        }
        return contentUrl;
    }
}