package com.qa.blog.springweb.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.blog.springweb.exception.ErrorDetails;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteActionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ("DELETE".equalsIgnoreCase(httpRequest.getMethod())) {
            String userHeader = httpRequest.getHeader("X-user");
            if (!"admin".equals(userHeader)) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ErrorDetails errorDetails = new ErrorDetails("403", "Only admins can delete blog posts");
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(errorDetails);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(jsonResponse);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}