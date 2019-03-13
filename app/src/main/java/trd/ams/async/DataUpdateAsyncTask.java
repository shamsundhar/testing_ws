package trd.ams.async;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import trd.ams.common.Globals;
import trd.ams.dto.*;

import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


/*
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
*/
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import trd.ams.R;
import trd.ams.activity.DataSyncActivity;
import trd.ams.activity.LoginActivity;
import trd.ams.common.Constants;
import trd.ams.dao.DataUpdateDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetMasterDataDto;
import trd.ams.dto.AssetScheduleActivityAssocDto;
import trd.ams.dto.AssetScheduleActivityRecordDto;
import trd.ams.dto.AssetScheduleAssocDto;
import trd.ams.dto.AssetsScheduleHistoryDto;
import trd.ams.dto.ElementarySectionsDto;
import trd.ams.dto.FacilityDto;
import trd.ams.dto.MasterDto;
import trd.ams.dto.MeasureOrActivityListDto;
import trd.ams.dto.PowerBlocksDto;
import trd.ams.dto.ProductDto;
import trd.ams.dto.ResponseAssetScheduleActivityRecordDto;
import trd.ams.dto.ResponseAssetsScheduleHistoryDto;
import trd.ams.dto.ScheduleDto;
import trd.ams.util.Utils;

public class DataUpdateAsyncTask extends AsyncTask<Void, Integer, String> {

    Globals globals;

    String TAG = DataUpdateAsyncTask.class.getSimpleName();
    private Activity activity;
    private android.content.Context context;
    private String status = "";
    SharedPreferences sharedPref, getSharedPref, getSharedPref2;
    String IMEI1, IMEI2, operation, sharedataSyncDT, ssecurity_id, getregid, updateDT,spdataSyncDT,dataSyncDTlast,DTfromSplash,url;
    String defaultDT = "31-01-1990 17:26:15.613" ;
    String dsi, di, amos, ashi, imei ;
    private Call.Factory okHttpClient;
    MasterDto masterDto = new MasterDto();
    DatabaseHelper dbhelper = null;


    public DataUpdateAsyncTask(Activity activity, android.content.Context context) {
        this.activity = activity;
        this.context = context;

        globals = (Globals)activity.getApplication();

        sharedPref = context.getSharedPreferences(context.getString(R.string.data_sync_preferences_file), Context.MODE_PRIVATE);
        getSharedPref = context.getSharedPreferences("PREFS", 0);
        Log.d("******", "pref***");
    }


