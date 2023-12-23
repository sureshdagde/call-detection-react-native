package com.firstproject.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class CallStateEventEmitter extends ReactContextBaseJavaModule {

    public CallStateEventEmitter(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "CallStateEventEmitter";
    }

    public void emitCallStateEvent(String state, String number) {
        WritableMap params = Arguments.createMap();
        params.putString("state", state);
        params.putString("number", number);

        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("CallStateUpdate", params);
    }
    public void emitEventFromJS(String eventName, WritableMap params) {
        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
