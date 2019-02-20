
# react-native-root-detection

Many times we need to check the integrity of phones that your app is installed in order to
protect data integrity.

- [ ] Identify whether an iPhone is jail broken.
- [x] Identify whether an Android device is rooted.
- [x] Add security levels: HIGH, MEDIUM, LOW for consumer and enterprise apps.

For Consumer based apps the MEDIUM or LOW security level is appropriate but for consumer facing apps 
HIGH security level.

## Getting started
`$ yarn add react-native-root-detection`

### Mostly automatic installation

`$ react-native link react-native-root-detection`\
\
after running react-native link do following changes.
```diff
MainApplication.java

-  import com.reactspace.rootDetection.RNRootDetectionPackage;
+  import com.reactspace.rootDetection.react.RNRootDetectionPackage;
```

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-root-detection` and add `RNRootDetection.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRootDetection.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
   * Add `import com.reactspace.rootDetection.react.RNRootDetectionPackage;` to the imports at the top of the file
   * Add `new RNRootDetectionPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-root-detection'
  	project(':react-native-root-detection').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-root-detection/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-root-detection')
  	```

## Usage
\
**How to use from android:**
\
 You'll need to get a API key from the Google developer console to allow you to verify with the 
 Android Device Verification API
```java
public class MainActivity extends ReactActivity {
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      // code...
      
      RootDetectorConfig config = new RootDetectorConfig();
      config.setGoogleApiKey("<-- Google Api Key -->");
      config.setLevel(SecurityLevel.HIGH);
      
      RootDetector.checkDeviceRootStatus(this, config, new Callback() {
        @Override
        public void onResult(boolean isRooted) {
          // code...
        }
        
        @Override
        public void onError(Exception e) {
          // code...
        }
      });
    }
}
```

\
**How to use from javascript:**
```javascript
import { checkRootStatus, SecurityLevel } from 'react-native-root-detection';

const config = {
  googleApiKey: '<-- Google Api Key -->',
  securityLevel: SecurityLevel.HIGH
};

checkRootStatus(config).then((isRooted) => {
  // ...
}).catch(() => {
  // ...
})

```

## How to obtain Google Api key.
In order to call the methods within the SafetyNet Attestation API, you must pass in an API key. 
To create this key, complete the following steps:

1. Go to the [Library](https://console.developers.google.com/apis/library) page in the Google APIs Console.
2. Search for the **Android Device Verification API**. When you've found the API, click on it. 
    The Android Device Verification API dashboard screen appears.
3. If the API isn't already enabled, click **Enable**.
4. If the Create credentials button appears, click on it to generate an API key. Otherwise, click the **All 
    API credentials** drop-down list and select the API key that is associated with the project for which 
    the Android Device Verification API is enabled.
5. In the sidebar on the left, click **Credentials**. Copy the **API key** that appears.


\
\
**Contact**\
In case of any help feel free to mail at `pradeet.swamy@sprinklr.com`
