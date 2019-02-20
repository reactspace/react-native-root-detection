
# react-native-root-detection

## Getting started

`$ npm install react-native-root-detection --save`

### Mostly automatic installation

`$ react-native link react-native-root-detection`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-root-detection` and add `RNRootDetection.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRootDetection.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.pradeet.rootDetection.RNRootDetectionPackage;` to the imports at the top of the file
  - Add `new RNRootDetectionPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-root-detection'
  	project(':react-native-root-detection').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-root-detection/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-root-detection')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNRootDetection.sln` in `node_modules/react-native-root-detection/windows/RNRootDetection.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Root.Detection.RNRootDetection;` to the usings at the top of the file
  - Add `new RNRootDetectionPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNRootDetection from 'react-native-root-detection';

// TODO: What to do with the module?
RNRootDetection;
```
  