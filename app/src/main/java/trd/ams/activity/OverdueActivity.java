package trd.ams.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import trd.ams.R;
import trd.ams.async.AIAsyncTask;
import trd.ams.async.ESAsyncTask;
import trd.ams.async.OverdueAsyncTask;
import trd.ams.common.Globals;
import trd.ams.dao.ReportsDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetType;

public class OverdueActivity extends TitleBarActivity {
    String TAG = "OverDueActivity";
    Button btnOverdue;
    Globals globals;
    private OverdueAsyncTask overdueAsync;
    Spinner spFacility;
    Spinner spAssetTypes;
    Spinner spScheduleCode;
    EditText spFromDate;
    EditText spThruDate;

    SpinnerFacilityAdapter facilityAdapter;
    SpinnerAssetTypesAdapter assetTypesAdapter;
    SpinnerScheduleCodeAdapter scheduleCodeAdapter;

    ArrayList<String> listFacility;
    ArrayList<AssetType> listAssetTypes;
    ArrayList<String> listScheduleCodes;

    String facilityName;
    String assetTypeId;
    String scheduleCode;
    String fromdate;
    String thruDate;
    String depoId;

    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
      // setContentView(R.layout.activity_overdue);
       globals = (Globals) getApplication();
        setupTitleBar("TRD_AMS",false,false,true);

        btnOverdue = findViewById(R.id.btn_submit_overdue);

        spAssetTypes = findViewById(R.id.spAssetTypesO);
        spScheduleCode = findViewById(R.id.spSchedulecodesO);
        spFromDate = findViewById(R.id.spFromDate);
        spThruDate = findViewById(R.id.spThruDate);

        listFacility = new ArrayList<String>();
        listAssetTypes = new ArrayList<AssetType>();
        listScheduleCodes = new ArrayList<String>();

        listFacility.clear();
        listFacility.addAll(getFacility());
        Log.d(TAG,"facility size--" +listFacility.size());


        // Facility
        spFacility = (Spinner) findViewById(R.id.spFacilityO);
        facilityAdapter = new SpinnerFacilityAdapter(OverdueActivity.this,
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

                DatabaseHelper dbhelper = DatabaseHelper.getInstance(OverdueActivity.this);
                SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");

                try{
                    if(db!= null && facilityName != "Select Depot"){

                        String sql = "SELECT facility_id from facility  WHERE facility_name = ?";
                        Cursor c = db.rawQuery(sql, new String[]{facilityName});

                        if(c != null && c.moveToFirst()){

                            depoId = c.getString(c.getColumnIndex("facility_id"));
                            Log.d(TAG,"depoId--"+depoId);
                            // globals.setDepoName(depoName);
                            globals.setDepoId(depoId);
                        }
                        c.close();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                listAssetTypes.clear();
                listAssetTypes.addAll(getAssetTypes(depoId));
               assetTypesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Asset types

        assetTypesAdapter = new SpinnerAssetTypesAdapter(OverdueActivity.this,
                android.R.layout.simple_spinner_item, listAssetTypes);

        spAssetTypes.setAdapter(assetTypesAdapter);
        assetTypesAdapter.notifyDataSetChanged();

        spAssetTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                AssetType assetTypeObj = assetTypesAdapter.getItem(position);
                assetTypeId = assetTypeObj.getAssetTypeId();
                Log.d(TAG, "assetTypeId: " + assetTypeId);
                globals.setAssetType(assetTypeId);


                listScheduleCodes.clear();
                listScheduleCodes.addAll(getScheduleCodes(assetTypeId));
                scheduleCodeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        // Schedule Codes
        scheduleCodeAdapter = new SpinnerScheduleCodeAdapter(OverdueActivity.this,
                android.R.layout.simple_spinner_item, listScheduleCodes);
        spScheduleCode.setAdapter(scheduleCodeAdapter);
        scheduleCodeAdapter.notifyDataSetChanged();


        spScheduleCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                scheduleCode = scheduleCodeAdapter.getItem(position);

                Log.d(TAG, "scheduleCode: " + scheduleCode);

                globals.setScheduleType(scheduleCode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        //From Date

        spFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(OverdueActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            String days = "";
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                String months;
                                if(day < 10)
                                    days = "0" + day ;
                                else
                                    days = ""+day;
                                if(month < 9)
                                    months  = "0" + (month+1) ;
                                else
                                    months = ""+ (month+1);
                                spFromDate.setText(days+"-"+(months)+"-"+year);
                                //fromdate = spFromDate.getText().toString();

                                DateFormat ipFormat = new SimpleDateFormat("dd-mm-yyyy");
                                DateFormat opFormat = new SimpleDateFormat("yyyy-mm-dd");
                                try {
                                    Date date = ipFormat.parse(spFromDate.getText().toString());
                                    fromdate = opFormat.format(date);
                                    Log.d(TAG,"From date is--" +fromdate);
                                    globals.setFromDate(fromdate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);


                // spFromDate.setText(days+"-"+(months)+"-"+year);
                datePickerDialog.show();
            }
        });


        //Thru Date

        spThruDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // to show  ** CURRENT MONTH **  ONLY
             /*int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
             calender.setMaxDate(day);*/

                DatePickerDialog datePickerDialog = new DatePickerDialog(OverdueActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            String days = "";
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                String months;
                                if(day < 10)
                                    days = "0" + day ;
                                else
                                    days = ""+day;
                                if(month < 9)
                                    months  = "0" + (month+1) ;
                                else
                                    months = ""+ (month+1);
                                spThruDate.setText(days+"-"+(months)+"-"+year);
                                DateFormat ipFormat = new SimpleDateFormat("dd-mm-yyyy");
                                DateFormat opFormat = new SimpleDateFormat("yyyy-mm-dd");
                                try{
                                    Date date = ipFormat.parse(spThruDate.getText().toString());
                                    thruDate = opFormat.format(date);
                                    globals.setThruDate(thruDate);
                                    Log.d(TAG,"Thru date is--" +thruDate);
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        btnOverdue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    public void run() {
                        btnOverdue.setBackgroundColor(Color.parseColor("#FFDFE1DA")); // faded Grey
                        overdueAsync = new OverdueAsyncTask(OverdueActivity.this, getApplicationContext());
                        overdueAsync.execute();
                        btnOverdue.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green
                    }
                });
            }
        });

