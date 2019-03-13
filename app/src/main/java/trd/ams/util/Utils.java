package trd.ams.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by hanu on 03-11-2017.
 */

public class Utils {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    private static final String TAG="Utils";

    public static String getIMEI(Activity activity) {


       String imei = "";

       if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
               != PackageManager.PERMISSION_GRANTED) {


           ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},
                   MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);


       } else {

           TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

           if (android.os.Build.VERSION.SDK_INT >= 26) {
               imei = telephonyManager.getImei();
           } else {
               imei = telephonyManager.getDeviceId();
           }

           if (imei == null) {

               imei = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

           }
           if (imei == null) {

               imei = "not available";
           }

       }

       return imei;
   }

    public static boolean isOnline() {


        Boolean online = false;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)
                    (new URL("http://clients3.google.com/generate_204")
                            .openConnection());
            urlConnection.setRequestProperty("User-Agent", "Android");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(3000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 204 &&
                    urlConnection.getContentLength() == 0) {
                //   Log.d("Network Checker", "Successfully connected to internet");
                online = true;
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e(TAG, "isOnline() - No internet connection." + e.toString());


        }

        return online;


    }


    public static long getRandomNumber() {

        return ThreadLocalRandom.current().nextLong(100000000000000000L,999999999999999999L);
    }


}
