package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import trd.ams.R;
import trd.ams.async.GraphAsyncTask;
import trd.ams.async.OverdueAsyncTask;
import trd.ams.common.Globals;
import trd.ams.dao.ReportsDAO;
import trd.ams.database.DatabaseHelper;

public class AssetScheduleGraphActivity  extends TitleBarActivity {

    String TAG = "AssetScheduleGraphActivity";
    private GraphAsyncTask graphAsync;
    Globals globals;
    Button btnGraph;
    Spinner spFacility;
    SpinnerFacAdapter facilityAdapter;

    ArrayList<String> listFacility;
    String facilityName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_asset_schedule_graph);


        globals = (Globals) getApplication();
        setupTitleBar("TRD_AMS", false, false, true);

        btnGraph = findViewById(R.id.btn_submit_graph);

        listFacility = new ArrayList<String>();

        listFacility.clear();
        listFacility.addAll(getFacility());
        Log.d(TAG,"facility size--" +listFacility.size());

        // Facility
        spFacility = findViewById(R.id.spFacilityG);
        facilityAdapter = new SpinnerFacAdapter(AssetScheduleGraphActivity.this,
                android.R.layout.simple_spinner_item, listFacility);
        facilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFacility.setAdapter(facilityAdapter);
        facilityAdapter.notifyDataSetChanged();

        spFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                facilityName = facilityAdapter.getItem(position);
                Log.d(TAG,"facility: " + facilityName);

                globals.setFacilityName(facilityName);

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btnGraph.setBackgroundColor(Color.parseColor("#FFDFE1DA")); // faded Grey
                new Handler().post(new Runnable() {
                    public void run() {
                        globals.setFacilityName(facilityName);
                        graphAsync = new GraphAsyncTask(AssetScheduleGraphActivity.this, getApplicationContext());
                        graphAsync.execute();

                        btnGraph.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green
                    }
                });
            }
        });
        btnGraph.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green

    }


    //Get Facility
    private ArrayList<String> getFacility(){
        ArrayList<String> facilityList =  null;
        try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetScheduleGraphActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            facilityList = reportsDAO.getFacility(db);

        } catch (Exception e){
            e.printStackTrace();
        }
        return facilityList;
    }


        //  Facility adapter

        class SpinnerFacAdapter extends ArrayAdapter<String> {


            private Context context;

            private ArrayList<String> values;
            LayoutInflater inflater;

            public SpinnerFacAdapter(Context context, int textViewResourceId,
                                     ArrayList<String> values) {
                super(context, textViewResourceId, values);
                this.context = context;
                this.values = values;
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            public int getCount() {
                return values.size();
            }

            public String getItem(int position) {
                return values.get(position);
            }

            // public long getItemId(int position) {
            //     return position;
            // }
            public int getPosition(String facilityId) {
                int position = 0;

                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).equals(facilityId)) {
                        position = i;
                        break;
                    }
                }

                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
                String facilityId = values.get(position);
                TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvName.setText(facilityId);

                return itemView;
            }

            public View getDropDownView(int position, View convertView, ViewGroup
                    parent) {
                return getView(position, convertView, parent);
            }
        }

       @Override
        public int getLayoutResource(){return R.layout.activity_asset_schedule_graph;}


}