    @Override
    protected void onPreExecute() {
        Log.d("******", "onPreExecute***");
        super.onPreExecute();
        SharedPreferences details = getSharedPref, sharedPref2, getSharedPref2;
        IMEI1 = details.getString("IMEI1", "");
        IMEI2 = details.getString("IMEI2", "");
        operation = details.getString("operation", "");
        url = details.getString("url","");
        ssecurity_id = details.getString("security_id", "");
        try {
            TimeUnit.SECONDS.sleep(5);
            getregid = details.getString("gri", "");
            Log.d(TAG, "id value is:~" + getregid);
        } catch (InterruptedException e) {
            Log.e(TAG, "Timer exception" + e.getMessage());
        }

        Log.d(TAG, "IMEI" + IMEI1 + "\n" + IMEI2 + "\n" + "security_id:" + ssecurity_id);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data_sync_status", "Started");
        editor.putString("data_sync_start_time", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(Calendar.getInstance().getTime()));
        editor.putString("data_sync_finish_time", "");
        editor.putString("data_sync_result", "in progress");
        editor.putString("shareddataSyncDT","31-01-1990 17:26:15.613");
        editor.commit();


        if (this.activity instanceof DataSyncActivity) {
            Log.d(TAG, "In DataSyncActivity");


            TextView tvDataSyncStatus = activity.findViewById(R.id.tvDataSyncStatus);
            tvDataSyncStatus.setText("Data Sync Status : " + sharedPref.getString("data_sync_status", ""));

            TextView tvDataSyncStartTime = activity.findViewById(R.id.tvDataSyncStartTime);
            tvDataSyncStartTime.setText("Start Time : " + sharedPref.getString("data_sync_start_time", ""));

            TextView tvDataSyncFinishTime = activity.findViewById(R.id.tvDataSyncFinishTime);
            tvDataSyncFinishTime.setText("Finish Time : " + sharedPref.getString("data_sync_finish_time", ""));

            TextView tvDataSyncResult = activity.findViewById(R.id.tvDataSyncResult);
            tvDataSyncResult.setText("Result : " + sharedPref.getString("data_sync_result", ""));

            ProgressBar progressBar = activity.findViewById(R.id.progressBarDataSync);

            progressBar.setVisibility(View.VISIBLE);


            Button btnDataSyncNow = activity.findViewById(R.id.btnDataSyncNow);
            btnDataSyncNow.setEnabled(false);
            Log.d(TAG, "comes in data sync.....");

            Toast.makeText(context, "Data Sync started", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected String doInBackground(Void... params) {
        Log.d(TAG, "doInBackground.");

        String result = "";
        try {

            if (!Utils.isOnline()) {
                status = "NO_CONNECTIVITY";
                Log.v(TAG, status);

                Handler handler =  new Handler(context.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText(context, R.string.internet,Toast.LENGTH_LONG).show();
                    }
                });

            } else {


                result = serverCommunication(true);
                if (result == "recvfrom failed: ECONNRESET (Connection reset by peer)") {
                    Handler handler = new Handler(context.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, R.string.server, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }


        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.toString());
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (this.activity instanceof DataSyncActivity) {

            ProgressBar progressBar = activity.findViewById(R.id.progressBarDataSync);
            progressBar.setProgress(values[0]);

        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG, "In Post execution");

        super.onPostExecute(result);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data_sync_status", "Finished");
        editor.putString("data_sync_finish_time", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(Calendar.getInstance().getTime()));
        editor.putString("data_sync_result", result);
        editor.commit();


        if (this.activity instanceof DataSyncActivity) {

            TextView tvDataSyncStatus = activity.findViewById(R.id.tvDataSyncStatus);
            tvDataSyncStatus.setText("Data Sync Status : " + sharedPref.getString("data_sync_status", ""));

            TextView tvDataSyncStartTime = activity.findViewById(R.id.tvDataSyncStartTime);
            tvDataSyncStartTime.setText("Start Time : " + sharedPref.getString("data_sync_start_time", ""));

            TextView tvDataSyncFinishTime = activity.findViewById(R.id.tvDataSyncFinishTime);
            tvDataSyncFinishTime.setText("Finish Time : " + sharedPref.getString("data_sync_finish_time", ""));

            TextView tvDataSyncResult = activity.findViewById(R.id.tvDataSyncResult);
            tvDataSyncResult.setText("Result : " + getSharedPref.getString("operation", ""));



            ProgressBar progressBar = activity.findViewById(R.id.progressBarDataSync);

            progressBar.setVisibility(View.INVISIBLE);

            Button btnDataSyncNow = activity.findViewById(R.id.btnDataSyncNow);
            btnDataSyncNow.setEnabled(true);
            Log.d(TAG, "comes in try.....");
            Toast.makeText(context, "Data Sync finished", Toast.LENGTH_SHORT).show();


        }

       /* if (this.activity instanceof DataSyncActivity) {

            activity.startActivity((new Intent(activity, LoginActivity.class)));
            activity.finish();
            Log.d(TAG,"Data Sync finished");
        }*/


    }

    /**
     * Runs on the UI thread after cancel(boolean) is invoked.
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        activity.finish();
    }

    private String serverCommunication(boolean setup) {

        String result = "";
        try {
            Log.d(TAG, "Data Sync started: " + setup);
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(context);
            SQLiteDatabase db = dbhelper.getDBObject(0);

            result = syncMasterData(setup, db);

            Log.d(TAG, "synching check**" + result);

            if(result == "Success"){


                globals.setLastSyncTime(updateDT);

                if (this.activity instanceof DataSyncActivity)
                {
                    activity.startActivity((new Intent(activity, LoginActivity.class)));
                    activity.finish();
                    Log.d(TAG,"Data Sync finished");
                }
            }else{
                Toast.makeText(context,"Data Synch failed",Toast.LENGTH_LONG).show();
                globals.setLastSyncTime(DTfromSplash);

            }

            db.close();

          /*  //  INSERTING VALUES INTO DATASYNCHISTORY TABLE

            List<DataSyncHistoryDto> dsh = new ArrayList<> () ;

            DataSyncHistoryDto objD = new DataSyncHistoryDto();

              objD.setDeviceId(imei);
              objD.setLastSyncDateTime(globals.getLastSyncTime());
              objD.setSyncStartDateTime(globals.getCurrentSyncTime());
              objD.setAshSentCount(globals.getAshCount());
              objD.setAsarSentCount(globals.getAsarCount());
              objD.setAscSentCount(globals.getAscCount());
              objD.setDsRequestDateTime(globals.getDsRequestTime());
              objD.setDsResponseDateTime(globals.getDsResponseTime());
              objD.setResponseStatus(globals.getResponseStatus());
              objD.setDevSerSeqUpdateDateTime(globals.getAshSeqUpdateTime());
              objD.setFinalStatusUpdate(globals.getFinalResponse());


              Log.d(TAG,"deviceId--"+imei);
              Log.d(TAG,"ASARsentCount--"+globals.getAsarCount());
              Log.d(TAG,"DSResponseTime--"+globals.getDsResponseTime());

                 dsh.add(objD);
                 Log.d(TAG,"added to DataSyncHistory Table");


           ResponseDataSyncHistoryDto responseDataSyncHistoryDto = new ResponseDataSyncHistoryDto();
           responseDataSyncHistoryDto.setDataSyncHistoryDtos(dsh);
           masterDto.setAppToServerCreatedResponseDataSyncHistoryDto(responseDataSyncHistoryDto);

           Log.d(TAG,"DataSyncHistory Table Sent");*/


        } catch (Exception e) {
            Log.e(TAG, "Exception while synchronizing the database: " + e.getMessage());
        }

        return result;
    }



    private String syncMasterData(boolean setup, final SQLiteDatabase db) {

        String result = "Failed";


        try {

            int progressValue = 1;

            Log.d(TAG, "Performing");

        /*String postAuthUrl = globals.getMASTER_AUTH_URL_USER();
             String postAuthUrl = Constants.MASTER_AUTH_URL;
         String postUrl = globals.getMASTER_URL_USER();*/

           String postUrl = url+ "/warehouse/rest/get-data";

           //   String postUrl = Constants.MASTER_URL;


            masterDto.setAppName("TRD_AMS");
            masterDto.setImeiAuth(false);
            masterDto.setImeiNo(operation);
            masterDto.setRegistrationId(getregid);


            //   masterDto.setUserLoginId("10064");
   /*        SharedPreferences details=sharedPref;
            sharedataSyncDT=details.getString("shareddataSyncDT", "");
            Log.d(TAG,"sync dt check**" +sharedataSyncDT);
            masterDto.setPreviousTimestamp(sharedataSyncDT);
            masterDto.setCurrentTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date()));

            //  in order to send last sync date to SplashActivity
            spdataSyncDT = masterDto.getPreviousTimestamp();
            Log.d(TAG,"sync dt check**" +spdataSyncDT);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("dataSyncDT", spdataSyncDT);
            editor.commit();
            Log.d(TAG, "sharedataSyncDT is:" + spdataSyncDT);*/



            try {
                TimeUnit.SECONDS.sleep(5);
                updateDT= sharedPref.getString("data_sync_start_time", "");
                Log.d(TAG,"updated sync time::" +updateDT);

            } catch (InterruptedException e) {
                Log.e(TAG, "Timer exception" + e.getMessage());
            }

            DTfromSplash = sharedPref.getString("dataSyncDTfromSPLASH","");
            Log.d(TAG,"last sync from Splash page is::" +DTfromSplash);

            Log.d(TAG,"Reload time is--" +globals.getReloadTime());

           try {

               if (DTfromSplash.equals("") || globals.getReloadTime() != null) {
                   Handler handler = new Handler(context.getMainLooper());
                   handler.post(new Runnable() {
                       public void run() {
                           masterDto.setPreviousTimestamp(defaultDT);
                           globals.setLastSyncTime(defaultDT);
                       }
                   });
               }
               /*else if (!globals.getReloadTime().equals("")) {
                   Handler handler = new Handler(context.getMainLooper());
                   handler.post(new Runnable() {
                       public void run() {

                           masterDto.setPreviousTimestamp(defaultDT);
                           globals.setLastSyncTime(defaultDT);
                       }
                   });

               }*/
               else if (!DTfromSplash.equals("")) {
                   Handler handler = new Handler(context.getMainLooper());
                   handler.post(new Runnable() {
                       public void run() {
                           masterDto.setPreviousTimestamp(DTfromSplash);
                           globals.setLastSyncTime(DTfromSplash);
                       }
                   });
               }
           }catch (Exception e){
               e.printStackTrace();
           }


           // masterDto.setPreviousTimestamp(updateDT);
          //  masterDto.setPreviousTimestamp("31-01-1990 17:26:15.613");
            String currentTimeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date());
            masterDto.setCurrentTimestamp(currentTimeStamp);
            globals.setCurrentSyncTime(currentTimeStamp);
            dataSyncDTlast = masterDto.getPreviousTimestamp();
            Log.d(TAG,"Previoustime stamp is:**:" +dataSyncDTlast);
           // In order to send updated last sync time stamp to SplashActivity
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("dataSyncDT", updateDT);
            editor.putString("url",url);
            Log.d(TAG,"url DATA ASYNC TASK is-- "+url);
            editor.commit();
            Log.d(TAG, "dataSyncDTlast to Splash is:" + updateDT);



            //******************  inserting through device   ****************



            String sql1 = " ";
            String sql2 = " ";
            String sql3 = " ";

            String args[] = {};
            Cursor c1 = null;
            Cursor c2 = null;
            Cursor c3 = null;

          //  List<String> listH = new ArrayList<>();
            List<AssetsScheduleHistoryDto> ash = new ArrayList<AssetsScheduleHistoryDto>();

            try{
                if (db != null)
                {
                    Log.d(TAG, " inserting ASH records from device****");
                    sql1= " select * from assets_schedule_history where seq_id is null ";

                    c1 = db.rawQuery(sql1,args);

                    Log.d(TAG," executing query");

                   int ashCount = c1.getCount();

                    Log.d(TAG,"count is***" +ashCount);

                    globals.setAshCount(ashCount);

                    if( c1.getCount() != 0) {

                        if (c1.moveToFirst()) {
                            Log.d(TAG, "inn if block ASH "+c1.getCount());
                          while (!c1.isAfterLast()) {

                                Log.d(TAG, "entered while loop");

                                //  for(int i=0; i< c.getCount(); i++)

                                AssetsScheduleHistoryDto objH = new AssetsScheduleHistoryDto();
                                objH.setSeqId(c1.getString(0));
                                objH.setFacilityId(c1.getString(1));
                                objH.setPbOperationSeqId(c1.getString(2));
                                objH.setDeviceId(c1.getString(3));
                                imei=c1.getString(3);
                                objH.setDeviceSeqId(c1.getString(4));
                                objH.setDeviceCreatedStamp(c1.getString(5));
                                objH.setDeviceLastUpdatedStamp(c1.getString(6));
                                objH.setAssetType(c1.getString(7));
                                objH.setAssetId(c1.getString(8));
                                objH.setScheduleCode(c1.getString(9));
                                objH.setScheduleDate(c1.getString(10));
                                objH.setStatus(c1.getString(11));
                                objH.setDetailsOfMaint(c1.getString(12));
                                objH.setDoneBy(c1.getString(13));
                                objH.setInitialOfIncharge(c1.getString(14));
                                objH.setRemarks(c1.getString(15));
                                objH.setCreatedOn(c1.getString(16));
                                objH.setCreatedBy(c1.getString(17));
                                objH.setLastUpdatedStamp(c1.getString(18));
                                objH.setLastUpdatedTxStamp(c1.getString(19));
                                objH.setCreatedStamp(c1.getString(20));
                                objH.setCreatedTxStamp(c1.getString(21));

                                Log.d(TAG, "Testing in ASH records from device");

                                ash.add(objH);
                                c1.moveToNext();
                            }
                        }
                        ResponseAssetsScheduleHistoryDto historyDto = new ResponseAssetsScheduleHistoryDto();
                        historyDto.setAssetsScheduleHistoryDtos(ash);
                        historyDto.setCount(ashCount);
                        masterDto.setAppToServerCreatedResponseAssetsScheduleHistoryDto(historyDto);
                    }

                    else{
                          Log.d(TAG,"no data in AssetScheduleHistory");
                    }



                     c1.close();
                    // db.close();
                }

                else{
                    Log.d(TAG, "No data ** ASH ** inserted through device");
                }

                Log.d(TAG, "inserted data in ASH" +ash.toString());


            } catch (Exception e)
            {
                Log.e(TAG, "device inserting records ASH:" + e.getMessage());
                if (c1!= null) {

                    c1.close();
                }
                if (db != null && db.isOpen()){

                  //  db.close();
                }
            }


           //**********  inserting AssetScheduleActivityRecord   **********

            List<AssetScheduleActivityRecordDto> asar = new ArrayList<AssetScheduleActivityRecordDto>();

            try{
                if (db != null)
                {
                    Log.d(TAG, " inserting ASAR records from device****");
                    sql2= " select * from asset_schedule_activity_record where asset_schedule_history_id is null ";

                    c2 = db.rawQuery(sql2,args);

                    Log.d(TAG," executing query");

                    int asarCount = c2.getCount();

                    Log.d(TAG,"count is***" +c2.getCount());

                    globals.setAsarCount(asarCount);

                    if( c2.getCount() != 0) {

                        if (c2.moveToFirst()) {
                            Log.d(TAG, "inn if block ASAR "+c2.getCount());
                            while (!c2.isAfterLast()) {

                                Log.d(TAG, "entered while loop");

                                //  for(int i=0; i< c.getCount(); i++)

                                AssetScheduleActivityRecordDto objR = new AssetScheduleActivityRecordDto();

                                objR.setAssetMeasureObserSeqId(c2.getString(0));
                                objR.setFacilityId(c2.getString(1));
                                objR.setAssetType(c2.getString(2));
                                objR.setAssetId(c2.getString(3));
                                objR.setScheduleCode(c2.getString(4));
                                objR.setScheduleDate(c2.getString(5));
                                objR.setScheduleActualDate(c2.getString(6));
                                objR.setAssetScheduleHistoryId(c2.getString(7));
                                objR.setStatus(c2.getString(8));
                                objR.setDeviceId(c2.getString(9));
                                objR.setDeviceSeqId(c2.getString(10));
                                objR.setDeviceCreatedStamp(c2.getString(11));
                                objR.setDeviceLastUpdatedStamp(c2.getString(12));

                                objR.setM1(c2.getString(13));
                                objR.setM2(c2.getString(14));
                                objR.setM3(c2.getString(15));
                                objR.setM4(c2.getString(16));
                                objR.setM5(c2.getString(17));
                                objR.setM6(c2.getString(18));
                                objR.setM7(c2.getString(19));
                                objR.setM8(c2.getString(20));
                                objR.setM9(c2.getString(21));
                                objR.setM10(c2.getString(22));
                                objR.setM11(c2.getString(23));
                                objR.setM12(c2.getString(24));
                                objR.setM13(c2.getString(25));
                                objR.setM14(c2.getString(26));
                                objR.setM15(c2.getString(27));
                                objR.setM16(c2.getString(28));
                                objR.setM17(c2.getString(29));
                                objR.setM18(c2.getString(30));
                                objR.setM19(c2.getString(31));
                                objR.setM20(c2.getString(32));
                                objR.setM21(c2.getString(33));
                                objR.setM22(c2.getString(34));
                                objR.setM23(c2.getString(35));
                                objR.setM24(c2.getString(36));
                                objR.setM25(c2.getString(37));
                                objR.setM26(c2.getString(38));
                                objR.setM27(c2.getString(39));
                                objR.setM28(c2.getString(40));
                                objR.setM29(c2.getString(41));
                                objR.setM30(c2.getString(42));
                                objR.setM31(c2.getString(43));
                                objR.setM32(c2.getString(44));
                                objR.setM33(c2.getString(45));
                                objR.setM34(c2.getString(46));
                                objR.setM35(c2.getString(47));
                                objR.setM36(c2.getString(48));
                                objR.setM37(c2.getString(49));
                                objR.setM38(c2.getString(50));
                                objR.setM39(c2.getString(51));
                                objR.setM40(c2.getString(52));
                                objR.setM41(c2.getString(53));
                                objR.setM42(c2.getString(54));
                                objR.setM43(c2.getString(55));
                                objR.setM44(c2.getString(56));
                                objR.setM45(c2.getString(57));
                                objR.setM46(c2.getString(58));
                                objR.setM47(c2.getString(59));
                                objR.setM48(c2.getString(60));
                                objR.setM49(c2.getString(61));
                                objR.setM50(c2.getString(62));

                                objR.setA1(c2.getString(63));
                                objR.setA2(c2.getString(64));
                                objR.setA3(c2.getString(65));
                                objR.setA4(c2.getString(66));
                                objR.setA5(c2.getString(67));
                                objR.setA6(c2.getString(68));
                                objR.setA7(c2.getString(69));
                                objR.setA8(c2.getString(70));
                                objR.setA9(c2.getString(71));
                                objR.setA10(c2.getString(72));
                                objR.setA11(c2.getString(73));
                                objR.setA12(c2.getString(74));
                                objR.setA13(c2.getString(75));
                                objR.setA14(c2.getString(76));
                                objR.setA15(c2.getString(77));
                                objR.setA16(c2.getString(78));
                                objR.setA17(c2.getString(79));
                                objR.setA18(c2.getString(80));
                                objR.setA19(c2.getString(81));
                                objR.setA20(c2.getString(82));
                                objR.setA21(c2.getString(83));
                                objR.setA22(c2.getString(84));
                                objR.setA23(c2.getString(85));
                                objR.setA24(c2.getString(86));
                                objR.setA25(c2.getString(87));
                                objR.setA26(c2.getString(88));
                                objR.setA27(c2.getString(89));
                                objR.setA28(c2.getString(90));
                                objR.setA29(c2.getString(91));
                                objR.setA30(c2.getString(92));
                                objR.setA31(c2.getString(93));
                                objR.setA32(c2.getString(94));
                                objR.setA33(c2.getString(95));
                                objR.setA34(c2.getString(96));
                                objR.setA35(c2.getString(97));
                                objR.setA36(c2.getString(98));
                                objR.setA37(c2.getString(99));
                                objR.setA38(c2.getString(100));
                                objR.setA39(c2.getString(101));
                                objR.setA40(c2.getString(102));
                                objR.setA41(c2.getString(103));
                                objR.setA42(c2.getString(104));
                                objR.setA43(c2.getString(105));
                                objR.setA44(c2.getString(106));
                                objR.setA45(c2.getString(107));
                                objR.setA46(c2.getString(108));
                                objR.setA47(c2.getString(109));
                                objR.setA48(c2.getString(110));
                                objR.setA49(c2.getString(111));
                                objR.setA50(c2.getString(112));

                                objR.setMma1(c2.getString(113));
                                objR.setMma2(c2.getString(114));
                                objR.setMma3(c2.getString(115));
                                objR.setMma4(c2.getString(116));
                                objR.setMma5(c2.getString(117));
                                objR.setMma6(c2.getString(118));
                                objR.setMma7(c2.getString(119));
                                objR.setMma8(c2.getString(120));
                                objR.setMma9(c2.getString(121));
                                objR.setMma10(c2.getString(122));

                                objR.setMake(c2.getString(123));
                                objR.setModel(c2.getString(124));
                                objR.setDetailsOfMaint(c2.getString(125));
                                objR.setDoneBy(c2.getString(126));
                                objR.setInitialOfIncharge(c2.getString(127));
                                objR.setRemarks(c2.getString(128));
                                objR.setCreatedOn(c2.getString(129));
                                objR.setCreatedBy(c2.getString(130));
                                objR.setLastUpdatedStamp(c2.getString(131));
                                objR.setLastUpdatedTxStamp(c2.getString(132));
                                objR.setCreatedStamp(c2.getString(133));
                                objR.setCreatedTxStamp(c2.getString(134));


                                Log.d(TAG, "Testing in ASAR records from device");

                                asar.add(objR);
                                c2.moveToNext();
                            }
                        }
                        ResponseAssetScheduleActivityRecordDto recordDto = new ResponseAssetScheduleActivityRecordDto();
                        recordDto.setAssetScheduleActivityRecordDtos(asar);
                        recordDto.setCount(asarCount);
                        masterDto.setAppToServerCreatedResponseAssetScheduleActivityRecordDto(recordDto);
                    }

                    else{
                        Log.d(TAG,"no data in AssetScheduleActivityRecord");
                    }



                    c2.close();
                    // db.close();
                }

                else{
                    Log.d(TAG, "No ** ASAR ** data inserted through device");
                }

                Log.d(TAG, "inserted data in ASAR" +asar.toString());


            } catch (Exception e)
            {
                Log.e(TAG, "device inserting records ASAR:" + e.getMessage());
                if (c2!= null) {

                    c2.close();
                }
                if (db != null && db.isOpen()){

                  //  db.close();
                }
            }



            //**********  inserting AssetScheduleContent   **********

         /*   List<AssetScheduleContentDto> asc = new ArrayList<AssetScheduleContentDto>();

            try{
                if (db != null){

                    Log.d(TAG, " inserting ASC records from device****");
                    sql3= " select * from asset_schedule_content where seq_id is null ";

                    c3 = db.rawQuery(sql3,args);

                    Log.d(TAG," executing query");

                    int ascCount = c3.getCount();

                    Log.d(TAG,"count is***" +ascCount);

                    globals.setAshCount(ascCount);

                    if(c3.getCount() !=0 ){

                        if(c3.moveToFirst()){
                            Log.d(TAG, "inn if block ASC "+c3.getCount());

                            while (!c3.isAfterLast()) {
                                Log.d(TAG, "entered while loop");

                                AssetScheduleContentDto objC = new AssetScheduleContentDto();

                                objC.setSeqId(c3.getString(0));
                                objC.setContentId(c3.getString(1));
                                objC.setFromDate(c3.getString(2));
                                objC.setThruDate(c3.getString(3));
                                objC.setDeviceId(c3.getString(4));
                                objC.setDeviceSeqId(c3.getString(5));
                                objC.setDeviceAshSeqId(c3.getString(6));
                                byte[] bytes = (c3.getString(7)).getBytes();
                                Log.d(TAG, "string size--" + (c3.getString(7)).length());
                                Log.d(TAG, "byte size--" + bytes.length);
                              //  String str64 = c3.getString(7);
                                //try {
                                 //   byte[] bytes = Base64.decode(c3.getString(7), Base64.DEFAULT);
                                 //   Log.d(TAG, "byte size--" + bytes.length);
                               // }catch (IllegalArgumentException i){
                                 //   i.printStackTrace();
                                  // }
                              // ByteBuffer buffers = ByteBuffer.wrap(bytes);
                               objC.setContent(bytes);
                             //   objC.setContent(buffers);
                                objC.setFileName(c3.getString(8));
                                objC.setUserName(c3.getString(9));

                                Log.d(TAG, "Testing in ASC records from device");

                                asc.add(objC);

                                c3.moveToNext();

                            }
                        }

                        ResponseAssetScheduleContentDto contentDto = new ResponseAssetScheduleContentDto();
                        contentDto.setAssetScheduleContentDtos(asc);
                        Log.d(TAG, "Testing in ASC records from device2");
                        contentDto.setCount(ascCount);
                      //  masterDto.setAppToServerCreatedResponseAssetScheduleContentDto(contentDto);

                    }

                         else{
                                 Log.d(TAG,"no data in AssetScheduleContent");
                             }

                             c3.close();
                }
                else{
                    Log.d(TAG, "No ** ASC ** data inserted through device");
                }

                Log.d(TAG, "inserted data in ASC" +asc.toString());

            } catch (Exception e)
            {
                Log.e(TAG, "device inserting records ASC:" + e.getMessage());
                if (c3!= null) {

                    c3.close();
                }
                if (db != null && db.isOpen()){

                    //  db.close();
                }
            }*/



            RequestBody requestBody = null;
            try {
                Log.d(TAG, "this is request1");

                requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(masterDto));
            } catch (IOException e1) {

                e1.printStackTrace();
            }
//webservice calll...............
            publishProgress(progressValue);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).build();
            Log.d(TAG, "this is request");
            Request request = new Request.Builder().url(postUrl).post(requestBody).build();

