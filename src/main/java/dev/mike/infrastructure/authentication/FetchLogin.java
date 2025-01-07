package dev.mike.infrastructure.authentication;

import okhttp3.*;
import dev.mike.core.authentication.User;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;

import java.io.IOException;

public class FetchLogin {

    public void execute(Configuration configuration, User user) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"primary_email\": \"" + user.getUser() + "\",\r\n    \"password\": \"" + user.getPassword() + "\",\r\n    \"oauth_token\": \"" + Token.TOKEN_LOGIN + "\"\r\n}");
        Request request = new Request.Builder()
                .url(configuration.getServerDomain() + "/authService/accounts/oauth/authorize?oauth_token=" + Token.TOKEN_LOGIN + "&doredirect=false&provider=userstore&domain=null")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Cookie", Cookie.COOKIE)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in FetchCookie");
        }
    }
}
