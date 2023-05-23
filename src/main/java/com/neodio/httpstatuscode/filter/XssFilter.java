package com.neodio.httpstatuscode.filter;

import com.nhncorp.lucy.security.xss.XssSaxFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class XssFilter implements Filter {

    private XssSaxFilter xssSaxFilter = XssSaxFilter.getInstance("lucy-xss-sax.xml", true);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        chain.doFilter(new XssFilterWrapper(request, xssSaxFilter), response);
    }

    @Override
    public void destroy() {}

}