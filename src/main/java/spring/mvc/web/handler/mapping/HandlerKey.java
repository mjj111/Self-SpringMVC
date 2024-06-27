package spring.mvc.web.handler.mapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

public class HandlerKey {
    private String url;
    private RequestMethod method;

    public HandlerKey(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public static HandlerKey from(final HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        return new HandlerKey(uri, method);
    }

    @Override
    public String toString() {
        return "HandlerKey [url= " + url + ", requestMethod=" + method + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return Objects.equals(url, that.url) && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }
}
