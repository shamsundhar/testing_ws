
package trd.ams.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import trd.ams.async.DataUpdateAsyncTask;
import trd.ams.R;
import trd.ams.common.Globals;


public class SplashActivity extends AppCompatActivity {

    String TAG = SplashActivity.class.getSimpleName();
    final private int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    private ProgressBar bar = null;
    Globals globals;
    public String dataSyncDTdefault = "";
    public SharedPreferences sharedPref;
   public  String lastSyncDT,url;
   public String progressTableUrl, elementarySectionsURL, assetIdURL,datesUrl,asHistoryUrl,asRecordUrl,ovedueUrl;
    private Context context;

    /*protected void onPreExecute1() {

        onPreExecute1();
        SharedPreferences details = sharedPref1;
        sahredataSyncDT = details.getString("sahredataSyncDT", "");
      //       Log.i(TAG,"testing value**" +sahredataSyncDT);
        //Log.i(TAG,"It is null" +sahredataSyncDT);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        globals = (Globals) getApplication();
        bar = new ProgressBar(this);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        bar.setIndeterminate(true);

        sharedPref = this.getSharedPreferences(this.getString(R.string.data_sync_preferences_file), Context.MODE_PRIVATE);
        // Check the Write permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }


        //if(!sharedPref1.(dataSyncDTdefult).equals(sahredataSyncDT)) {
        SharedPreferences details = sharedPref;
        lastSyncDT = details.getString("dataSyncDT","");
        url = details.getString("url","");
        Log.d(TAG,"url SPLASH from SP is-- "+url);
        globals.setHOST_URL_BY_USER(url);

        if(!url.equals("")){
            Log.d(TAG,"appending url");
            progressTableUrl = url+ "/warehouse/rest/get-report-progress";
            elementarySectionsURL = url + "/warehouse/rest/get-elementarySections-data";
            assetIdURL = url + "/warehouse/rest/get-assetId-data";
            datesUrl = url + "/warehouse/rest/get-dates-data";
            asHistoryUrl = url +  "/warehouse/rest/get-report-assetScheduleTrack";
            asRecordUrl = url +  "/warehouse/rest/get-report-assetScheduleRecord";
            ovedueUrl = url + "/warehouse/rest/get-report-overdue";

            globals.setPROGRESS_TABLE_URL_USER(progressTableUrl);
            globals.setELEMENTARY_SECTIONS_URL_USER(elementarySectionsURL);
            globals.setASSET_ID_URL_USER(assetIdURL);
            globals.setDATES_URL_USER(datesUrl);
            globals.setASSET_SCHEDULE_TRACK_URL_USER(asHistoryUrl);
            globals.setASSET_SCHEDULE_RECORD_URL_USER(asRecordUrl);
            globals.setOVREDUE_URL_USER(ovedueUrl);
        }

       if(lastSyncDT.equals(""))
       {
           Log.d(TAG,"lastsync date is::: 31-01-1990 17:26:15");
       }
       else {
           Log.d(TAG, "lastSyncDT is**" + lastSyncDT);
       }
        Log.d(TAG,"default DT is**" +dataSyncDTdefault);


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("dataSyncDTfromSPLASH", lastSyncDT);
        editor.commit();
        Log.d(TAG, "dataSyncDTlast is:" + lastSyncDT);


         if(lastSyncDT.equals(dataSyncDTdefault))
        {
            Log.d(TAG,"if logic for Registration Page");
          startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));

            finish();
        } else {
                   globals.setLastSyncTime(lastSyncDT);
            Log.d(TAG,"else logic for LoginPage");
             Log.d(TAG,"url SPLASH is-- "+url);
             globals.setHOST_URL_BY_USER(url);
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();

        }

    /*    boolean permissionFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            if(permissionFineLocation) {
            // {Some Code}
              } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                     }*/
      /*  boolean permissionPhoneState = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            if(permissionPhoneState) {
              } else {
                        ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.READ_PHONE_STATE},300);
                     }

        boolean permissionInternet = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
             if(permissionInternet){
             } else {
                       ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},400);
                    }

        boolean permissionNetworkState = ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
             if(permissionNetworkState){
             } else{
                       ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 500);
                   }

        boolean permissionExternalStorage = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
             if(permissionExternalStorage){
             } else{
                      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 600);
                   }

        boolean permissionWakeLock = ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED;
             if(permissionWakeLock) {
             } else{
                      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, 700);
                   }

        boolean permissionCamera = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
             if(permissionCamera) {
             } else{
                      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 800);
                   }*/

    }
 /*   @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
        }

    }*/

}

//10.185.36.103