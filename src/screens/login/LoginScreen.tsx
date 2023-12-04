// import React, { useMemo } from "react";
import React, { useState,useMemo } from "react";
// import { View } from "react-native";
import { useTheme } from "@react-navigation/native";
/**
 * ? Local Imports
 */
// import createStyles from ".";
import createStyles from "./LoginScreen.style";

// import Text from "../../shared/components/text-wrapper/TextWrapper";
import { StatusBar } from "expo-status-bar";
// import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  View,
  Image,
  TextInput,
  Button,
  TouchableOpacity,
} from "react-native";
interface ProfileScreenProps {}

const LoginScreen: React.FC<ProfileScreenProps> = () => {
  const theme = useTheme();
  const { colors } = theme;
  const styles = useMemo(() => createStyles(theme), [theme]);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

    return (
      <View style={styles.container}>
        <Image style={styles.profilePicImageStyle} source={require("../../assets/log2.png")} /> 
        <StatusBar style="auto" />
        <View style={styles.inputView}>
          <TextInput
            style={styles.TextInput}
            placeholder="Email."
            placeholderTextColor="#003f5c"
            onChangeText={(email) => setEmail(email)}
          /> 
        </View> 
        <View style={styles.inputView}>
          <TextInput
            style={styles.TextInput}
            placeholder="Password."
            placeholderTextColor="#003f5c"
            secureTextEntry={true}
            onChangeText={(password) => setPassword(password)}
          /> 
        </View> 
        <TouchableOpacity>
          <Text style={styles.forgot_button}>Forgot Password?</Text> 
        </TouchableOpacity> 
        <TouchableOpacity style={styles.loginBtn}>
          <Text >LOGIN</Text> 
        </TouchableOpacity> 
      </View> 
    );
};

export default LoginScreen;
