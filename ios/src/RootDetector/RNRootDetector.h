/**
 * Copyright (c) 2019-present, React Space.
 * All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface RNRootDetector : NSObject

+ (float)firmwareVersion;
+ (BOOL)isDeviceRooted;
+ (BOOL)isAppCracked;
+ (BOOL)isAppStoreVersion;

@end

NS_ASSUME_NONNULL_END
