package lab7;

import lab5.utils.GlobalConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();

        try {
            GlobalConfig config = new GlobalConfig();
            config.loadGlobalConfig();
            application.setAttribute("shop.images.root", config.getProperty("shop.images.root"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
