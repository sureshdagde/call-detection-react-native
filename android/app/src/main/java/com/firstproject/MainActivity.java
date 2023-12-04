// MainActivity.java

package com.firstproject;

// import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.net.Uri;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {
  public static final String DEMO_URL = "https://sten.app/WhoIsThat/";

  public static final int MY_PERMISSIONS_REQUEST_READ_CALL_LOG = 0;
  public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
  public static final int MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS = 2;

  CalendarModule calendarModule;

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_READ_CALL_LOG: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (ContextCompat.checkSelfPermission(MainActivity.this,
              Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                Manifest.permission.READ_PHONE_STATE },
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
          }
        } else {
          Toast.makeText(getApplicationContext(), "missing READ_CALL_LOG",
              Toast.LENGTH_LONG).show();
        }
        break;
      }
      case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          if (ContextCompat.checkSelfPermission(MainActivity.this,
              Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                new String[] { Manifest.permission.PROCESS_OUTGOING_CALLS },
                MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS);
          }
        } else {
          Toast.makeText(getApplicationContext(), "missing READ_PHONE_STATE",
              Toast.LENGTH_LONG).show();
        }
        break;
      }
      case MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permission granted!
        } else {
          Toast.makeText(getApplicationContext(), "missing PROCESS_OUTGOING_CALLS",
              Toast.LENGTH_LONG).show();
        }
        break;
      }
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
  // Handle the back button press if needed
  return super.onKeyDown(keyCode, event);
  }

  /**
   * Returns the name of the main component registered from JavaScript. This is
   * used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "firstProject";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util
   * class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and
   * Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled());
  }
}
