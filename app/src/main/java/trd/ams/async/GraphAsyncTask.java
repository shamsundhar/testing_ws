package trd.ams.async;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import trd.ams.activity.AssetScheduleGraphActivity;
import trd.ams.activity.GraphActivity;
import trd.ams.activity.LoginActivity;
import trd.ams.common.Constants;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetScheduleGraphDto;
import trd.ams.dto.ElementarySectionsDto;
import trd.ams.dto.MasterDto;
import trd.ams.dto.ResponseAssetScheduleGraphDto;
import trd.ams.dto.ResponseElementarySectionsDto;
import trd.ams.util.Utils;

public class GraphAsyncTask extends AsyncTask {
    private Activity activity;
    private android.content.Context context;
    String facilityId;
    DatabaseHelper dbhelper = null;
    String sql;
    Cursor cursor = null;
    SQLiteDatabase db;
    Globals globals;
    String status = "";
    String url;

    String TAG = "GraphAsyncTask";

    public GraphAsyncTask(Activity activity,android.content.Context context){
        Log.d(TAG, "GraphAsyncTask**");
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

        //facilityId = globals.getFacilityId();

    }

    public void onPreExecute(){
        Log.d( TAG, "onPreExecute***");
        /*ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
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

                result = syncGraphData(true);
                  /*ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
                  progressBar.setVisibility(View.INVISIBLE);*/
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

    private String syncGraphData(Boolean setup) {
        String responses = "";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();


        MasterDto masterDto = new MasterDto();
        masterDto.setFacilityId(facilityId);
        Log.d(TAG, "facility is::" + masterDto.getFacilityId());
        url = globals.getHOST_URL_BY_USER();
        String graphUrl = url + "/warehouse/rest/get-graph-data";
        //  masterDto.setReportProgress(reportProgress);


        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(masterDto));
            Request request = new Request.Builder().url(graphUrl).post(requestBody).build();
           //  Request request = new Request.Builder().url(Constants.GRAPH_URL).post(requestBody).build();
            Log.d(TAG, "..first......." + request);
            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG, "..second......." + response);
            String output = response.body().string();
            Log.d(TAG, "Response Body *** " + output);

            ResponseAssetScheduleGraphDto dto = new ObjectMapper().readValue(output,ResponseAssetScheduleGraphDto.class);

            Log.d(TAG,"check 1***");
            if(dto != null) {
                try {
                    List<AssetScheduleGraphDto> assetScheduleGraphDtos = dto.getAssetScheduleGraphDtos();
                    Log.d(TAG, "check 2***");

                    if(dto.getAssetScheduleGraphDtos() != null){

                    ArrayList<String> descriptionList = new ArrayList<>();
                    for (AssetScheduleGraphDto assetScheduleGraphDto : assetScheduleGraphDtos) {
                        Log.d(TAG, "Description is---" + assetScheduleGraphDto.getDescription());
                        descriptionList.add(assetScheduleGraphDto.getDescription());
                    }
                    Log.d(TAG, "Description List size---" + descriptionList.size());

                    Log.d(TAG, "*******************************3");

                    ArrayList<String> populationList = new ArrayList<>();
                    for (AssetScheduleGraphDto assetScheduleGraphDto : assetScheduleGraphDtos) {
                        Log.d(TAG, "Population is---" + assetScheduleGraphDto.getPopulation());
                        populationList.add(assetScheduleGraphDto.getPopulation());
                    }

                    Log.d(TAG, "Population List size---" + populationList.size());

                    Log.d(TAG, "*******************************4");

                    ArrayList<String> targetList = new ArrayList<>();
                    for (AssetScheduleGraphDto assetScheduleGraphDto : assetScheduleGraphDtos) {
                        Log.d(TAG, "Target is---" + assetScheduleGraphDto.getTarget());
                        targetList.add(assetScheduleGraphDto.getTarget());
                    }
                    Log.d(TAG, "target List size---" + targetList.size());

                    Log.d(TAG, "*******************************5");

                    ArrayList<String> targetTillMonthList = new ArrayList<>();
                    for (AssetScheduleGraphDto assetScheduleGraphDto : assetScheduleGraphDtos) {
                        Log.d(TAG, "Target till month is---" + assetScheduleGraphDto.getTargetTillMonth());
                        targetTillMonthList.add(assetScheduleGraphDto.getTargetTillMonth());
                    }

                    Log.d(TAG, "*******************************6");

                    ArrayList<String> progressList = new ArrayList<>();
                    ArrayList<String> complianceList = new ArrayList<>();
                    ArrayList<String> outstandingList = new ArrayList<>();
                    for (AssetScheduleGraphDto assetScheduleGraphDto : assetScheduleGraphDtos) {
                        Log.d(TAG, "Progress is---" + assetScheduleGraphDto.getProgress());
                        progressList.add(assetScheduleGraphDto.getProgress());

                        Log.d(TAG, "Compliance is---" + assetScheduleGraphDto.getCompliance());
                        complianceList.add(assetScheduleGraphDto.getCompliance());

                        Log.d(TAG, "Outstanding is---" + assetScheduleGraphDto.getOutstanding());
                        outstandingList.add(assetScheduleGraphDto.getOutstanding());
                    }
                    Log.d(TAG, "Compliance List size---" + complianceList.size());

                    globals.setDescriptionList(descriptionList);
                    globals.setPopulationList(populationList);
                    globals.setTargetList(targetList);
                    globals.setTargetTillMonthList(targetTillMonthList);
                    globals.setProgressList(progressList);
                    globals.setComplianceList(complianceList);
                    globals.setOutstandingList(outstandingList);

                    if (this.activity instanceof AssetScheduleGraphActivity) {
                        activity.startActivity(new Intent(activity, GraphActivity.class));
                        activity.finish();
                        Log.d(TAG, "Goes to graph page");
                    }

                }
                    else{
                        Handler handler = new Handler(context.getMainLooper());
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(context, R.string.no_dto, Toast.LENGTH_LONG).show();

                            }
                        });
                        Log.d(TAG,"No data to Show");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            else{
                Handler handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, R.string.no_dto, Toast.LENGTH_LONG).show();

                    }
                });
                Log.d(TAG,"No data for Grpahs");
            }





        } catch (Exception e) {
            e.printStackTrace();
        }

        return responses;
    }
}
