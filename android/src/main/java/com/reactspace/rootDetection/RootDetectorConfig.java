package com.reactspace.rootDetection;

import com.reactspace.rootDetection.constants.SecurityLevel;

public class RootDetectorConfig {
    private String googleApiKey;
    private SecurityLevel level = SecurityLevel.HIGH;

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public void setGoogleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    public SecurityLevel getLevel() {
        return level;
    }

    public void setLevel(SecurityLevel level) {
        this.level = level;
    }
}
