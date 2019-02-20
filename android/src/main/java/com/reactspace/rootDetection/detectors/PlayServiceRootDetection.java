package com.reactspace.rootDetection.detectors;


import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.reactspace.rootDetection.BuildConfig;
import com.reactspace.rootDetection.RootDetectorConfig;
import com.reactspace.rootDetection.constants.SecurityLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Random;

import okhttp3.internal.tls.OkHostnameVerifier;


public class PlayServiceRootDetection extends BaseRootDetection {

    private static final String LOG_TAG = "PlayServiceRootDetector";

    private static final Random RANDOM = new SecureRandom();

    private RootDetectorConfig mConfig;
    private RootDetectionAsyncTask mRootDetectionAsyncTask;

    public PlayServiceRootDetection(RootDetectorConfig config) {
        super();
        this.mConfig = config;
    }

    @Override
    public void checkDeviceRootStatus() {
        mRootDetectionAsyncTask = new RootDetectionAsyncTask(getContext(), mConfig);
        mRootDetectionAsyncTask.setCallback(getCallback());
        mRootDetectionAsyncTask.execute();
    }

    @Override
    public void cancel() {
        if (mRootDetectionAsyncTask != null) {
            mRootDetectionAsyncTask.cancel(true);
        }
    }

    private class RootDetectionAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private final RootDetectorConfig mConfig;
        private WeakReference<Context> mContextWeakReference;
        private Callback mCallback;

        private byte[] getRequestNonce(String data) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[24];
            RANDOM.nextBytes(bytes);
            try {
                byteStream.write(bytes);
                byteStream.write(data.getBytes());
            } catch (IOException e) {
                return null;
            }

            return byteStream.toByteArray();
        }


        RootDetectionAsyncTask(Context context, RootDetectorConfig mConfig) {
            mContextWeakReference = new WeakReference<>(context);
            this.mConfig = mConfig;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            checkRootWithPlayService();
            return null;
        }

        private void checkRootWithPlayService() {
            String nonceData = "Safety Net Sample: " + System.currentTimeMillis();

            byte[] nonce = getRequestNonce(nonceData);
            if (mContextWeakReference == null || mContextWeakReference.get() == null) {
                mCallback.onResult(true);
                return;
            }

            SafetyNet.getClient(mContextWeakReference.get()).attest(nonce, mConfig.getGoogleApiKey())
                    .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
                        @Override
                        public void onSuccess(SafetyNetApi.AttestationResponse attestationResponse) {
                            String jwsToken = attestationResponse.getJwsResult();

                            AttestationStatement stmt = parseAndVerify(jwsToken);
                            if (stmt == null) {
                                mCallback.onResult(true);
                                return;
                            }

                            boolean isRooted = !(PlayServiceRootDetection.this.mConfig.getLevel() == SecurityLevel.HIGH ? stmt.isCtsProfileMatch() : stmt.hasBasicIntegrity());
                            mCallback.onResult(isRooted);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mCallback.onError(e);
                        }
                    });
        }

        private AttestationStatement parseAndVerify(String signedAttestationStatement) {

            // Parse JSON Web Signature format. (Check whether the signedAttestationStatement is in Form of AttestationStatement class)
            JsonWebSignature jws;
            try {
                jws = JsonWebSignature.parser(JacksonFactory.getDefaultInstance()).setPayloadClass(AttestationStatement.class).parse(signedAttestationStatement);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Failure: " + signedAttestationStatement + " is not valid JWS format.");
                return null;
            }


            // Verify the signature of the JWS and retrieve the signature certificate.
            X509Certificate cert;
            try {
                cert = jws.verifySignature();
                if (cert == null) {
                    Log.e(LOG_TAG, "Failure: Signature verification failed.");
                    return null;
                }
            } catch (GeneralSecurityException e) {
                Log.e(LOG_TAG, "Failure: Error during cryptographic verification of the JWS signature.");
                return null;
            }

            // Verify the hostname of the certificate.
            if (!verifyHostname("attest.android.com", cert)) {
                Log.e(LOG_TAG, "Failure: Certificate isn't issued for the hostname attest.android.com.");
                return null;
            }


            // Extract and use the payload data.
            AttestationStatement stmt = (AttestationStatement) jws.getPayload();
            return stmt;
        }

        private boolean verifyHostname(String hostname, X509Certificate leafCert) {
            try {
                return OkHostnameVerifier.INSTANCE.verify(hostname, leafCert);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }

            return false;
        }


        void setCallback(Callback callback) {
            mCallback = callback;
        }
    }
}
