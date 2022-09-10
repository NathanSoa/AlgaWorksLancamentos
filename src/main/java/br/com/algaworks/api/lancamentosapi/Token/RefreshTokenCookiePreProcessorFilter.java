package br.com.algaworks.api.lancamentosapi.Token;

import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(request.getCookies() != null
            && request.getParameter("grant_type").equals("refresh_token")
            && request.getRequestURI().equalsIgnoreCase("/oauth/token")){

            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("refreshToken")){
                    String refreshToken = cookie.getValue();
                    request = new MyServletRequestWraper(request, refreshToken);
                }
            }
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private static class MyServletRequestWraper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyServletRequestWraper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap(){
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] {refreshToken});
            map.setLocked(true);
            return map;
        }
    }
}
