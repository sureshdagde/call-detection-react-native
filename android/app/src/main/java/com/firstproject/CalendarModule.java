package com.firstproject;

import android.content.Context;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import android.util.Log;
import java.util.Date;

public class CalendarModule extends ReactContextBaseJavaModule {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;

    public CalendarModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // Ensure that we are on the main thread
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        TelephonyManager telephonyManager = (TelephonyManager) reactContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String number) {
                onCustomCallStateChanged(reactContext, state, number);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public String getName() {
        return "CalendarModule";
    }

    @ReactMethod
    public void debug(String msg) {
        // Handle debugging messages from JavaScript
        // You can implement custom logic or log messages as needed
    }

    private void emitEvent(String eventName, String eventData) {
        Log.d("CalendarModule", "Create event called with name: "
                + " and location: " + eventName);
        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, eventData);
    }

    private void onCustomCallStateChanged(ReactApplicationContext reactContext, int state, String number) {
        Log.d("CalendarModule", "Create event called with name: "
                + " and location: " + state);
        if (lastState == state) {
            return;
        }

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                emitEvent("PhoneCallIncoming", number);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    emitEvent("PhoneCallOutgoing", savedNumber);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    emitEvent("PhoneCallMissed", savedNumber);
                } else if (isIncoming) {
                    emitEvent("PhoneCallIncomingEnded", savedNumber);
                } else {
                    emitEvent("PhoneCallOutgoingEnded", savedNumber);
                }
                break;
        }
        lastState = state;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createCalendarEvent(String name, String location) {
        // Log.d("CalendarModule", "Create event called with name: " + name
        // + " and location: " + location);
        return name;
    }
}
