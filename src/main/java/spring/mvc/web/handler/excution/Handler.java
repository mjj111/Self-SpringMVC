package spring.mvc.web.handler.excution;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Handler {
    void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
