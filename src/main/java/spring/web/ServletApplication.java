package spring.web;

public class ServletApplication {
    public static void main(String[] args) {
        final TomcatStarter tomcatStarter = new TomcatStarter();
        tomcatStarter.start();
        tomcatStarter.await();
    }
}
