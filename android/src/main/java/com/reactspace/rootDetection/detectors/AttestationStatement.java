package com.reactspace.rootDetection.detectors;

import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.util.Key;

/**
 * Class for parsing the JSON data.
 */
public class AttestationStatement extends JsonWebSignature.Payload {

    @Key
    private String nonce;

    @Key
    private long timestampMs;

    @Key
    private String apkPackageName;

    @Key
    private String apkDigestSha256;

    @Key
    private boolean ctsProfileMatch;

    @Key
    private boolean basicIntegrity;

    public String getNonce() {
        return nonce;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    public String getApkDigestSha256() {
        return apkDigestSha256;
    }

    public boolean isCtsProfileMatch() {
        return ctsProfileMatch;
    }

    public boolean hasBasicIntegrity() {
        return basicIntegrity;
    }
}