             btnOverdue.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green

    }

    //Get Facility
    private ArrayList<String> getFacility(){
        ArrayList<String> facilityList =  null;
        try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(OverdueActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            facilityList = reportsDAO.getFacility(db);

        } catch (Exception e){
            e.printStackTrace();
        }
        return facilityList;
    }


    // Get Asset Types
    private ArrayList<AssetType> getAssetTypes(String depoId){

        ArrayList<AssetType> assetTypeList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(OverdueActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            assetTypeList =   reportsDAO.getAssetTypes(depoId,db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assetTypeList;
    }


    // Get Schedule Codes
    private ArrayList<String> getScheduleCodes(String assetTypeId){

        ArrayList<String> scheduleCodesList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(OverdueActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            scheduleCodesList =   reportsDAO.getScheduleCodes(assetTypeId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleCodesList;
    }




    //  Facility adapter

    class SpinnerFacilityAdapter extends ArrayAdapter<String> {


        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerFacilityAdapter(Context context, int textViewResourceId,
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
        public int getPosition(String facilityId){
            int position = 0;

            for(int i = 0; i < values.size(); i++) {
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




    // Asset Types adapter
    class SpinnerAssetTypesAdapter extends ArrayAdapter<AssetType> {


        private Context context;
        private ArrayList<AssetType> values;
        LayoutInflater inflater;

        public SpinnerAssetTypesAdapter(Context context, int textViewResourceId,
                                        ArrayList<AssetType> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return values.size();
        }

        public AssetType getItem(int position) {
            return values.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getPosition(String assetTypeId){
            int position = 0;
            // Log.d(TAG, "getPosition - assetTypeId: " + assetTypeId);
            // Log.d(TAG, "getPosition -  assetTypeId values size: " + values.size());
            for(int i = 0; i < values.size(); i++) {

                //  Log.d(TAG, "getPosition - assetTypeId values - " + i +  " : " + values.get(i).getAssetTypeId());
                if (values.get(i).getAssetTypeId().equals(assetTypeId)) {
                    position = i;
                    break;
                }
            }
            // Log.d(TAG, "getPosition - assetTypeId position: " + position);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            AssetType itemObj = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);

            tvName.setText(itemObj.getAssetTypeName());

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }


    // Schedule Code adapter
    class SpinnerScheduleCodeAdapter extends ArrayAdapter<String> {


        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerScheduleCodeAdapter(Context context, int textViewResourceId,
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
        public int getPosition(String scheduleCode){
            int position = 0;

            for(int i = 0; i < values.size(); i++) {
                if (values.get(i).equals(scheduleCode)) {
                    position = i;
                    break;
                }
            }

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            String scheduleCode = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(scheduleCode);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }
 @Override
    public int getLayoutResource(){return R.layout.activity_overdue;}

}
