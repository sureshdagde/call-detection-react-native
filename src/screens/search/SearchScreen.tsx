import React, { useMemo,useEffect,useState } from "react";
// import { View } from "react-native";
import { useTheme } from "@react-navigation/native";
import { Button} from 'react-native';
// import CalendarModule from "./ToastExample";
import createStyles from "./SearchScreen.style";
// import Text from "../../shared/components/text-wrapper/TextWrapper";
// import React, { useEffect } from 'react';
import { View, Text } from 'react-native';
import { NativeEventEmitter, NativeModules } from 'react-native';

const { CalendarModule } = NativeModules;
const calendarEventEmitter = new NativeEventEmitter(CalendarModule);
interface SearchScreenProps {}
const SearchScreen: React.FC<SearchScreenProps> = () => {
  const theme = useTheme();
  const { colors } = theme;
  const styles = useMemo(() => createStyles(theme), [theme]);
  const [latestCall, setLatestCall] = useState<String>();
  // const {CalendarModule} = NativeModules;
  useEffect(() => {
    console.log('Calling createCalendarEvent...');

    const subscription = calendarEventEmitter.addListener('PhoneCallIncoming', (number) => {
      // Handle incoming call event
      console.log(`Incoming call from ${number}`);
    });

    // Add subscriptions for other events (PhoneCallOutgoing, PhoneCallMissed, etc.)

    return () => {
      subscription.remove();
    };
  }, []);
  // const onPress = async () => {
  //   try {
  //     console.log('Calling createCalendarEvent...');
  //     const data = await CalendarModule.createCalendarEvent('testName', 'testLocation');
  //     console.log('createCalendarEvent response:', data);
  //     setLatestCall(data);
  //   } catch (error) {
  //     console.error('Error calling createCalendarEvent:', error);
  //   }
  // };

 
  // return (
  //   <View style={styles.container}>
  //     <Text h1 color={colors.text}>
  //       {latestCall}
  //     </Text>
  //     <Button
  //       title="Click mes!"
  //       color="#841584"
  //       onPress={onPress}
  //     />
  //   </View>
   
  // );

  return (
    <View>
      {/* Your React Native UI components */}
      <Text>Phone Call Component</Text>
      {/* WebView */}
      {/* <WebView source={{ uri: 'https://sten.app/WhoIsThat/' }} /> */}
    </View>
  );
};

export default SearchScreen;
