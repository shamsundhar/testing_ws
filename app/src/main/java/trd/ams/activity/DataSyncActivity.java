package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import trd.ams.R;
import trd.ams.async.DataUpdateAsyncTask;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.util.Utils;

public class DataSyncActivity extends TitleBarActivity {

    Globals globals;
    String TAG = SplashActivity.class.getSimpleName();
    private DataUpdateAsyncTask dataUpdateTask;
    Button btnDataSyncNow;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        globals = (Globals)getApplication();

        setupTitleBar(globals.getFacilityName(), true, false, true);

        sharedPref = this.getSharedPreferences(
                this.getString(R.string.data_sync_preferences_file), Context.MODE_PRIVATE);


        TextView tvDataSyncStatus = findViewById(R.id.tvDataSyncStatus);
        tvDataSyncStatus.setText("Data Sync Status : " + sharedPref.getString("data_sync_status", ""));

        TextView tvDataSyncStartTime = findViewById(R.id.tvDataSyncStartTime);
        tvDataSyncStartTime.setText("Start Time : " + sharedPref.getString("data_sync_start_time", ""));

        TextView tvDataSyncFinishTime = findViewById(R.id.tvDataSyncFinishTime);
        tvDataSyncFinishTime.setText("Finish Time : " + sharedPref.getString("data_sync_finish_time", ""));

        TextView tvDataSyncResult = findViewById(R.id.tvDataSyncResult);
        tvDataSyncResult.setText("Result : " + sharedPref.getString("data_sync_result", ""));

        Log.d(TAG,"got details from DUAT");
        btnDataSyncNow = (Button)findViewById(R.id.btnDataSyncNow);


        if (sharedPref.getString("data_sync_status","").equals("Started")){

            btnDataSyncNow.setEnabled(false);

        }




        btnDataSyncNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (!sharedPref.getString("data_sync_status","").equals("Started")) {


                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                dataUpdateTask = new DataUpdateAsyncTask(DataSyncActivity.this, getApplicationContext());

                                Log.d(TAG,"data**sync**first");
                                dataUpdateTask.execute();

                            /*    Intent intent2=new Intent(DataSyncActivity.this,LoginActivity.class );
                                startActivity(intent2);*/
                            }
                        });

                    } else {

                        Toast.makeText(DataSyncActivity.this, "Data Sync in progress ", Toast.LENGTH_SHORT).show();
                    }

            }
        });

       /* btnDataRefresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                if (!sharedPref.getString("data_sync_status","").equals("Started")) {

                    DatabaseHelper dbhelper = DatabaseHelper.getInstance(getApplicationContext());
                    dbhelper.checkDatabaseTables();
                    dbhelper.TruncateDatabaseTables();
                    Log.d(TAG,"Deleting data from tables");
                    dbhelper.checkDatabaseTables();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            dataUpdateTask = new DataUpdateAsyncTask(DataSyncActivity.this, getApplicationContext());
                            dataUpdateTask.execute();
                            Log.d(TAG,"data**sync**refresh");
                        }
                    });

                } else {



                    Toast.makeText(DataSyncActivity.this, "Data Sync / Refresh in progress ", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

    }



    @Override
    public int getLayoutResource() {
        return R.layout.activity_data_sync;
    }


}
