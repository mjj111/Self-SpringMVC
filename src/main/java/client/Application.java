package client;

import spring.ioc.annotation.ComponentScan;
import spring.ioc.annotation.Configuration;
import spring.mvc.web.TomcatStarter;

@Configuration
@ComponentScan(value = {"client"})
public class Application {
    public static void main(String[] args) {
        final TomcatStarter tomcatStarter = new TomcatStarter();
        tomcatStarter.start();
        tomcatStarter.await();
    }
}
