package com.rootdetection;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;

import com.facebook.react.ReactActivity;
import com.reactspace.rootDetection.RootDetector;
import com.reactspace.rootDetection.RootDetectorConfig;
import com.reactspace.rootDetection.constants.SecurityLevel;
import com.reactspace.rootDetection.detectors.Callback;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "RNRootDetectionExample";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RootDetectorConfig config = new RootDetectorConfig();
        config.setGoogleApiKey("AIzaSyBebhy_QAoh4zfwpMniqwU_9szHLEuPoxk");
        config.setLevel(SecurityLevel.HIGH);

        RootDetector.checkDeviceRootStatus(this, config, new Callback() {
            @Override
            public void onResult(boolean isRooted) {
                if (isRooted) {
                    onRootDetected();
                    return;
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("App is good")
                        .setMessage("good to go")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void onRootDetected() {
        new AlertDialog.Builder(this)
                .setTitle("App can't be used on this device")
                .setMessage("We can not run app on a rooted device. Please unroot your device to run the app")
                .setPositiveButton(Html.fromHtml("<font color='#ff3366'>OK</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
