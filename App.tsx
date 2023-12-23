import { View, Text, DeviceEventEmitter } from 'react-native';
import React, { useState, useEffect } from 'react';
import CallStateModule from './CallListenerModule'; // Update the path based on your project structure

const App = () => {
  const [callState, setCallState] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');

  useEffect(() => {
    const callStateUpdateListener = DeviceEventEmitter.addListener(
      'CallStateUpdate',
      (event) => {
        console.log("==============================>",event.state);
        setCallState(event.state);
        setPhoneNumber(event.number);
      }
    );

    const exampleEventListener = DeviceEventEmitter.addListener(
      'ExampleEvent',
      (event) => {
        // Handle the example event
        console.log('Received example event:', event);
      }
    );

    return () => {
      callStateUpdateListener.remove();
      exampleEventListener.remove();
    };
  }, []);

  return (
    <View>
      <Text>Call State==: {callState}</Text>
      {callState === 'RINGING' && <Text>Number: {phoneNumber}</Text>}
    </View>
  );
};

export default App;

