package dev.mike.infrastructure.search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import dev.mike.core.configuration.Configuration;
import dev.mike.core.search.Integration;
import dev.mike.core.search.ObjectService;
import dev.mike.infrastructure.inMemory.Cookie;
import dev.mike.infrastructure.inMemory.Token;
import dev.mike.infrastructure.inMemory.UrlTemenos;
import dev.mike.infrastructure.tool.GetProcessorInfomation;
import org.json.JSONObject;

import java.io.IOException;

public class FetchVerb {

    public static String execute(String url, ObjectService objectService, Integration integration, Configuration configuration) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").addHeader("Cookie", Cookie.COOKIE).addHeader("X-Kony-Authorization", Token.TOKEN_AUTHENTICATION).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String bodyText = response.body().string();
                JSONObject jsonObject = new JSONObject(bodyText);
                if (jsonObject.has(UrlTemenos.VERB_NAME)) {
                    JSONObject verbObject = jsonObject.getJSONObject(UrlTemenos.VERB_NAME);
                    JSONObject operationConfig = verbObject.getJSONObject("operationConfig");
                    JSONObject backendMeta = operationConfig.getJSONObject("backendMeta");
                    JSONObject customCode = null;
                    if (verbObject.has("customCode") && !verbObject.isNull("customCode")) {
                        customCode = verbObject.getJSONObject("customCode");
                    }

                    objectService.setPostprocessors(GetProcessorInfomation.getProcessorInformation(customCode, "responseProcessorName"));
                    objectService.setPreprocessors(GetProcessorInfomation.getProcessorInformation(customCode, "requestProcessorName"));

                    objectService.setMappedTo(operationConfig.getString("fullyQualifiedBackendVerbName"));

                    integration.setLink(configuration.getServerDomain() + "/mfconsole/#/servicemgmt/api/integration/" + backendMeta.getString("serviceId") + "/operation/" + backendMeta.getString("operationId"));

                    return configuration.getServerDomain() + "/mfconsole/projects/null/integration/" + backendMeta.getString("serviceId") + "/operations/" + backendMeta.getString("operationId");

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
