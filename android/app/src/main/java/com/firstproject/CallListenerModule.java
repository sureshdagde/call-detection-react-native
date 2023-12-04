package com.firstproject;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;

public class CallListenerModule extends ReactContextBaseJavaModule {
    CallListenerModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "CallListenerModule";
    }

    // @ReactMethod
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createCalendarEvent2(String name, String location) {
        // Log.d("CallListenerModule", "Create event called with name: " + name
        // + " and location: " + location);
        return name;
    }
}