package dev.mike.infrastructure.tool;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieStore implements CookieJar {
    private final Map<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<okhttp3.Cookie> cookies) {
        cookieStore.put(url, cookies);
    }

    @Override
    public List<okhttp3.Cookie> loadForRequest(HttpUrl url) {
        List<okhttp3.Cookie> cookies = cookieStore.get(url);
        return cookies != null ? cookies : new ArrayList<>();
    }

    public Map<HttpUrl, List<Cookie>> getCookieStore() {
        return cookieStore;
    }
}
