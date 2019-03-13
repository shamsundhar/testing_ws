package trd.ams.activity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import trd.ams.R;
import trd.ams.async.AIAsyncTask;
import trd.ams.async.DateAsyncTask;
import trd.ams.async.ESAsyncTask;
import trd.ams.async.RecordAsyncTask;
import trd.ams.common.Globals;
import trd.ams.dao.ReportsDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetType;

public class AsRecordActivity extends TitleBarActivity {
    String TAG = "AsRecordActivity";
    private ESAsyncTask esAsync;
    private AIAsyncTask aiAsync;
    private DateAsyncTask dateAsync;
    private RecordAsyncTask recordAsync;
    Button btnSubmitRecord;
    Globals globals;
    Spinner spFacility;
    Spinner spAssetTypes;
    Spinner spScheduleCode;
    Spinner spElementarySection;
    Spinner spAssetId;
    Spinner spDate;
    TextInputLayout tilKilometer;
    EditText etKilometer;
    String kilometer;
    String likeKm;

    String sendDate;

    SpinnerFacilityAdapter spinnerFacilityAdapter;
    SpinnerAssetTypesAdapter spinnerAssetTypesAdapter;
    SpinnerScheduleCodeAdapter scheduleCodeAdapter;
    SpinnerElementarySectionAdapter elementarySectionAdapter;
    SpinnerAssetsAdapter assetAdapter;
    SpinnerDatesAdapter datesAdapter;

    ArrayList<String> listFacility;
    ArrayList<AssetType> listAssetTypes;
    ArrayList<String> listScheduleCodes;
    ArrayList<String> listElementarySections;
    ArrayList<String> listAssets;
    ArrayList<String> listDates;
    ArrayList<String > listAssetId;

    String facilityName;
    String assetTypeId;
    String scheduleCode;
    String elementarySection;
    String assetId;
    String date;
    String depoId;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_as_record);
        globals = (Globals) getApplication();

        setupTitleBar("TRD_AMS",false,false,true);

        btnSubmitRecord = findViewById(R.id.btn_submit_record);


        spScheduleCode = findViewById(R.id.spSchedulecodesR);
      //  spElementarySection = findViewById(R.id.spElementarySectionsR);
        spAssetId = findViewById(R.id.spAssetIdsR);
        spDate = findViewById(R.id.spDateR);
        tilKilometer = findViewById(R.id.recordKm_text_input_layout);
        etKilometer = findViewById(R.id.etKilometersR);

        listFacility = new ArrayList<String>();
        listAssetTypes = new ArrayList<AssetType>();
        listScheduleCodes = new ArrayList<String>();
        listElementarySections = new ArrayList<String>();
        listAssets = new ArrayList<String>();
        listDates = new ArrayList<String>();
        listAssetId = new ArrayList<String>();


        listFacility.clear();
        listFacility.addAll(getFacility());
        Log.d(TAG,"facility size--" +listFacility.size());
