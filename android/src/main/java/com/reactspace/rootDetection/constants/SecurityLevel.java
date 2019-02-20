package com.reactspace.rootDetection.constants;

public enum SecurityLevel {
    LOW,
    MEDIUM,
    HIGH;

    public static SecurityLevel safeValueOf(String name, SecurityLevel defaultValue) {
        try {
            return SecurityLevel.valueOf(name);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
