package org.andrewfroze.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHolder {

    public static Logger get(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }
}
