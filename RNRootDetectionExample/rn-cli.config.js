const path = require('path');
const blacklist = require('metro').createBlacklist; // eslint-disable-line import/no-extraneous-dependencies

const modules = Object.keys(require('../package.json').peerDependencies);

console.log('my rn-cli.config', modules); // eslint-disable-line no-console
module.exports = {
  resolver: {
    extraNodeModules: modules,
    blacklistRE: blacklist(modules.map(function (module) { // eslint-disable-line prefer-arrow-callback, func-names
      return new RegExp(`^${escape(path.resolve(__dirname, '..', 'node_modules', module))}\\/.*$`);
    }))
  },
  projectRoot: __dirname,
  watchFolders: [path.join(__dirname, '..')],
};
