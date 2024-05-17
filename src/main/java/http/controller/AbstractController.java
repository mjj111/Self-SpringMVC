package http.controller;

import http.model.HttpRequestMethod;
import http.view.request.HttpRequest;
import http.view.response.HttpResponse;

public abstract class AbstractController implements Controller{
    @Override
    public void service(HttpRequest request, HttpResponse response) {

        if(HttpRequestMethod.POST.equals(request.getMethod())) {
            doPost(request, response);
        }
        else {
            doGet(request, response);
        }
    }

    protected void doGet(final HttpRequest request, final HttpResponse response) {
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) {
    }
}
