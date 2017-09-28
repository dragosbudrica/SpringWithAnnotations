package com.kepler.rominfo.filter;

import com.kepler.rominfo.dao.AuthorizationDao;
import com.kepler.rominfo.dao.ResourceDao;
import com.kepler.rominfo.domain.vo.Resource;
import com.kepler.rominfo.domain.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    private static final Log LOGGER = LogFactory.getLog(AuthenticationFilter.class);

    private static final String LOGIN_PAGE = "/login";
    private static final String UNAUTHORIZED = "/accessDenied";

    private AuthorizationDao authorizationDao;
    private ResourceDao resourceDao;

    @Autowired
    public void setAuthorizationMapper(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    @Autowired
    public void setResourceMapper(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String resourceRequested = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        if (resourceRequested.contains("/login") || resourceRequested.contains("/doLogin") ||
                resourceRequested.contains("css") || resourceRequested.contains("js") || resourceRequested.contains("resources")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            User user = (User) httpServletRequest
                    .getSession().getAttribute("user");

            if (user != null) {
                LOGGER.debug("user is logged in");
                // user is logged in, continue request
                resourceRequested = ((HttpServletRequest) servletRequest).getRequestURL().toString();
                String[] parts = resourceRequested.split("/");

                Resource res = resourceDao.getResourceByName(parts[parts.length - 1]);

                if (!authorizationDao.isAuthorized(user.getRole().getRoleId(), res.getResourceId())) {
                    httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath()
                                    + UNAUTHORIZED);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }

            } else {
                LOGGER.debug("user is not logged in");
                // user is not logged in, redirect to login page
                httpServletResponse.sendRedirect(
                        httpServletRequest.getContextPath()
                                + LOGIN_PAGE);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void destroy() {
        // close resources
    }
}
