package com.kepler.rominfo.config.servlet;

import com.kepler.rominfo.config.spring.MainContextConfiguration;
import com.kepler.rominfo.config.spring.MvcConfiguration;
import com.kepler.rominfo.filter.AuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

public class LsmvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {MainContextConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {MvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new CharacterEncodingFilter() };
    }

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setAttribute("Constants", com.kepler.rominfo.utils.Constants.class);
        EnumSet<DispatcherType> disps = EnumSet.allOf(DispatcherType.class);

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);
        encodingFilter.addMappingForUrlPatterns(disps, false, "/*");
        encodingFilter.setInitParameter("encoding", "UTF-8");

        FilterRegistration.Dynamic authenticationFilter = servletContext.addFilter("authentication-filter", AuthenticationFilter.class);
        authenticationFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
