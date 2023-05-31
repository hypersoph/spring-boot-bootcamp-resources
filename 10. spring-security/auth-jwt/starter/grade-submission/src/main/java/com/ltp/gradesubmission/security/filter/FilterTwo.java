package com.ltp.gradesubmission.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;

public class FilterTwo implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        User user = new ObjectMapper().readValue(((HttpServletRequest) request).getInputStream(), User.class);
        
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        System.out.println("FILTER TWO");

        chain.doFilter(request, response);
    }
    
}
