package trd.ams.async;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;

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
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetMasterDataDto;
import trd.ams.dto.AssetsScheduleHistoryDto;
import trd.ams.dto.MasterDto;
import trd.ams.dto.ResponseAssetsScheduleHistoryDto;
import trd.ams.util.Utils;

public class DateAsyncTask extends AsyncTask {
    private Activity activity;
    private android.content.Context context;
    String TAG = "DateAsyncTask";
    Globals globals;
    String status = "";
    String assetType;
    String facilityId;
    String scheduleType;
    String assetId;
    DatabaseHelper dbhelper = null;
    String sql;
    Cursor cursor = null;
    SQLiteDatabase db;
    String url;

    public DateAsyncTask(Activity activity, Context context) {
        Log.d(TAG,"Date AsyncTask**");
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
      /*  ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
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

                result = syncDate(true);

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


    private String syncDate(Boolean setUp) {
        String responses = "";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        assetType = globals.getAssetType();
        assetId = globals.getAssetId();
        scheduleType = globals.getScheduleType();

      //  Log.d(TAG, "AT - " + assetType + "\n" + "AI - " + assetId + "\n" + "FI - " + facilityId + "\n" + "ST - " + scheduleType);

        MasterDto masterDto = new MasterDto();
        masterDto.setFacilityId(facilityId);
        masterDto.setAssetType(assetType);
        masterDto.setScheduleType(scheduleType);
        masterDto.setAssetId(assetId);

        url = globals.getHOST_URL_BY_USER();
        String datesUrl = url + "/warehouse/rest/get-dates-data";

        /*masterDto.setAssetType("ATD");
        masterDto.setScheduleType("COMM");
        masterDto.setAssetId("189/34");*/

        try {
            if(assetType != "0" && assetId != null) {

                Log.d(TAG, "AT - " + assetType + "\n" + "AI - " + assetId + "\n" + "FI - " + facilityId + "\n" + "ST - " + scheduleType);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(masterDto));
                Request request = new Request.Builder().url(datesUrl).post(requestBody).build();
                //  Request request = new Request.Builder().url(trd.ams.common.Constants.DATES_URL).post(requestBody).build();
                Log.d(TAG, "..first......." + request);
                Response response = okHttpClient.newCall(request).execute();
                Log.d(TAG, "..second......." + response);
                String output = response.body().string();
                Log.d(TAG, "Response Body *** " + output);

                ResponseAssetsScheduleHistoryDto dto = new ObjectMapper().readValue(output, ResponseAssetsScheduleHistoryDto.class);
                Log.d(TAG, "check A***");
                if (dto != null) {
                    try {
                        List<AssetsScheduleHistoryDto> assetsScheduleHistoryDtos = dto.getAssetsScheduleHistoryDtos();
                        Log.d(TAG, "check B***");

                        ArrayList<String> dateList = new ArrayList<>();

                        for (AssetsScheduleHistoryDto assetsScheduleHistoryDto : assetsScheduleHistoryDtos) {
                            Log.d(TAG, "date is---" + assetsScheduleHistoryDto.getScheduleDate());
                            dateList.add(assetsScheduleHistoryDto.getScheduleDate());
                        }

                        Log.d(TAG, "date List size---" + dateList.size());
                        globals.setDateList(dateList);
                        Log.d(TAG, "global AI size--" + globals.getDateList().size());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responses;
    }

}



