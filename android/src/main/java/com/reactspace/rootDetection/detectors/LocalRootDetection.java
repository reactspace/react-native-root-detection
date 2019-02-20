package com.reactspace.rootDetection.detectors;

import android.os.Build;

import com.scottyab.rootbeer.RootBeer;
import com.reactspace.rootDetection.RootDetectorConfig;

public class LocalRootDetection extends BaseRootDetection {

    private static final String ONEPLUS = "oneplus";
    private static final String MOTO = "moto";
    private static final String XIAOMI = "Xiaomi";

    private RootDetectorConfig mConfig;

    public LocalRootDetection(RootDetectorConfig config) {
        super();
        this.mConfig = config;
    }

    @Override
    public void checkDeviceRootStatus() {
        RootBeer rootBeer = new RootBeer(getContext());

        boolean isRooted;
        if (Build.BRAND.contains(ONEPLUS) || Build.BRAND.contains(MOTO) || Build.BRAND.contains(XIAOMI)) {
            isRooted = rootBeer.isRootedWithoutBusyBoxCheck();
        } else {
            isRooted = rootBeer.isRooted();
        }

        getCallback().onResult(isRooted);
    }

    @Override
    public void cancel() {
    }
}
