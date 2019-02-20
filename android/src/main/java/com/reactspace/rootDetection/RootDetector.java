package com.reactspace.rootDetection;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.reactspace.rootDetection.detectors.Callback;
import com.reactspace.rootDetection.detectors.LocalRootDetection;
import com.reactspace.rootDetection.detectors.PlayServiceRootDetection;

public class RootDetector {
    public static void checkDeviceRootStatus(Context context, @NonNull RootDetectorConfig config, final Callback callback) {
        if (isPlayServicesAvailable(context)) {
            checkWithPlayService(context, config, callback);
        } else {
            checkWithLocalDetection(context, config, callback);
        }
    }

    private static void checkWithPlayService(final Context context, final RootDetectorConfig config, final Callback callback) {
        PlayServiceRootDetection playServiceRootDetection = new PlayServiceRootDetection(config);
        playServiceRootDetection.checkDeviceRootStatus(context, new Callback() {
            @Override
            public void onResult(boolean isRooted) {
                callback.onResult(isRooted);
            }

            @Override
            public void onError(Exception e) {
                checkWithLocalDetection(context, config, callback);
            }
        });
    }

    private static void checkWithLocalDetection(final Context context, RootDetectorConfig config, final Callback callback) {
        LocalRootDetection localRootDetection = new LocalRootDetection(config);
        localRootDetection.checkDeviceRootStatus(context, callback);
    }

    private static boolean isPlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog((Activity) context, resultCode, 2404).show();
            }
            return false;
        }

        return true;
    }
}
