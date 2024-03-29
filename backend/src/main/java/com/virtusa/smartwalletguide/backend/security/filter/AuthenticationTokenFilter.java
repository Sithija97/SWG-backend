

package com.virtusa.smartwalletguide.backend.security.filter;

import com.virtusa.smartwalletguide.backend.security.AppConstant;
import com.virtusa.smartwalletguide.backend.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }


    @Override
    public FilterConfig getFilterConfig() {
        return super.getFilterConfig();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        tokenUtils = WebApplicationContextUtils
                .getRequiredWebApplicationContext(this.getServletContext())
                .getBean(TokenUtils.class);



        userDetailsService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(this.getServletContext())
                .getBean(UserDetailsService.class);

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, " + AppConstant.tokenHeader);


        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println(httpRequest.getRequestURL().toString().contains("auth"));
        if(httpRequest.getRequestURL().toString().contains("auth"))
            chain.doFilter(request,response);
        else {
            String authToken = httpRequest.getHeader(AppConstant.tokenHeader);
            String username = this.tokenUtils.getUsernameFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (this.tokenUtils.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            chain.doFilter(request, response);
        }
    }

}

