package trd.ams.async;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import trd.ams.R;
import trd.ams.activity.DataSyncActivity;
import trd.ams.activity.LoginActivity;
import trd.ams.activity.RegistrationActivity;
import trd.ams.common.Constants;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.DeviceAuthDto;
import trd.ams.util.AES;
import trd.ams.util.Utils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by opentaps on 8/3/18.
 */

public class RegistrationAsyncTask extends AsyncTask {

    private Activity activity;
    private android.content.Context context;

    Globals globals;
    SharedPreferences sharedPref,getSharedPref2;
    String IMEI1,IMEI2,operation,gri;
    DatabaseHelper dbhelper;



    //DatabaseHelper myDBs=null;

    String TAG = "RegistrationActivity";
    private String status = "";

    public RegistrationAsyncTask(Activity activity,android.content.Context context){
        Log.d(TAG, "registrationAsyncTask**");
        this.activity = activity;
        this.context = context;
        globals = (Globals)activity.getApplication();



        sharedPref = context.getSharedPreferences(
                context.getString(R.string.data_sync_preferences_file), Context.MODE_PRIVATE);
        getSharedPref2 = context.getSharedPreferences("PREFS",0);
        Log.d(TAG,"pref***");





        sharedPref = context.getSharedPreferences(
                context.getString(R.string.data_sync_preferences_file), Context.MODE_PRIVATE);
        Log.d( TAG, "onPreExecute***");

    }
    @Override
    protected void onPreExecute() {
        SharedPreferences details = getSharedPref2;
        IMEI1 = details.getString("IMEI1", "");
        IMEI2 = details.getString("IMEI2", "");
        operation = details.getString("operation", "");
       // ssecurity_id = details.getString("security_id", "");



        Log.d("TEST2","IMEI"+IMEI1+"\n"+IMEI2 );


    }




    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "doInBackGround**");
        String result = "";
        try {

            if (!Utils.isOnline()) {
                status = "NO_CONNECTIVITY";
                Log.v(TAG, status);

                Handler handler =  new Handler(context.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText(context,R.string.internet,Toast.LENGTH_LONG).show();
                    }
                });

            } else {


                result = syncRegistration(true);
                Log.d(TAG,"registration result--"+result);



                    if (result == "Failed") {
                        Handler handler = new Handler(context.getMainLooper());
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "Select valid IMEI number", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    if (result == "recvfrom failed: ECONNRESET (Connection reset by peer)") {
                        Handler handler = new Handler(context.getMainLooper());
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, R.string.server, Toast.LENGTH_LONG).show();
                            }
                        });
                    }  if(result != null || result.equals("")) {
                        Handler handler = new Handler(context.getMainLooper());
                        final String finalResult = result;
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, finalResult, Toast.LENGTH_LONG).show();
                            }
                        });
                    }


            }


        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.toString());
        }
        return result;
    }



    private String syncRegistration(boolean setup){

        String responses="";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        DeviceAuthDto deviceAuthDto = new DeviceAuthDto();
        deviceAuthDto.setAppName("TRD_AMS");
        deviceAuthDto.setImeiNo(operation);
        Log.d(TAG, "CHECKING......." + operation);
        deviceAuthDto.setImeiAuth(false);
       // deviceAuthDto.setRegistrationId(ssecurity_id);
        //String encryptedSecurityCode = AES.encrypt(ssecurity_id, ssecurity_id);
       // deviceAuthDto.setRegistrationId(encryptedSecurityCode);
        //Log.d(TAG, "ENCRYPTED*****"  +encryptedSecurityCode);


        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(deviceAuthDto));
            Request request = new Request.Builder().url(globals.getMASTER_AUTHIMEI_URL_USER()).post(requestBody).build();
          //   Request request = new Request.Builder().url(Constants.MASTER_AUTHIMEI_URL).post(requestBody).build();
            Log.d(TAG, "..first......." + request);
            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG, "..second......." + response);
            String output = response.body().string();
            Log.d(TAG, "Response Body *** " + output);

            DeviceAuthDto deviceAuthDto2 = new ObjectMapper().readValue(output, DeviceAuthDto.class);

            Log.d(TAG, "Resultimei.****......" + deviceAuthDto2.getMessage() + "......" + deviceAuthDto2.getRegistrationId() + "autho****" + deviceAuthDto2.isImeiAuth());

             gri = deviceAuthDto2.getRegistrationId();
            Log.d(TAG, "get registration id is: " +gri);

            if (deviceAuthDto2.isImeiAuth() && this.activity instanceof RegistrationActivity)
            {
                Log.d(TAG, "***VALID***");

                globals.setGlobalIMEI(operation);

                SharedPreferences.Editor editor=getSharedPref2.edit();
                editor.putString("gri", gri);
                editor.putBoolean("auth",deviceAuthDto2.isImeiAuth());
                editor.commit();
                Log.d(TAG, "Stored in SP***" +gri);

                    try {
                            dbhelper = DatabaseHelper.getInstance(context);
                            dbhelper.deleteDatabase();
                            dbhelper.createDataBase();

                        } catch (Exception e){

                            Log.e(TAG, "creating database - "+ e.getMessage());
                        }
                    activity.startActivity(new Intent(activity, DataSyncActivity.class));
                    activity.finish();

            }

            if (deviceAuthDto2.isImeiAuth() == false)
            {
                Log.d(TAG,"Imei is not correct");
                responses = "Failed";
            }
           /* else {
                Toast.makeText(context,"Choose valid IMEI number",Toast.LENGTH_LONG).show();
            }*/



        } catch (Exception e){

            Log.d(TAG, "Web service issue: " + e.getMessage());

            responses = "Server issue - " + e.getMessage();

        }


            return responses;


    }


}
