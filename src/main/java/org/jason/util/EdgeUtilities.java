package org.jason.util;

public abstract class EdgeUtilities {
    public static String toThingworxPropertyName(String propertyName) {
        return propertyName.substring(1).replace('/', '_');
    }
}
