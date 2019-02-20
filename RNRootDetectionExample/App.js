/* eslint-disable no-nested-ternary */
import React from 'react';
import { checkRootStatus, SecurityLevel } from 'react-native-root-detection';
import { StyleSheet, Text, TouchableNativeFeedback, View } from 'react-native';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    color: '#333333',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
    fontSize: 16,
  },
  buttonContainer: {
    height: 36,
    margin: 4,
    paddingHorizontal: 20,
    alignItems: 'center',
    justifyContent: 'center'
  }
});

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isRooted: false,
      rootCheckStatus: ''
    };
  }

  render() {
    const { rootCheckStatus, isRooted } = this.state;
    return (
      <View style={[styles.container, { backgroundColor: rootCheckStatus === 'Success' ? (isRooted ? '#FF0000AA' : '#00FF00AA') : 'white' }]}>
        <Text style={styles.welcome}>React Native Root Detection!</Text>
        {this.state.rootCheckStatus === 'Loading' && <Text style={styles.instructions}>{'Loading...'}</Text>}
        {this.state.rootCheckStatus === 'Success' && <Text style={styles.instructions}>{this.state.isRooted ? 'This device is rooted' : 'This device has passed all root detection checks'}</Text>}
        <TouchableNativeFeedback onPress={this.onPress}>
          <View style={styles.buttonContainer}>
            <Text>{'Check for root / jailbreak'}</Text>
          </View>
        </TouchableNativeFeedback>
      </View>
    );
  }

  onPress = () => {
    const config = {
      googleApiKey: 'AIzaSyBebhy_QAoh4zfwpMniqwU_9szHLEuPoxk',
      securityLevel: SecurityLevel.HIGH
    };

    this.setState({ rootCheckStatus: 'Loading' });
    checkRootStatus(config).then((isRooted) => {
      this.setState({
        isRooted,
        rootCheckStatus: 'Success'
      });
    }).catch(() => {
      this.setState({
        isRooted: false,
        rootCheckStatus: 'Error'
      });
    });
  }
}
