package trd.ams.async;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import trd.ams.R;
import trd.ams.common.Constants;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.ElementarySectionsDto;
import trd.ams.dto.MasterDto;
import trd.ams.dto.ResponseElementarySectionsDto;
import trd.ams.util.Utils;

public class ESAsyncTask extends AsyncTask {

    private Activity activity;
    private android.content.Context context;
    String TAG = "EsAsyncTask";
    Globals globals;
    String facilityId;
    DatabaseHelper dbhelper = null;
    String sql;
    Cursor cursor = null;
    SQLiteDatabase db;
    String status = "";
    String url;

    public  ESAsyncTask(Activity activity, Context context){
        Log.d(TAG,"ES AsyncTask**");
        this.activity = activity;
        this.context = context;
        globals=(Globals)activity.getApplication();

        dbhelper = DatabaseHelper.getInstance(context);
        net.sqlcipher.database.SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");

        String facility_name = globals.getFacilityName();
        try {
            if (db != null) {
                sql = "select facility_id from facility where facility_name = ?";
                cursor = db.rawQuery(sql, new String[]{facility_name});
                Log.d("Facility ****", "count is***" + cursor.getCount());
                while (cursor.moveToNext()) {
                    facilityId = cursor.getString(0);
                }
                Log.d(TAG, "fac::" + facilityId);
                cursor.close();
            } else {
                Log.d(TAG, "no facility");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onPreExecute(){
        Log.d( TAG, "onPreExecute***");
       /* ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
        progressBar.setVisibility(View.VISIBLE);*/

    }

    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "doInBackGround**");
        String result = "";
        try {
            if (!Utils.isOnline())
            {
                status = "NO_CONNECTIVITY";
                Log.v(TAG, status);
                Handler handler =  new Handler(context.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText(context, R.string.internet,Toast.LENGTH_LONG).show();

                    }
                });

            } else {

                result = syncES(true);

                if (result == "recvfrom failed: ECONNRESET (Connection reset by peer)") {
                    Handler handler = new Handler(context.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, R.string.server, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        }catch (Exception e) {

            Log.e(TAG, "doInBackground: " + e.toString());
        }
        return result;
    }

    private String syncES(Boolean setUp){
        String responses="";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();


        MasterDto masterDto = new MasterDto();
        masterDto.setFacilityId(facilityId);
       // Log.d(TAG,"facility is::"+masterDto.getFacilityId());

        url = globals.getHOST_URL_BY_USER();
        String elementarySectionsURL = url + "/warehouse/rest/get-elementarySections-data";

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(masterDto));
            Request request = new Request.Builder().url(elementarySectionsURL).post(requestBody).build();
         // Request request = new Request.Builder().url(Constants.ELEMENTARY_SECTIONS_URL ).post(requestBody).build();
            Log.d(TAG, "..first......." + request);
            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG, "..second......." + response);

            String output = response.body().string();
            Log.d(TAG, "Response Body *** " +output);

           // MasterDto dto = new ObjectMapper().readValue(output,MasterDto.class);
            ResponseElementarySectionsDto dto = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false).readValue(output,ResponseElementarySectionsDto.class);

            Log.d(TAG,"check 1***");

              if(dto != null) {

                  try {

                      List<ElementarySectionsDto> elementarySectionsDtos = dto.getElementarySectionsDtos();
                      Log.d(TAG, "check 2***");

                      ArrayList<String> esList = new ArrayList<>();

                     // esList.add("Select ElementarySections");

                      for (ElementarySectionsDto elementarySectionsDto : elementarySectionsDtos) {

                          Log.d(TAG, "Elementary Section Code is---" + elementarySectionsDto.getElementarySectionCode());
                          esList.add(elementarySectionsDto.getElementarySectionCode());

                      }
                      Log.d(TAG,"ES List size---" + esList.size());

                  globals.setElementaryList(esList);

                  Log.d(TAG, "global ES size--" +globals.getElementaryList().size());


                  } catch (Exception e){
                      e.printStackTrace();
                  }

              } else{
                  Log.d(TAG,"No data in ElementarySections");
              }


        } catch (Exception e){
            e.printStackTrace();
        }

        return responses;
    }


}
