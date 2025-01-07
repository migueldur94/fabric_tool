package dev.mike.infrastructure.inMemory;

public class UrlTemenos {

    public static String URL;
    public static String OBJECT_NAME;
    public static String MAPPING_NAME;
    public static String VERB_NAME;

    public static void splitDetails(String url) {
        UrlTemenos.URL = url;
        String[] paths = url.split("/");
        int postionVersion = -1;
        for (String path : paths) {
            postionVersion++;
            if (path.indexOf("v1") != -1) {
                break;
            }
        }
        if (postionVersion > -1) {
            UrlTemenos.OBJECT_NAME = paths[postionVersion + 1];
            UrlTemenos.MAPPING_NAME = paths[postionVersion + 3];
            UrlTemenos.VERB_NAME = paths[postionVersion + 4];
        }
    }
}
