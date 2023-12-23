package com.firstproject;

import android.app.Application;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import java.util.Arrays;
import java.util.List;
import com.wscodelabs.callLogs.CallLogPackage;
import com.firstproject.utils.CallStateListener;
import com.firstproject.utils.CallStateEventEmitter;
import com.facebook.react.bridge.ReactContext;
import com.swmansion.gesturehandler.RNGestureHandlerPackage;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new RNGestureHandlerPackage(),
          new CallLogPackage() // Add other packages as needed
      // new CallStateEventEmitter() // Add your CallStateEventEmitter package
      // new CallStateListener(this, new CallStateEventEmitter(reactContext))
      // new CallStateListener(this, new CallStateEventEmitter(reactContext))
      // callStateListener = new CallStateListener(this, new CallStateEventEmitter())

      );
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }

    // @Override
    protected boolean isNewArchEnabled() {
      return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    }

    // @Override
    protected Boolean isHermesEnabled() {
      return BuildConfig.IS_HERMES_ENABLED;
    }
  };

  private CallStateListener callStateListener;

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    // Get reactContext
    ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
    ReactApplicationContext reactContext = (ReactApplicationContext) reactInstanceManager.getCurrentReactContext();

    if (reactContext != null) {
      callStateListener = new CallStateListener(this, new CallStateEventEmitter(reactContext));
      callStateListener.registerForCallStateUpdates();
    } else {
      reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
        @Override
        public void onReactContextInitialized(ReactContext reactContext) {
          // callStateListener = new CallStateListener(this, new
          // CallStateEventEmitter((ReactApplicationContext) reactContext));
          callStateListener = new CallStateListener(MainApplication.this,
              new CallStateEventEmitter((ReactApplicationContext) reactContext));
          callStateListener.registerForCallStateUpdates();
        }
      });
    }

    SoLoader.init(this, /* native exopackage */ false);

    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you need to do something when new architecture is enabled
    }

    ReactNativeFlipper.initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    callStateListener.unregisterForCallStateUpdates();
  }
}
