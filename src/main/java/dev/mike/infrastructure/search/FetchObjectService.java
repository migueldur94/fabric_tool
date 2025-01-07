package dev.mike.infrastructure.search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import dev.mike.infrastructure.inMemory.UrlTemenos;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FetchObjectService {

    public String execute(Configuration configuration) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(configuration.getServerDomain() + "/workspace/100000002/api/v1/ws/100000002/objectsvcs?$select=endpoints/meta,versions,service_config")
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", Cookie.COOKIE)
                .addHeader("X-Kony-Authorization", Token.TOKEN_AUTHENTICATION)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String bodyText = response.body().string();
                JSONObject jsonObject = new JSONObject(bodyText);
                if (jsonObject.has("content") && jsonObject.get("content") instanceof JSONArray) {
                    JSONArray contentArray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject item = contentArray.getJSONObject(i);

                        if (item.has("name") && UrlTemenos.OBJECT_NAME.equals(item.getString("name"))) {

                            return item.getJSONObject("_links").getJSONObject("self").getString("href");
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
        throw new RuntimeException("Something was wrong fetching Object service");
    }
}
