package http.controller;

import http.view.request.HttpRequest;
import http.view.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
