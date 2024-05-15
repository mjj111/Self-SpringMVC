package util;

import java.io.BufferedReader;
import java.io.IOException;


public class IOUtils {
    private IOUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String readData(final BufferedReader br, final int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return convertJsonToUrl(String.copyValueOf(body));
    }

    public static String convertJsonToUrl(final String json) {
        StringBuffer sb = new StringBuffer();
        String[] keyValuePairs = json.replaceAll("[{}\"]", "").split(",");

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            sb.append(key).append("=").append(value).append("&");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}