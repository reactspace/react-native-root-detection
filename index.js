/* eslint-disable no-underscore-dangle */
const { NativeModules } = require('react-native');
const _get = require('lodash.get');

const { RNRootDetection } = NativeModules;

export function checkRootStatus({ googleApiKey, securityLevel }) {
  if (!RNRootDetection || Object.prototype.toString.call(RNRootDetection.checkRootStatus) !== '[object Function]') {
    return Promise.reject(new Error('RNRootDetection is not linked properly'));
  }

  return RNRootDetection.checkRootStatus({ googleApiKey, securityLevel }).then(isRooted => !!isRooted);
}

export const SecurityLevel = {
  LOW: _get(RNRootDetection, 'SecurityLevel.LOW', 'LOW'),
  HIGH: _get(RNRootDetection, 'SecurityLevel.HIGH', 'HIGH'),
  MEDIUM: _get(RNRootDetection, 'SecurityLevel.MEDIUM', 'MEDIUM'),
};
