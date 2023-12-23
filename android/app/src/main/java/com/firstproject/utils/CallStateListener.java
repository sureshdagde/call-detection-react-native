package com.firstproject.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.firstproject.utils.CallStateEventEmitter;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import android.app.Activity;

public class CallStateListener {
    private static final int REQUEST_PHONE_STATE_PERMISSION = 123; // You can use any unique code
    private Context context;
    private TelephonyManager telephonyManager;
    private CallStateCallback callback;
    private CallStateEventEmitter eventEmitter;

    public CallStateListener(Context context, CallStateEventEmitter emitter) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        callback = new CallStateCallback(emitter);
        eventEmitter = emitter;
    }

    public void registerForCallStateUpdates() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("CallStateListener", "Permission granted. Registering for call state updates.");
            telephonyManager.listen(callback, PhoneStateListener.LISTEN_CALL_STATE);
        } else {
            // Handle permission not granted case
            Log.d("CallStateListener", "Permission denied. Registering for call state updates.");
            ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.READ_PHONE_STATE },
                    REQUEST_PHONE_STATE_PERMISSION);
        }
    }

    public void unregisterForCallStateUpdates() {
        telephonyManager.listen(callback, PhoneStateListener.LISTEN_NONE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PHONE_STATE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start listening for call state updates
                telephonyManager.listen(callback, PhoneStateListener.LISTEN_CALL_STATE);
            } else {
                Log.d("CallStateListener222", "Permission denied. Registering for call state updates.");
                // Permission denied, handle the situation accordingly
                // You might want to inform the user or take other actions
            }
        }
    }

    private class CallStateCallback extends PhoneStateListener {
        private static final String TAG = "CallStateListene*****************************************************************r";
        private CallStateEventEmitter eventEmitter;

        public CallStateCallback(CallStateEventEmitter eventEmitter) {
            Log.d(TAG, "onCallStateChan################################################################ged: state=");
            this.eventEmitter = eventEmitter;
        }

        @Override
        public void onCallStateChanged(int state, String number) {
            super.onCallStateChanged(state, number);
            Log.d(TAG, "onCallStateChan################################################################ged: state="
                    + state + ", number=" + number);
            String callStateStr;
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    callStateStr = "RINGING";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    callStateStr = "IN_PROGRESS";
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    callStateStr = "IDLE";
                    break;
                default:
                    callStateStr = "UNKNOWN";
            }
            eventEmitter.emitCallStateEvent(callStateStr, number);
        }
    }
}
