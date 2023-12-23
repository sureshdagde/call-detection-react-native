import { View, Text  } from 'react-native';
// SearchScreen.tsx
import React, { useState, useEffect, useMemo } from 'react';
import { NativeModules } from 'react-native';
// import CallStateModule from '../../CallStateModule'; // Adjust path if needed

interface SearchScreenProps {}

const SearchScreen: React.FC<SearchScreenProps> = () => {
  const [callState, setCallState] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');

  useEffect(() => {
    NativeModules.CallStateModule.addListener('CallStateUpdate', (state: any, number: any) => {
      setCallState(state);
      setPhoneNumber(number);
    });
    return () => NativeModules.CallStateModule.removeListener('CallStateUpdate');
  }, []);

  return (
    <View>
      <Text>Call State: {callState}</Text>
      {callState === 'RINGING' && <Text>Number: {phoneNumber}</Text>}
    </View>
  );
};

export default SearchScreen;
