package dev.mike.infrastructure.tool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;

public class GetProcessorInfomation {

    public static List<String> getProcessorInformation(JSONObject customCode, String processorName) {
        List<String> listProcessor = new ArrayList<>();
        if (customCode == null) {
            return listProcessor;
        }
        if (customCode.isNull(processorName)) {
            return listProcessor;
        }
        if (customCode.get(processorName) instanceof JSONArray) {
            JSONArray posProcessorJSON = customCode.getJSONArray(processorName);
            getArray(posProcessorJSON, listProcessor);
        } else {
            String valueProcessor = customCode.getString(processorName);
            if (valueProcessor.startsWith("[")) {
                getArray(new JSONArray(customCode.getString(processorName)), listProcessor);
            } else {
                if (valueProcessor.contains(",")) {
                    listProcessor.addAll(Arrays.asList(valueProcessor.split(",")));
                } else {
                    listProcessor.add(valueProcessor);
                }
            }
        }

        return listProcessor;
    }

    private static void getArray(JSONArray posProcessorJSON, List<String> listProcessor) throws JSONException {
        for (int i = 0; i < posProcessorJSON.length(); i++) {
            JSONObject item = posProcessorJSON.getJSONObject(i);
            if (!item.getBoolean("disabled")) {
                listProcessor.add(item.getString("name"));
            }
        }
    }
}
