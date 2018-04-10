package com.fos;

import com.fos.tools.Logging;
import databaseupdater.SqlUpdate;
import com.fos.tools.Helper;
import org.apache.logging.log4j.Level;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;


/**
 * wird gestartet wenn der Tomcat Server started.
 */
public class OnStartup implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    /***
     * wird ausgeführt wenn der Server started
     */
    public OnStartup() {

        try {
            Connection connection = Helper.getConnection();
            new SqlUpdate(Helper.getDbchema(), false).UpdateDatabase(connection);
        } catch (Exception e) {
            Logging.logMessage("error by updateing the database", Level.FATAL);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
