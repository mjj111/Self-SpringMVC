package spring.mvc.handler.mapping;

import java.util.Objects;

public class HandlerKey {
    private String url;
    private RequestMethod method;

    public HandlerKey(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String toString() {
        return "HandlerKey [url= {}" + url + ", requestMethod=" + method + "]";
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
