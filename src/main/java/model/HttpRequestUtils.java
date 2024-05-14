package model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils {

    private HttpRequestUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, String> parseQueryString(final String queryString) {
        Map<String, String> result = new HashMap<>();
        String[] splintedQuery = queryString.split("&");

        for(String sentence : splintedQuery) {
            String[] query = sentence.split("=");

            String key = query[0];
            String value = query[1];

            result.put(key, value);
        }

        return result;
    }
}
