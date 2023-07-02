package com.codecool.hackernews.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        sce.getServletContext().setAttribute("web_app", application);
        TemplateEngine engine = constructTemplateEngine(application);
        TemplateEngineUtil.setTemplateEngine(sce.getServletContext(), engine);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private TemplateEngine constructTemplateEngine(IWebApplication webApplication) {
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(getTemplateResolver(webApplication));
        return engine;
    }

    private ITemplateResolver getTemplateResolver(IWebApplication webApplication) {
        WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(webApplication);
        resolver.setPrefix("/templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }
}
