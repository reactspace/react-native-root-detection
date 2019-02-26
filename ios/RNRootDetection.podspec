
Pod::Spec.new do |s|
  s.name         = "RNRootDetection"
  s.version      = "1.0.0"
  s.summary      = "RNRootDetection"
  s.description  = "Detect rooted device"
  s.homepage     = "https://github.com/reactspace/react-native-root-detection"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "gaurav.mnit07@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/reactspace/react-native-root-detection.git", :tag => "master" }
  s.source_files  = "ios/RNRootDetection/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end
