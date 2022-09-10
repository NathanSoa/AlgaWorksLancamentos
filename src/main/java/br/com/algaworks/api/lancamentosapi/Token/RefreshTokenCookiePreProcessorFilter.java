package br.com.algaworks.api.lancamentosapi.Token;

import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.apache.catalina.util.ParameterMap;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RefreshTokenPreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(request.getCookies() != null
            && request.getAttribute("refresh_token") != null
            && request.getRequestURI().equals("/oauth/token")){

            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("refreshToken")){

                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static class MyServletWraper extends HttpServletRequestWrapper{


        public MyServletWraper(HttpServletRequest request, String refreshToken) {
            super(request);
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] {refreshToken});
            map.setLocked(true);
        }
    }
}
