package dev.mike.infrastructure.authentication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import org.json.JSONObject;

import java.io.IOException;

public class FetchAuthentication {

    public void execute(Configuration configuration) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(configuration.getServerDomain() + "/mfconsole/accountInfo")
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", Cookie.COOKIE)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String bodyText = response.body().string();
                JSONObject jsonObject = new JSONObject(bodyText);
                Token.TOKEN_AUTHENTICATION = jsonObject.get("authToken").toString();
            } else {
                throw new RuntimeException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in FetchCookie");
        }
    }
}
