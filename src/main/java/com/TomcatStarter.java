package com;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatStarter {

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private final Tomcat tomcat;

    public TomcatStarter() {
        this(WEBAPP_DIR_LOCATION);
    }

    public TomcatStarter(final String webappDirLocation) {
        this.tomcat = new Tomcat();
        tomcat.setConnector(createConnector());

        String docBase = new File(webappDirLocation).getAbsolutePath();
        StandardContext context = (StandardContext) tomcat.addWebapp("", docBase);
    }

    public void start() {
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    public void await() {
        tomcat.getServer().await();
    }

    public void stop() {
        try {
            tomcat.stop();
            tomcat.destroy();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    private Connector createConnector() {
        Connector connector = new Connector();
        connector.setPort(8080);
        return connector;
    }
}

