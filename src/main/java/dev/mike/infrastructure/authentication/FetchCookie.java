package dev.mike.infrastructure.authentication;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.authentication.User;
import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import dev.mike.infrastructure.tool.CookieStore;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class FetchCookie {

    public void execute(Configuration configuration, User user) {
        CookieStore cookieJar = new CookieStore();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cookieJar(cookieJar)
                .build();
        Request request = new Request.Builder()
                .url(configuration.getServerDomain() + "/mfconsole/")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assignToken(response);
                assignCookie(cookieJar, user);
            } else {
                throw new RuntimeException("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error in FetchCookie");
        }

    }

    private void assignCookie(CookieStore cookieJar, User user) {
        List<okhttp3.Cookie> cookie = cookieJar.getCookieStore().entrySet().stream().filter(value -> value.getKey().toString().indexOf("mfconsole") != -1).findFirst().get().getValue();
        try {
            Cookie.COOKIE = "user_to_recent_app_map=" + URLEncoder.encode("{\"" + user.getUser() + "\":{}}", "UTF-8") + "; JSESSIONID=" + cookie.get(0).value();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void assignToken(Response response) {
        Token.TOKEN_LOGIN = response.request().url().queryParameter("oauth_token");
    }
}
