import React, {useMemo, useState, useEffect} from 'react';
import {View} from 'react-native';
import {useTheme} from '@react-navigation/native';
/**
 * ? Local Imports
 */
import createStyles from './ProfileScreen.style';
import Text from '../../shared/components/text-wrapper/TextWrapper';
// import React, { useEffect, useState } from 'react';
// import { View } from 'react-native';
import CallListener, {CallDetails} from '../../../CallListenerModule'; // Ensure this import line is present

interface ProfileScreenProps {}

const ProfileScreen: React.FC<ProfileScreenProps> = () => {
  const theme = useTheme();
  const {colors} = theme;
  const styles = useMemo(() => createStyles(theme), [theme]);

  const [latestCall, setLatestCall] = useState<CallDetails | null>(null);

  useEffect(() => {
    const startListening = () => {
      CallListener.startListening(callDetails => {
        console.log(
          'Started startListening --------------------------------->',
        );
        setLatestCall(callDetails);
      });
    };

    const stopListening = () => {
      CallListener.stopListening();
    };
    console.log('Started calling --------------------------------->');
    startListening();

    return stopListening;
  }, []);

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      {latestCall ? (
        <View>
          <Text>Latest Call Details:</Text>
          <Text>Phone Number: {latestCall.phoneNumber}</Text>
          <Text>Type: {latestCall.callType}</Text>
          <Text>Date: {new Date(latestCall.timestamp).toLocaleString()}</Text>
        </View>
      ) : (
        <Text>No call details available.</Text>
      )}
    </View>
  );
};

export default ProfileScreen;
