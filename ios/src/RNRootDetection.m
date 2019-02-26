/**
 * Copyright (c) 2019-present, React Space.
 * All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "RNRootDetection.h"
#import "RNRootDetector.h"
#import <React/RCTConvert.h>

NSString *const RNRootDetectionLevelHigh = @"HIGH";
NSString *const RNRootDetectionLevelMedium = @"MEDIUM";
NSString *const RNRootDetectionLevelLow = @"LOW";

@implementation RNRootDetection

RCT_EXPORT_MODULE()

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

- (NSDictionary<NSString *,id> *)constantsToExport {
    return @{
             @"SecurityLevel": @{
                 @"HIGH": RNRootDetectionLevelHigh,
                 @"MEDIUM": RNRootDetectionLevelMedium,
                 @"LOW": RNRootDetectionLevelLow,
             }
            };
}

RCT_EXPORT_METHOD(checkRootStatus:(NSDictionary *)options
                  withResolver:(RCTPromiseResolveBlock)resolve
                  andFailure:(RCTPromiseRejectBlock)reject) {
    NSString *securityLevel = [RCTConvert NSString:options[@"securityLevel"]];
    if (securityLevel.length != 0) {
        BOOL isRooted = [RNRootDetector isDeviceRooted];
        resolve(@(isRooted));
    } else {
        reject(@"Invalid Security level", @"Please pass valid security level", nil);
    }
}

@end
  
