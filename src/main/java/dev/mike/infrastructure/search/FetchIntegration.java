package dev.mike.infrastructure.search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.search.Integration;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import dev.mike.infrastructure.tool.GetProcessorInfomation;
import org.json.JSONObject;

import java.io.IOException;

public class FetchIntegration {

    public void execute(String url, Integration integration) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").addHeader("Cookie", Cookie.COOKIE).addHeader("X-Kony-Authorization", Token.TOKEN_AUTHENTICATION).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String bodyText = response.body().string();
                JSONObject jsonObject = new JSONObject(bodyText);
                if (jsonObject.has("payload")) {
                    jsonObject = new JSONObject(jsonObject.getString("payload"));

                    integration.setName(jsonObject.getString("name"));
                    JSONObject operationconfig = jsonObject.getJSONObject("operationconfig");
                    if (operationconfig.has("className")) {
                        integration.setClassName(operationconfig.getString("className"));
                    }
                    integration.setPreprocessors(GetProcessorInfomation.getProcessorInformation(operationconfig, "preprocessorname"));
                    integration.setPostprocessors(GetProcessorInfomation.getProcessorInformation(operationconfig, "postprocessorname"));
                    return;
                }
            } else {
                System.out.println("Request failed with code: " + response.code() + ":" + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Something was wrong fetching object verb");
    }
}
