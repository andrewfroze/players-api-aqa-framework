package org.andrewfroze.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private final Properties props = new Properties();

    public PropertyReader(String resourceName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is != null) {
                props.load(is);
            } else {
                throw new RuntimeException("Property resource not found: " + resourceName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String get(String key) {
        return props.getProperty(key);
    }


    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
