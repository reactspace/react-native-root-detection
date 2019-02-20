package com.reactspace.rootDetection.detectors;

import android.content.Context;

import java.lang.ref.WeakReference;

public abstract class BaseRootDetection {
    private Context mContext;
    private WeakReference<Callback> mCallbackWeakReference;

    BaseRootDetection(Context mContext, Callback callback) {
        this.mContext = mContext;
        this.mCallbackWeakReference = new WeakReference<>(callback);
    }

    BaseRootDetection() {
    }

    public abstract void checkDeviceRootStatus();

    public void checkDeviceRootStatus(Context context, Callback callback) {
        this.mContext = context;
        this.mCallbackWeakReference = new WeakReference<>(callback);
        checkDeviceRootStatus();
    }

    public abstract void cancel();

    protected Callback getCallback() {
        if (mCallbackWeakReference == null) {
            throw new IllegalStateException("RootDetection: Callback is null");
        }

        return mCallbackWeakReference.get();
    }

    protected Context getContext() {
        if (mContext == null) {
            throw new IllegalStateException("RootDetection: Context is null");
        }

        return mContext;
    }
}
