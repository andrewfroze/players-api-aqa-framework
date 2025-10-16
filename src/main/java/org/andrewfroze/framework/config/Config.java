package org.andrewfroze.framework.config;

import org.andrewfroze.framework.utils.PropertyReader;

public class Config {

    private static final PropertyReader reader = new PropertyReader("application.properties");


    public static String baseUrl() {
        return reader.get("base.url");
    }


    public static String basePath() {
        return reader.get("api.basePath", "/");
    }


    public static String threads() {
        return reader.get("threads", "3");
    }


    public static int requestTimeoutSeconds() {
        return Integer.parseInt(reader.get("request.timeout.seconds", "10"));
    }
}
