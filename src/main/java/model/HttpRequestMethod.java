package model;

public enum HttpRequestMethod {
    POST("POST"), GET("GET"), PATCH("PATCH"), DELETE("DELETE");

    private final String value;

    HttpRequestMethod(final String value) {
        this.value = value;
    }

    public boolean equals(final String op) {
        return op.equals(value);
    }
}
