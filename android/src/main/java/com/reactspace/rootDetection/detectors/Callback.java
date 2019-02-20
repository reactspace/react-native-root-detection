package com.reactspace.rootDetection.detectors;

public interface Callback {
    void onResult(boolean isRooted);
    void onError(Exception e);
}
