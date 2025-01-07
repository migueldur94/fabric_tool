package dev.mike.infrastructure.search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.configuration.Configuration;
import dev.mike.core.search.ObjectService;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import dev.mike.infrastructure.inMemory.UrlTemenos;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FetchOperationObject {

    public String execute(String url, ObjectService objectService, Configuration configuration) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", Cookie.COOKIE)
                .addHeader("X-Kony-Authorization", Token.TOKEN_AUTHENTICATION)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String bodyText = response.body().string();
                JSONObject jsonObject = new JSONObject(bodyText);
                if (jsonObject.has("objects") && jsonObject.get("objects") instanceof JSONArray) {
                    JSONArray contentArray = jsonObject.getJSONArray("objects");
                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject item = contentArray.getJSONObject(i);

                        if (item.has("name") && UrlTemenos.MAPPING_NAME.equals(item.getString("name"))) {
                            String urlVerb = item.getJSONObject("_links").getJSONObject("verbs").getString("href");
                            String[] paths = urlVerb.split("\\/");
                            objectService.setLink(configuration.getServerDomain() + "/mfconsole/#/servicemgmt/api/objectservices/" + item.getString("projectId") + "/objects/" + paths[paths.length - 2] + "/mapping");
                            return urlVerb;
                        }
                    }
                }
            } else {
                throw new RuntimeException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in FetchCookie");
        }
        throw new RuntimeException("Something was wrong fetching operation object");
    }
}