            String requestTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date());

            Log.i(TAG, "the request is:::" + request);
            Log.d(TAG,"Requst time is---"+requestTime);
            globals.setDsRequestTime(requestTime);

            progressValue = progressValue + 1;
            publishProgress(progressValue);

            Response response = null;

            if (setup) {

                try {
                    response = okHttpClient.newCall(request).execute();
                    Log.d(TAG, "this is response......" + response);
                    String responseTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date());
                    Log.d(TAG,"Response Time is---"+responseTime);
                    globals.setDsResponseTime(responseTime);


                    progressValue = progressValue + 2;
                    publishProgress(progressValue);

                    String data = response.body().string();
                    Log.d(TAG, "Response Body :- " + data);

                    Log.d(TAG,"check 1***");

                    MasterDto dto = new ObjectMapper().readValue(data,MasterDto.class);

                   // MasterDto dto = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false).readValue(data,MasterDto.class);

                    Log.d(TAG,"flow order check**");
                    ResponseFacilityDto responseFacilityDto = dto.getCreatedResponseFacilityDto();
                    List<FacilityDto> facilityDtos = responseFacilityDto.getFacilityDtos();
                    Log.d(TAG,"flow order check here**");
                   /* for (FacilityDto facilityDto : facilityDtos) {
                        Log.d(TAG, ".if..facility..id.." + facilityDto.getFacilityId());
                    }*/
                    progressValue = progressValue + 2;
                    publishProgress(progressValue);

                    if (dto != null) {
                        Log.d(TAG, "entering this block");

                        if (updateDatabase(dto, db)) {

                            result = "Success";

                        } else {

                            result = "Failed";

                        }


                        Log.d(TAG, "Value of result**" + result);


                    } else

                    {
                        Log.d(TAG, "not coming");
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            } else {

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();

                        Log.d("TAG", "Web service call failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "Response Body - " + response.body().string());
                        String string = response.body().string();

                        MasterDto dto = new ObjectMapper().readValue(string, MasterDto.class);

                        ResponseFacilityDto responseFacilityDto = dto.getCreatedResponseFacilityDto();
                        List<FacilityDto> facilityDtos = responseFacilityDto.getFacilityDtos();
                        for (FacilityDto facilityDto : facilityDtos) {
                            Log.d(TAG, ".else..facility..id.." + facilityDto.getFacilityId());
                        }

                        if (updateDatabase(dto, db)) {
                            // result = "Success";

                        }


                    }
                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
/*
    public static void retrieveValuesFromListMethod(Map serverToAppAssetsScheduleHistoryMap)
    {
        Set keys = serverToAppAssetsScheduleHistoryMap.keySet();
        Iterator itr = keys.iterator();

        String key;
        String value;
        while(itr.hasNext())
        {
            key = (String)itr.next();
            value = (String)serverToAppAssetsScheduleHistoryMap.get(key);
            Log.d("TAG", "key**" +key + "\n" + "value**" +value);
        }

    }*/

    private boolean updateDatabase(MasterDto dto, SQLiteDatabase db) {

        Log.d(TAG, "flow checking**");
        boolean result = false;

        int progressValue = 7;




        if (dto != null) {

            Log.d(TAG,"in update database method");

            try {
                DataUpdateDAO dataUpdateDAO = DataUpdateDAO.getInstance();


                 // retrieveValuesFromListMethod();
                 //*****  updating ASH seq_id  *************

                Map serverToAppAssetsScheduleHistoryMap
                        =  dto.getServerToAppAssetsScheduleHistoryMap() ;

                if(serverToAppAssetsScheduleHistoryMap != null) {

                    String args[] = {};

                    Iterator myIterator1 = serverToAppAssetsScheduleHistoryMap.keySet().iterator();
                    while (myIterator1.hasNext()) {
                        String key = (String) myIterator1.next();
                        String value = (String) serverToAppAssetsScheduleHistoryMap.get(key);
                        Log.d(TAG, " ASH key_value******");
                        Log.d(TAG, "key**" + key + "\n" + "value**" + value);

                        StringTokenizer tokens = new StringTokenizer(key, "_");
                        dsi = tokens.nextToken();
                        di = tokens.nextToken();

                        Log.d(TAG, "dsi is**" + dsi + "\n" + "di is**" + di);

                        try {

                            if (db != null) {
                                Log.d(TAG, " updating ASH records from device****");

                                ContentValues cv = new ContentValues();
                                cv.put("seq_id", value);
                                db.update("assets_schedule_history",cv,"device_id="+di+ "  and  device_seq_id="+dsi,null);
                                Log.d(TAG, " executing * ASH * seq_id updation query");
                                String ashUpdateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date());
                                Log.d(TAG,"Response Time is---"+ashUpdateTime);
                                globals.setAshSeqUpdateTime(ashUpdateTime);

                               /* Log.d(TAG, " updating ASC records from device****");

                                ContentValues cv2 = new ContentValues();
                                cv2.put("seq_id",value);
                                db.update("asset_schedule_content",cv2,"device_id="+di+ "  and device_ash_seq_id="+dsi,null);
                                Log.d(TAG, " executing ** ASC ** seq_id updation query");*/
                                // String ascUpdateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(new Date());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }else {
                    Log.d(TAG," No AppToServer ** ASH ** Data");
                }

                //*********** updating ASAR seq_id ****************


                Map serverToAppAssetScheduleActivityRecordMap = dto.getServerToAppAssetScheduleActivityRecordMap();
                if(serverToAppAssetScheduleActivityRecordMap != null) {

                    Iterator myIterator2 = serverToAppAssetScheduleActivityRecordMap.keySet().iterator();
                    while (myIterator2.hasNext()) {
                        String key2 = (String) myIterator2.next();
                        String value2 = (String) serverToAppAssetScheduleActivityRecordMap.get(key2);
                        Log.d(TAG, " ASAR key_value******");
                        Log.d(TAG, "key**" + key2 + "\n" + "value**" + value2);

                        StringTokenizer tokens = new StringTokenizer(key2, "_");
                        dsi = tokens.nextToken();
                        di = tokens.nextToken();
                        Log.d(TAG, "dsi is**" + dsi + "\n" + "di is**" + di);

                        StringTokenizer tokenizer = new StringTokenizer(value2, "_");
                        amos = tokenizer.nextToken();
                        ashi = tokenizer.nextToken();
                        Log.d(TAG, "ashi is**" + ashi + "\n" + "amos is**" + amos);

                        try{
                            if ((db != null)){
                                Log.d(TAG, " updating ASAR records from device****");

                                ContentValues cv1 = new ContentValues();
                                cv1.put("asset_schedule_history_id", ashi);
                                cv1.put("asset_measure_obser_seq_id", amos);
                                db.update("asset_schedule_activity_record",cv1,"device_id="+di+ "  and  device_seq_id="+dsi,null);
                                Log.d(TAG, " executing seq_id and mesureORobser id updation query");
                            }
                        }catch (Exception e){
                                       e.printStackTrace();
                                 }

                    }
                }else {
                    Log.d(TAG," No AppToServer ** ASAR ** Data");
                }


                //*********** updating ASC content id ****************

             /*   Map serverToAppAssetScheduleContentMap = dto.getServerToAppAssetScheduleContentMap();
                if(serverToAppAssetScheduleContentMap != null){
                    Iterator myIterator3 = serverToAppAssetScheduleContentMap.keySet().iterator();
                    while (myIterator3.hasNext()){
                        String key3 = (String) myIterator3.next();
                        String value3 = (String) serverToAppAssetScheduleContentMap.get(key3);
                        Log.d(TAG, " ASC key_value******");
                        Log.d(TAG, "key**" + key3 + "\n" + "value**" + value3);
                        StringTokenizer tokens = new StringTokenizer(key3,"_");
                        dsi = tokens.nextToken();
                        di = tokens.nextToken();
                        Log.d(TAG, "dsi is**" + dsi + "\n" + "di is**" + di);
                        try{
                            if (db != null) {
                                Log.d(TAG, " updating ASC records from device****");
                                ContentValues cv3 = new ContentValues();
                                cv3.put("content_id",value3);
                                db.update("asset_schedule_content",cv3,"device_id="+di+ " and device_seq_id=" +dsi,null);
                                Log.d(TAG, " executing ** ASC ** content_id updation query");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }else {
                    Log.d(TAG," No AppToServer ** ASC ** Data");
                }*/


                List<FacilityDto> insertFacilityDtos = dto.getCreatedResponseFacilityDto().getFacilityDtos();

                if (insertFacilityDtos != null && insertFacilityDtos.size() > 0) {

                    Log.d(TAG, "facility insert records : " + insertFacilityDtos.size());

                    for (FacilityDto facilityDto : insertFacilityDtos) {

                        dataUpdateDAO.insertFacilityData(facilityDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<FacilityDto> updateFacilityDtos = dto.getUpdatedResponseFacilityDto().getFacilityDtos();

                if (updateFacilityDtos != null && updateFacilityDtos.size() > 0) {
                    Log.d(TAG, "facility update records : " + updateFacilityDtos.size());

                    for (FacilityDto facilityDto : updateFacilityDtos) {

                        dataUpdateDAO.updateFacilityData(facilityDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<PowerBlocksDto> insertPowerBlocksDtos = dto.getCreatedResponsePowerBlocksDto().getPowerBlocksDtos();

                if (insertPowerBlocksDtos != null && insertPowerBlocksDtos.size() > 0) {

                    Log.d(TAG, "power block insert records : " + insertPowerBlocksDtos.size());

                    for (PowerBlocksDto powerBlocksDto : insertPowerBlocksDtos) {

                        dataUpdateDAO.insertPowerBlocksData(powerBlocksDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }


                List<PowerBlocksDto> updatePowerBlocksDtos = dto.getUpdatedResponsePowerBlocksDto().getPowerBlocksDtos();

                if (updatePowerBlocksDtos != null && updatePowerBlocksDtos.size() > 0) {
                    Log.d(TAG, "power block update records : " + updatePowerBlocksDtos.size());

                    for (PowerBlocksDto powerBlocksDto : updatePowerBlocksDtos) {

                        dataUpdateDAO.updatePowerBlocksData(powerBlocksDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<OheLocationDto> insertOheLocationDtos = dto.getCreatedResponseOheLocationDto().getOheLocationDtos();

                if (insertOheLocationDtos != null && insertOheLocationDtos.size() > 0) {

                    Log.d(TAG, "Ohe Location insert records : " + insertOheLocationDtos.size());

                    for (OheLocationDto oheLocationDto : insertOheLocationDtos) {

                        dataUpdateDAO.insertOheLocationData(oheLocationDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }

                List<OheLocationDto> updateOheLocationDtos = dto.getUpdatedResponseOheLocationDto().getOheLocationDtos();

                if (updateOheLocationDtos != null && updateOheLocationDtos.size() > 0) {

                    Log.d(TAG, "Ohe Location update records : " + updateOheLocationDtos.size());

                    for (OheLocationDto oheLocationDto : updateOheLocationDtos) {

                        dataUpdateDAO.updateOheLocationData(oheLocationDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }


                List<AppDeviceUnitDto> insertAppDeviceUnitDtos = dto.getCreatedResponseAppDeviceUnitDto().getAppDeviceUnitDtos();

                if (insertAppDeviceUnitDtos != null && insertAppDeviceUnitDtos.size() > 0) {

                    Log.d(TAG, "App Device Unit insert records : " + insertAppDeviceUnitDtos.size());

                    for (AppDeviceUnitDto appDeviceUnitDto : insertAppDeviceUnitDtos) {

                        dataUpdateDAO.insertAppDeviceUnitData(appDeviceUnitDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }


                List<AppDeviceUnitDto> updateAppDeviceUnitDtos = dto.getUpdatedResponseAppDeviceUnitDto().getAppDeviceUnitDtos();

                if (updateAppDeviceUnitDtos != null && updateAppDeviceUnitDtos.size() > 0) {
                    Log.d(TAG, "App Device Unit update records : " + updateAppDeviceUnitDtos.size());

                    for (AppDeviceUnitDto appDeviceUnitDto : updateAppDeviceUnitDtos) {

                        dataUpdateDAO.updateAppDeviceUnitData(appDeviceUnitDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ElementarySectionsDto> insertElementarySectionsDtos = dto.getCreatedResponseElementarySectionsDto().getElementarySectionsDtos();

                if (insertElementarySectionsDtos != null && insertElementarySectionsDtos.size() > 0) {

                    Log.d(TAG, "elementary section insert records : " + insertElementarySectionsDtos.size());

                    for (ElementarySectionsDto elementarySectionsDto : insertElementarySectionsDtos) {

                        dataUpdateDAO.insertElementarySectionsData(elementarySectionsDto, db);

                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ElementarySectionsDto> updateElementarySectionsDtos = dto.getUpdatedResponseElementarySectionsDto().getElementarySectionsDtos();


                if (updateElementarySectionsDtos != null && updateElementarySectionsDtos.size() > 0) {

                    Log.d(TAG, "elementary section update records : " + updateElementarySectionsDtos.size());


                    for (ElementarySectionsDto elementarySectionsDto : updateElementarySectionsDtos) {

                        dataUpdateDAO.updateElementarySectionsData(elementarySectionsDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<AssetStatusUpdateDto> insertAssetStatusUpdateDtos = dto.getCreatedResponseAssetStatusUpdateDto().getAssetStatusUpdateDtos();

                if (insertAssetStatusUpdateDtos != null && insertAssetStatusUpdateDtos.size() > 0) {

                    Log.d(TAG, "Asset Status Update Dto insert records : " + insertAssetStatusUpdateDtos.size());


                    for (AssetStatusUpdateDto assetStatusUpdateDto : insertAssetStatusUpdateDtos) {

                        dataUpdateDAO.insertAssetStatusUpdateData(assetStatusUpdateDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetStatusUpdateDto> updateAssetStatusUpdate = dto.getUpdatedResponseAssetStatusUpdateDto().getAssetStatusUpdateDtos();

                if (updateAssetStatusUpdate != null && updateAssetStatusUpdate.size() > 0) {

                    Log.d(TAG, "Asset Status Update Dto update records : " + updateAssetStatusUpdate.size());

                    for (AssetStatusUpdateDto assetStatusUpdateDto : updateAssetStatusUpdate) {

                        dataUpdateDAO.updateAssetStatusUpdateData(assetStatusUpdateDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<ScheduleDto> insertScheduleDtos = dto.getCreatedResponseScheduleDto().getScheduleDtos();

                if (insertScheduleDtos != null && insertScheduleDtos.size() > 0) {

                    Log.d(TAG, "schedule insert records : " + insertScheduleDtos.size());


                    for (ScheduleDto scheduleDto : insertScheduleDtos) {

                        dataUpdateDAO.insertScheduleData(scheduleDto, db);

                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ScheduleDto> updateScheduleDtos = dto.getUpdatedResponseScheduleDto().getScheduleDtos();

                if (updateScheduleDtos != null && updateScheduleDtos.size() > 0) {
                    Log.d(TAG, "schedule update records : " + updateScheduleDtos.size());

                    for (ScheduleDto scheduleDto : updateScheduleDtos) {

                        dataUpdateDAO.updateScheduleData(scheduleDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ProductDto> insertProductDtos = dto.getCreatedResponseProductDto().getProductDtos();

                if (insertProductDtos != null && insertProductDtos.size() > 0) {

                    Log.d(TAG, "product insert records : " + insertProductDtos.size());

                    for (ProductDto productDto : insertProductDtos) {
                        dataUpdateDAO.insertProductData(productDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ProductDto> updateProductDtos = dto.getUpdatedResponseProductDto().getProductDtos();

                if (updateProductDtos != null && updateProductDtos.size() > 0) {
                    Log.d(TAG, "product update records : " + updateProductDtos.size());


                    for (ProductDto productDto : updateProductDtos) {
                        dataUpdateDAO.updateProductData(productDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetScheduleAssocDto> insertAssetScheduleAssocDtos = dto.getCreatedResponseAssetScheduleAssocDto().getAssetScheduleAssocDtos();

                if (insertAssetScheduleAssocDtos != null && insertAssetScheduleAssocDtos.size() > 0) {

                    Log.d(TAG, "Asset Schedule Assoc Dto insert records : " + insertAssetScheduleAssocDtos.size());

                    for (AssetScheduleAssocDto assetScheduleAssocDto : insertAssetScheduleAssocDtos) {

                        dataUpdateDAO.insertAssetScheduleAssocData(assetScheduleAssocDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetScheduleAssocDto> updateAssetScheduleAssocDtos = dto.getUpdatedResponseAssetScheduleAssocDto().getAssetScheduleAssocDtos();

                if (updateAssetScheduleAssocDtos != null && updateAssetScheduleAssocDtos.size() > 0) {
                    Log.d(TAG, "Asset Schedule Assoc Dto update records : " + updateAssetScheduleAssocDtos.size());


                    for (AssetScheduleAssocDto assetScheduleAssocDto : updateAssetScheduleAssocDtos) {

                        dataUpdateDAO.updateAssetScheduleAssocData(assetScheduleAssocDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ProductCategoryMemberDto> insertProductCategoryMemberDtos = dto.getCreatedResponseProductCategoryMemberDto().getProductCategoryMemberDtos();

                if (insertProductCategoryMemberDtos != null && insertProductCategoryMemberDtos.size() > 0) {

                    Log.d(TAG, "Product Category Member insert records : " + insertProductCategoryMemberDtos.size());

                    for (ProductCategoryMemberDto productCategoryMemberDto : insertProductCategoryMemberDtos) {
                        dataUpdateDAO.insertProductCategoryMemberData(productCategoryMemberDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<ProductCategoryMemberDto> updateProductCategoryMemberDtos = dto.getUpdatedResponseProductCategoryMemberDto().getProductCategoryMemberDtos();

                if (updateProductCategoryMemberDtos != null && updateProductCategoryMemberDtos.size() > 0) {
                    Log.d(TAG, "Product Category Member update records : " + updateProductCategoryMemberDtos.size());

                    for (ProductCategoryMemberDto productCategoryMemberDto : updateProductCategoryMemberDtos) {
                        dataUpdateDAO.updateProductCategoryMemberData(productCategoryMemberDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<AssetScheduleActivityAssocDto> insertAssetScheduleActivityAssocDtos = dto.getCreatedResponseAssetScheduleActivityAssocDto().getAssetScheduleActivityAssocDtos();

                if (insertAssetScheduleActivityAssocDtos != null && insertAssetScheduleActivityAssocDtos.size() > 0) {

                    Log.d(TAG, "Asset Schedule Activity Assoc Dto insert records : " + insertAssetScheduleActivityAssocDtos.size());


                    for (AssetScheduleActivityAssocDto assetScheduleActivityAssocDto : insertAssetScheduleActivityAssocDtos) {

                        dataUpdateDAO.insertAssetScheduleActivityAssocData(assetScheduleActivityAssocDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetScheduleActivityAssocDto> updateAssetScheduleActivityAssocDtos = dto.getUpdatedResponseAssetScheduleActivityAssocDto().getAssetScheduleActivityAssocDtos();

                if (updateAssetScheduleActivityAssocDtos != null && updateAssetScheduleActivityAssocDtos.size() > 0) {
                    Log.d(TAG, "Asset Schedule Activity Assoc Dto update records : " + updateAssetScheduleActivityAssocDtos.size());

                    for (AssetScheduleActivityAssocDto assetScheduleActivityAssocDto : updateAssetScheduleActivityAssocDtos) {

                        dataUpdateDAO.updateAssetScheduleActivityAssocData(assetScheduleActivityAssocDto, db);

                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }
                List<MeasureOrActivityListDto> insertMeasureOrActivityListDtos = dto.getCreatedResponseMeasureOrActivityListDto().getMeasureOrActivityListDtos();

                if (insertMeasureOrActivityListDtos != null && insertMeasureOrActivityListDtos.size() > 0) {

                    Log.d(TAG, "Measure Or Activity List Dto insert records : " + insertMeasureOrActivityListDtos.size());

                    for (MeasureOrActivityListDto measureOrActivityListDto : insertMeasureOrActivityListDtos) {

                        dataUpdateDAO.insertMeasureOrActivityData(measureOrActivityListDto, db);

                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<MeasureOrActivityListDto> updateMeasureOrActivityListDtos = dto.getUpdatedResponseMeasureOrActivityListDto().getMeasureOrActivityListDtos();

                if (updateMeasureOrActivityListDtos != null && updateMeasureOrActivityListDtos.size() > 0) {

                    Log.d(TAG, "Measure Or Activity List Dto update records : " + updateMeasureOrActivityListDtos.size());


                    for (MeasureOrActivityListDto measureOrActivityListDto : updateMeasureOrActivityListDtos) {

                        dataUpdateDAO.updateMeasureOrActivityData(measureOrActivityListDto, db);

                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }


               List<AssetMonthlyTargetsDto> insertAssetMonthlyTargetsDtos = dto.getCreatedResponseAssetMonthlyTargetsDto().getAssetMonthlyTargetsDtos();

                if (insertAssetMonthlyTargetsDtos != null && insertAssetMonthlyTargetsDtos.size() > 0) {

                    Log.d(TAG, "Asset Monthly Targets Dto insert records : " + insertAssetMonthlyTargetsDtos.size());


                    for (AssetMonthlyTargetsDto assetMonthlyTargetsDto : insertAssetMonthlyTargetsDtos) {

                        dataUpdateDAO.insertAssetMonthlyTargetsData(assetMonthlyTargetsDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetMonthlyTargetsDto> updateAssetMonthlyTargetsDtos = dto.getUpdatedResponseAssetMonthlyTargetsDto().getAssetMonthlyTargetsDtos();

                if (updateAssetMonthlyTargetsDtos != null && updateAssetMonthlyTargetsDtos.size() > 0) {

                    Log.d(TAG, "Asset Monthly targets Dto update records : " + updateAssetMonthlyTargetsDtos.size());

                    for (AssetMonthlyTargetsDto assetMonthlyTargetsDto : updateAssetMonthlyTargetsDtos) {

                        dataUpdateDAO.updateAssetMonthlyTargetsData(assetMonthlyTargetsDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<UserLoginDto> insertUserLoginDtos = dto.getCreatedResponseUserLoginDto().getUserLoginDtos();

                if (insertUserLoginDtos != null && insertUserLoginDtos.size() > 0) {

                    Log.d(TAG, "User Login Dto insert records : " + insertUserLoginDtos.size());


                    for (UserLoginDto userLoginDto : insertUserLoginDtos) {

                        dataUpdateDAO.insertUserLoginData(userLoginDto,db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<UserLoginDto> updateUserLoginDtos = dto.getUpdatedResponseUserLoginDto().getUserLoginDtos();

                if (updateUserLoginDtos != null && updateUserLoginDtos.size() > 0) {

                    Log.d(TAG, "User Login Dto update records : " + updateUserLoginDtos.size());

                    for (UserLoginDto userLoginDto : updateUserLoginDtos) {

                        dataUpdateDAO.updateUserLoginData(userLoginDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<AssetMasterDataDto> insertAssetMasterDataDtos = dto.getCreatedResponseAssetMasterDataDto().getAssetMasterDataDtos();

                if (insertAssetMasterDataDtos != null && insertAssetMasterDataDtos.size() > 0) {

                    Log.d(TAG, "Asset Master Data Dto insert records : " + insertAssetMasterDataDtos.size());


                    for (AssetMasterDataDto assetMasterDataDto : insertAssetMasterDataDtos) {

                        dataUpdateDAO.insertAssetMasterData(assetMasterDataDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetMasterDataDto> updateAssetMasterDataDtos = dto.getUpdatedResponseAssetMasterDataDto().getAssetMasterDataDtos();

                if (updateAssetMasterDataDtos != null && updateAssetMasterDataDtos.size() > 0) {

                    Log.d(TAG, "Asset Master Data Dto update records : " + updateAssetMasterDataDtos.size());

                    for (AssetMasterDataDto assetMasterDataDto : updateAssetMasterDataDtos) {

                        dataUpdateDAO.updateAssetMasterData(assetMasterDataDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }






                List<AssetsScheduleHistoryDto> insertAssetsScheduleHistoryDtos = dto.getServerToAppCreatedResponseAssetsScheduleHistoryDto().getAssetsScheduleHistoryDtos();

                if (insertAssetsScheduleHistoryDtos != null && insertAssetsScheduleHistoryDtos.size() > 0) {

                    Log.d(TAG, "Assets Schedule History Dto insert records : " + insertAssetsScheduleHistoryDtos.size());

                    for (AssetsScheduleHistoryDto assetsScheduleHistoryDto : insertAssetsScheduleHistoryDtos) {
                        dataUpdateDAO.insertAssetsScheduleHistoryData(assetsScheduleHistoryDto, db);

                    }


                    progressValue = progressValue + 1;
                    publishProgress(progressValue);

                }

                List<AssetsScheduleHistoryDto> updateAssetsScheduleHistoryDtos = dto.getServerToAppUpdatedResponseAssetsScheduleHistoryDto().getAssetsScheduleHistoryDtos();

                if (updateAssetsScheduleHistoryDtos != null && updateAssetsScheduleHistoryDtos.size() > 0) {

                    Log.d(TAG, "Assets Schedule History Dto update records : " + updateAssetsScheduleHistoryDtos.size());

                    for (AssetsScheduleHistoryDto assetsScheduleHistoryDto : updateAssetsScheduleHistoryDtos) {

                        dataUpdateDAO.updateAssetsScheduleHistoryData(assetsScheduleHistoryDto, db);


                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }


                List<AssetScheduleActivityRecordDto> insertAssetScheduleActivityRecordDtos = dto.getServerToAppCreatedResponseAssetScheduleActivityRecordDto().getAssetScheduleActivityRecordDtos();

                if (insertAssetScheduleActivityRecordDtos != null && insertAssetScheduleActivityRecordDtos.size() > 0) {

                    Log.d(TAG, "Asset Schedule Activity Record Dto insert records : " + insertAssetScheduleActivityRecordDtos.size());


                    for (AssetScheduleActivityRecordDto assetScheduleActivityRecordDto : insertAssetScheduleActivityRecordDtos) {

                        dataUpdateDAO.insertAssetScheduleActivityRecordData(assetScheduleActivityRecordDto, db);
                    }

                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                List<AssetScheduleActivityRecordDto> updateAssetScheduleActivityRecordDtos = dto.getServerToAppUpdatedResponseAssetScheduleActivityRecordDto().getAssetScheduleActivityRecordDtos();
                if (updateAssetScheduleActivityRecordDtos != null && updateAssetScheduleActivityRecordDtos.size() > 0) {
                    Log.d(TAG, "Asset Schedule Activity Record Dto update records : " + updateAssetScheduleActivityRecordDtos.size());


                    for (AssetScheduleActivityRecordDto assetScheduleActivityRecordDto : updateAssetScheduleActivityRecordDtos) {

                        dataUpdateDAO.updateAssetScheduleActivityRecordData(assetScheduleActivityRecordDto, db);
                    }
                    progressValue = progressValue + 1;
                    publishProgress(progressValue);
                }

                result = true;
            } catch (Exception e) {

                Log.e(TAG, "updateDatabase  - : " + e.getMessage());
              e.printStackTrace();
            }


        } else {

            result = true;
        }


        return result;
    }


}
