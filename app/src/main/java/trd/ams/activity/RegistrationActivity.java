package trd.ams.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import trd.ams.common.Globals;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import trd.ams.R;
import trd.ams.async.RegistrationAsyncTask;
import trd.ams.common.Constants;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.DeviceAuthDto;
import trd.ams.util.Utils;

import static java.util.logging.Logger.global;

public class RegistrationActivity extends AppCompatActivity {
    private RegistrationAsyncTask registrationAsync;
    String TAG = "RegistrationActivity";

    String IMEI, IMEI2;
    Spinner spImei;
    String operation,gri;
    TextInputEditText url_entry;
    //String ssecurity_id;
    Button register;
    private TelephonyManager telephonyManager;
    private Call.Factory okHttpClient;
    DatabaseHelper dbhelper;
    String url;
    Globals globals;
    String masterAuthImeiUrl,masterUrl,masterAuthUrl,progressTableUrl, elementarySectionsURL, assetIdURL;
    String datesUrl,asHistoryUrl,asRecordUrl,ovedueUrl;
    boolean auth;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        globals = (Globals)getApplication();

        spImei = (Spinner) findViewById(R.id.spIMEI);
       // security_id = (EditText) findViewById(R.id.securityid_id);
        url_entry =  findViewById(R.id.et_url);
        register = (Button) findViewById(R.id.btnSubmit);


        url = url_entry.getUrls().toString();
        Log.d(TAG,"url entered is---" +url);


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegistrationActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                ActivityCompat.requestPermissions(RegistrationActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            }

        } else {
            // do nothing

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        IMEI = telephonyManager.getDeviceId(0).toString();
        IMEI2 = telephonyManager.getDeviceId(1).toString();


//        firstd.setText(IMEI);
//        secondd.setText(IMEI2);


        String IMEIAll[] = {IMEI, IMEI2};

//Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, IMEIAll);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spImei.setAdapter(spinnerArrayAdapter);
//  select values of spinner
        spImei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    spImei.setSelection(parent);
                operation = (String) parent.getItemAtPosition(position);

                //operation1 = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*if (!Utils.isOnline()) {
            String status ="NO_CONNECTIVITY";
            Log.d(TAG,"net--"+status);
            register.setEnabled(false);
            register.setBackgroundColor(Color.parseColor("#FFDDDEDA"));
        }
        else{
            register.setEnabled(true);
            register.setBackgroundColor(Color.parseColor("#738b28"));
            Log.d(TAG,"yes");
        }*/

        register.setOnClickListener(new View.OnClickListener() {


            public String IMEI1;
            public SharedPreferences getSharedPref,getSharedPref2;

            @Override
            public void onClick(View v) {

                if (url_entry.equals("")) {
                    url_entry.setError("Enter url");
                }
                else{

                    try {
                         url_entry.clearFocus();
                        register.setBackgroundColor(Color.parseColor("#FFDFE1DA"));

                        // ssecurity_id = security_id.getText().toString();
                        // url = url_entry.getUrls().toString();
                        url = url_entry.getText().toString();
                        Log.d(TAG, "url entered is---" + url);

                        globals.setHOST_URL_BY_USER(url);
                        masterAuthImeiUrl = url + "/warehouse/rest/device/auth";
                        masterUrl = url + "/warehouse/rest/get-data";

                        globals.setMASTER_AUTHIMEI_URL_USER(masterAuthImeiUrl);
                        globals.setMASTER_URL_USER(masterUrl);


                        // CALL GetText method to make post method call
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                String postAuthUrl = globals.getMASTER_AUTHIMEI_URL_USER();
                // String postAuthUrl = Constants.MASTER_AUTHIMEI_URL;

                SharedPreferences details = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor2 = details.edit();

                editor2.putString("IMEI1", IMEI);
                editor2.putString("IMEI2", IMEI2);
                editor2.putString("operation", operation);
                editor2.putString("url", url);
                //   editor2.putString("security_id", ssecurity_id);
                editor2.apply();


                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                new Handler().post(new Runnable() {
                    public void run() {
                        Log.d(TAG, "getApplicationContext()*****" + getApplicationContext());
                        registrationAsync = new RegistrationAsyncTask(RegistrationActivity.this, getApplicationContext());
                        registrationAsync.execute();
                        changeColor();
                    }
                });

            }

            }

        });

        Log.d(TAG,"registration async task completed");
        changeColor();




    }
    public void changeColor(){ register.setBackgroundColor(Color.parseColor("#738b28")); }

}