//        facilityAdapter.notifyDataSetChanged();



        // Facility
        spFacility = (Spinner) findViewById(R.id.spFacilityR);
        spinnerFacilityAdapter = new SpinnerFacilityAdapter(AsRecordActivity.this,
                android.R.layout.simple_spinner_item, listFacility);
        spinnerFacilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFacility.setAdapter(spinnerFacilityAdapter);
        spinnerFacilityAdapter.notifyDataSetChanged();

        spFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                facilityName = spinnerFacilityAdapter.getItem(position);
                Log.d(TAG,"facility: " + facilityName);
                globals.setFacilityName(facilityName);

                DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsRecordActivity.this);
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
                spinnerAssetTypesAdapter.notifyDataSetChanged();

                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Asset types

        spAssetTypes = findViewById(R.id.spAssetTypesR);
        spinnerAssetTypesAdapter = new SpinnerAssetTypesAdapter(AsRecordActivity.this,
                android.R.layout.simple_spinner_item, listAssetTypes);

        spAssetTypes.setAdapter(spinnerAssetTypesAdapter);
        spinnerAssetTypesAdapter.notifyDataSetChanged();

        spAssetTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                    new Handler().post(new Runnable() {
                        public void run() {
                            aiAsync = new AIAsyncTask(AsRecordActivity.this, getApplicationContext());
                            aiAsync.execute();
                        }
                    });


                AssetType assetTypeObj = spinnerAssetTypesAdapter.getItem(position);
                assetTypeId = assetTypeObj.getAssetTypeId();
                Log.d(TAG, "assetTypeId: " + assetTypeId);
                globals.setAssetType(assetTypeId);


                listScheduleCodes.clear();
                listScheduleCodes.addAll(getScheduleCodes(assetTypeId));
                scheduleCodeAdapter.notifyDataSetChanged();



               /*    listAssets.clear();
                listAssets.addAll(getAsset());
                assetAdapter.notifyDataSetChanged();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        // Schedule Codes
        scheduleCodeAdapter = new SpinnerScheduleCodeAdapter(AsRecordActivity.this,
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

                listAssetId.clear();
                //listAssetId.add(0,"Select Asset Id");
                listAssetId.addAll(getAsset());
                assetAdapter.notifyDataSetChanged();


               /* listElementarySections.clear();
                listElementarySections.addAll(getElementarySection());
                elementarySectionAdapter.notifyDataSetChanged();*/
                listAssets.clear();
                listAssets.addAll(getAsset());
                assetAdapter.notifyDataSetChanged();

                getKiloMeter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        // Elementary Sections



        // Asset Ids

        assetAdapter = new SpinnerAssetsAdapter(AsRecordActivity.this,
                android.R.layout.simple_spinner_item, listAssets);
        assetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAssetId.setAdapter(assetAdapter);
        assetAdapter.notifyDataSetChanged();

        spAssetId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               /* assetId = assetAdapter.getItem(position);
                Log.d(TAG,"asset id:" +assetId);
                globals.setAssetId(assetId);*/

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            dateAsync = new DateAsyncTask(AsRecordActivity.this, getApplicationContext());
                            dateAsync.execute();
                        }
                    });


                assetId = assetAdapter.getItem(position);
                Log.d(TAG,"asset id:" +assetId);
                globals.setAssetId(assetId);



                listDates.clear();
                Log.d(TAG,"adding list of dates");
                listDates.addAll(getDates());
                datesAdapter.notifyDataSetChanged();


                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        getList();
                    }
                });



            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Dates

        datesAdapter = new SpinnerDatesAdapter(AsRecordActivity.this,
                        android.R.layout.simple_spinner_item, listDates);
        datesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDate.setAdapter(datesAdapter);
        datesAdapter.notifyDataSetChanged();

        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                date = datesAdapter.getItem(position);
                Log.d(TAG, "Date is:" + date);

                if (date != "Select Date") {

                    DateFormat ipFormat = new SimpleDateFormat("dd-mm-yyyy");
                DateFormat opFormat = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    Date dates = ipFormat.parse(date);
                    sendDate = opFormat.format(dates);
                    Log.d(TAG, "From date is--" + sendDate);
                    globals.setFromDate(sendDate);

                    globals.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        btnSubmitRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSubmitRecord.setBackgroundColor(Color.parseColor("#FFDFE1DA")); // faded Grey

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        recordAsync = new RecordAsyncTask(AsRecordActivity.this,getApplicationContext());
                        recordAsync.execute();
                        btnSubmitRecord.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green
                    }
                });

            }
        });

             btnSubmitRecord.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green

    }

    public ArrayList<String> getList(){
        listDates.clear();
        Log.d(TAG,"separate method");
        listDates.addAll(getDates());
        datesAdapter.notifyDataSetChanged();

        return listDates;
    }

    //Get Facility
    private ArrayList<String> getFacility(){
        ArrayList<String> facilityList =  null;
        try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsRecordActivity.this);

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
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsRecordActivity.this);

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
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsRecordActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            scheduleCodesList =   reportsDAO.getScheduleCodes(assetTypeId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleCodesList;
    }

    // Get Elementary Sections

    private ArrayList<String> getElementarySection() {

        ArrayList<String> elementaryList = new ArrayList<>();
        elementaryList.add(0,"Select ElementarySections");
        try{
            if(globals.getElementaryList() != null && globals.getElementaryList().size() >0){

                for (int i = 0; i < globals.getElementaryList().size() ; i++) {
                    Log.d(TAG,"ES is--" + globals.getElementaryList().get(i));
                    elementaryList.add(i+1,globals.getElementaryList().get(i));
                }
            }

            //return elementaryList;
        }catch (Exception e){
            e.printStackTrace();
        }
        //getKiloMeter();

        return elementaryList;
    }


    // Get Assets

    private ArrayList<String> getAsset(){

        ArrayList<String> assetList = new ArrayList<>();
        assetList.add(0,"Select AssetId");
        Log.d(TAG,"get Assets method");
        try{
            Log.d(TAG,"before:" +globals.getAssetList().size());
            if( globals.getAssetList() != null && globals.getAssetList().size() > 0 ) {
                Log.d(TAG,"after");
                for(int i = 0; i < globals.getAssetList().size() ; i++){
                    Log.d(TAG, " AI is --"+globals.getAssetList().get(i));
                    assetList.add(i+1, globals.getAssetList().get(i));
                }

            }
            else{
                Toast.makeText(AsRecordActivity.this,"No Asset Ids", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"No Asset Ids");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return assetList;
    }


    // Get Dates

    private ArrayList<String> getDates(){
        ArrayList<String> datesList = new ArrayList<>();
        datesList.add(0,"Select Date");
        Log.d(TAG,"Get Dates Method");
        try{
                  if(globals.getDateList() != null && globals.getAssetList().size() > 0 ){
                      for (int i = 0 ; i < globals.getDateList().size() ; i++){
                          Log.d(TAG, " Date -- "+globals.getDateList().get(i));
                          datesList.add(i+1,globals.getDateList().get(i));
                      }
                  }
                  else{
                      Log.d(TAG,"No Schedule dates");
                  }
        }catch (Exception e){
            e.printStackTrace();
        }

        return datesList;
    }


    // Get Kilometer


    public String getKiloMeter(){


        tilKilometer.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int i, int i1, int i2) {

                if(text.length() > 0 && text.length() <=3 ){
                    kilometer = etKilometer.getText().toString();
                    likeKm = kilometer;
                    int km = Integer.parseInt(kilometer);
                    Log.d(TAG,"KM is:" +km);

                    globals.setKilolmeter(likeKm);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               /* listAssets.clear();
              //  listAssets.addAll(getAssets());
                assetsAdapter.notifyDataSetChanged();*/
                listAssets.clear();
                listAssets.addAll(getAssets(likeKm));
                Log.d(TAG,"all assets added");
                assetAdapter.notifyDataSetChanged();
            }
        });



        return kilometer;
    }


    public Collection<? extends String> getAssets(String likeKm){

        ArrayList<String> filter = new ArrayList<String>();
        Log.d(TAG,"check");
        if(likeKm != null || !likeKm.equals("")){

            Log.d(TAG,"check2");
            for(String ids: listAssetId){
                Log.d(TAG,"check3");
                if(ids.startsWith(likeKm)){
                    Log.d(TAG,"check4");
                    filter.add(0,"Select Asset Id");
                    filter.add(ids);
                }
            }
            Log.d(TAG,"Filter list size--"+filter.size());

        }
        return filter;
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


    // Elementary Sections adapter
    class SpinnerElementarySectionAdapter extends ArrayAdapter<String> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerElementarySectionAdapter(Context context, int textViewResourceId,
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
        public int getPosition(String elementarySectionCode){
            int position = 0;
            //  Log.d(TAG, "getPosition - elementarySectionCode: " + elementarySectionCode);
            //  Log.d(TAG, "getPosition - elementarySectionCode values size: " + values.size());
            for(int i = 0; i < values.size(); i++) {

                //  Log.d(TAG, "getPosition - elementarySectionCode values - " + i +  " : " + values.get(i));

                if (values.get(i).equals(elementarySectionCode)) {

                    position = i;
                    break;
                }
            }

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            String elementarySectionCode = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(elementarySectionCode);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }



    // Assets adapter
    class SpinnerAssetsAdapter extends ArrayAdapter<String> {

        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerAssetsAdapter(Context context, int textViewResourceId,
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            String assetId = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(assetId);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }


    // Dates adapter
    class SpinnerDatesAdapter extends ArrayAdapter<String> {

        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerDatesAdapter(Context context, int textViewResourceId,
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            String date = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(date);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }

    @Override
    public int getLayoutResource(){return R.layout.activity_as_record;}

}
