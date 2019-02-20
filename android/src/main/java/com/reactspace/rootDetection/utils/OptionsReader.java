package com.reactspace.rootDetection.utils;

import com.facebook.react.bridge.ReadableMap;

public class OptionsReader {
    public static String getString(ReadableMap options, String key) {
        if (options == null || !options.hasKey(key)) {
            return null;
        }

        return options.getString(key);
    }
}
