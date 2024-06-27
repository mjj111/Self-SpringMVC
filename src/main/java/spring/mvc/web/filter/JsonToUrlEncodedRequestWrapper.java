package spring.mvc.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class JsonToUrlEncodedRequestWrapper extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;
    private Map<String, String[]> params = new HashMap<>();

    public JsonToUrlEncodedRequestWrapper(ServletRequest request) throws IOException {
        super((HttpServletRequest) request);
        cacheInputStream(request);
        parseJsonToParams();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) {
            cacheInputStream(super.getRequest());
        }
        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        return values != null ? values[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    private void cacheInputStream(ServletRequest request) throws IOException {
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(request.getInputStream(), cachedBytes);
    }

    private void parseJsonToParams() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(cachedBytes.toByteArray(), Map.class);
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            params.put(entry.getKey(), new String[]{entry.getValue().toString()});
        }
    }

    private static class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream buffer;

        public CachedServletInputStream(byte[] contents) {
            this.buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }
    }
}
