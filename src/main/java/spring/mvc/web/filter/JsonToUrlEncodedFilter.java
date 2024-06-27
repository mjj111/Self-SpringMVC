package spring.mvc.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter("/*")
public class JsonToUrlEncodedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("POST".equalsIgnoreCase(((HttpServletRequest) request).getMethod())
                && "application/json".equalsIgnoreCase(request.getContentType())) {
            request = new JsonToUrlEncodedRequestWrapper(request);
        }
        chain.doFilter(request, response);
    }
}