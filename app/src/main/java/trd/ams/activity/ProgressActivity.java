package trd.ams.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.ArrayList;
import java.util.List;

import trd.ams.R;
import trd.ams.async.DataUpdateAsyncTask;
import trd.ams.async.ProgressAsyncTask;
import trd.ams.common.Globals;
import trd.ams.dao.ReportsDAO;
import trd.ams.database.DatabaseHelper;

public class ProgressActivity extends TitleBarActivity {

    Globals globals;
    String TAG = "ProgressActivity";
    private ProgressAsyncTask progressAsync;
    Button btnSubmitProgress;
    Spinner spFacility;
    DatabaseHelper dbhelper = null;
    String sql;
    Cursor cursor = null;
    SQLiteDatabase db;
    Context context;
    String facilityName;
    String[] facilityAll;
    String facilityOk;
    ArrayList<String> listFacility;
    SpinnerFacilityAdapterP facilityAdapterP;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globals = (Globals) getApplication();
        setupTitleBar(getResources().getString(R.string.app_name), false, false, true);
        // setContentView(R.layout.activity_progress);
        btnSubmitProgress = findViewById(R.id.btn_submit_progress);
        spFacility = findViewById(R.id.spFacility);
       /* this.context = context;
        dbhelper = DatabaseHelper.getInstance(context);
        net.sqlcipher.database.SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");*/

        listFacility = new ArrayList<String>();

        listFacility.clear();
        listFacility.addAll(getFacility());
        Log.d(TAG,"facility size--" +listFacility.size());

        // Facility


        facilityAdapterP = new SpinnerFacilityAdapterP(ProgressActivity.this,
                            android.R.layout.simple_spinner_item,listFacility);
        spFacility.setAdapter(facilityAdapterP);
        facilityAdapterP.notifyDataSetChanged();

        spFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                facilityName = facilityAdapterP.getItem(position);
                Log.d(TAG,"facility: " + facilityName);
                globals.setFacilityName(facilityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





       /* ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facilityAll);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spFacility.setAdapter(spinnerArrayAdapter);
//  select values of spinner
        spFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    spImei.setSelection(parent);
                facilityOk = (String) parent.getItemAtPosition(position);

                //operation1 = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/



        btnSubmitProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSubmitProgress.setBackgroundColor(Color.parseColor("#FFDFE1DA")); // faded Grey
                 new Handler().post(new Runnable()
                {
                    public void run(){
                        progressAsync = new ProgressAsyncTask(ProgressActivity.this,getApplicationContext());
                        progressAsync.execute();
                        btnSubmitProgress.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green
                    }
                });
            }
        });
               btnSubmitProgress.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green


    }

    // Get Facility

    private ArrayList<String> getFacility(){
        ArrayList<String> facilityList = null;
        try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(ProgressActivity.this);
            net.sqlcipher.database.SQLiteDatabase db = dbhelper.getDBObject(0);
            ReportsDAO reportsDAO = ReportsDAO.getInstance();
            facilityList = reportsDAO.getFacility(db);

        }catch (Exception e){
            e.printStackTrace();
        }

        return facilityList;
    }


    //  Facility Adapter


    class SpinnerFacilityAdapterP extends  ArrayAdapter<String> {

        private  Context context;
        private  ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerFacilityAdapterP(Context context,int textViewResourceId,ArrayList<String> values){
            super(context,textViewResourceId,values);
            this.context = context;
            this.values = values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public int getCount() {return  values.size();}
        public String getItem(int position) {return values.get(position);}
        public int getPosition(String facilityId){
            int position = 0 ;
            for(int i = 0; i < values.size(); i++) {
                if (values.get(i).equals(facilityId)) {
                    position = i;
                    break;
                }
            }
            return position;
        }
        public View getView (int position, View convertView, ViewGroup parent){
            View itemView = inflater.inflate(R.layout.measure_spinner,parent,false);
            String facilityId = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(facilityId);

            return itemView;
        }
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            return  getView(position,convertView,parent);
        }

    }

 @Override
   public int getLayoutResource(){return  R.layout.activity_progress;}
}
