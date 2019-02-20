
package com.reactspace.rootDetection.react;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactspace.rootDetection.RootDetector;
import com.reactspace.rootDetection.RootDetectorConfig;
import com.reactspace.rootDetection.constants.SecurityLevel;
import com.reactspace.rootDetection.detectors.Callback;
import com.reactspace.rootDetection.utils.OptionsReader;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RNRootDetectionModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    @Override
    public String getName() {
        return "RNRootDetection";
    }

    public RNRootDetectionModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void checkRootStatus(ReadableMap readableConfig, final Promise promise) {
        String googleApiKey = OptionsReader.getString(readableConfig, "googleApiKey");
        SecurityLevel securityLevel = SecurityLevel.safeValueOf(OptionsReader.getString(readableConfig, "securityLevel"), SecurityLevel.HIGH);

        RootDetectorConfig config = new RootDetectorConfig();
        config.setGoogleApiKey(googleApiKey);
        config.setLevel(securityLevel);

        RootDetector.checkDeviceRootStatus(reactContext, config, new Callback() {
            @Override
            public void onResult(boolean isRooted) {
                promise.resolve(isRooted);
            }

            @Override
            public void onError(Exception e) {
                promise.reject(e);
            }
        });
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map<String, String> levels = new HashMap<>();
        for (SecurityLevel level : SecurityLevel.values()) {
            levels.put(level.toString(), level.toString());
        }

        Map<String, Object> constants = new HashMap<>();
        constants.put("SecurityLevel", levels);
        return constants;
    }
}
