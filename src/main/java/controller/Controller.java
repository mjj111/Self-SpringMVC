package controller;

import view.request.HttpRequest;
import view.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
