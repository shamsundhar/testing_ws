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

import java.util.ArrayList;
import java.util.Collection;

import trd.ams.R;
import trd.ams.async.AIAsyncTask;
import trd.ams.async.ESAsyncTask;
import trd.ams.async.HistoryAsyncTask;
import trd.ams.common.Globals;
import trd.ams.dao.ReportsDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetType;

public class AsHistoryActivity  extends TitleBarActivity {

    String TAG = "AsHistoryActivity";
    private ESAsyncTask esAsync;
    private AIAsyncTask aiAsync;
    private HistoryAsyncTask historyAsync;
    Globals globals;
    Button btnSubmitHistory;
    Spinner spFacility;
    Spinner spAssetTypes;
    Spinner spScheduleCode;
    Spinner spElementarySection;
    Spinner spAssetId;
    TextInputLayout tilKilometer;
    EditText etKilometer;
    String kilometer;
    String likeKm;

    SpinnerFacilityAdapter facilityAdapter;
    AsHistoryActivity.SpinnerAssetTypesAdapter assetTypesAdapter;
    SpinnerScheduleCodeAdapter scheduleCodeAdapter;
    SpinnerElementarySectionAdapter elementarySectionAdapter;
    SpinnerAssetsAdapter assetAdapter;

    ArrayList<String> listFacility;
    ArrayList<AssetType> listAssetTypes;
    ArrayList<String> listScheduleCodes;
    ArrayList<String> listElementarySections;
    ArrayList<String> listAssets;
    ArrayList<String> listAssetId;

    String facilityName;
    String assetTypeId;
    String scheduleCode;
    String elementarySection;
    String assetId;
    String depoId;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_as_history);
        setupTitleBar("TRD_AMS", false, false, true);
        globals = (Globals) getApplication();

        btnSubmitHistory = findViewById(R.id.btn_submit_history);

        spAssetTypes = findViewById(R.id.spAssetTypes);
        spScheduleCode = findViewById(R.id.spSchedulecodes);
       // spElementarySection = findViewById(R.id.spElementarySections);
        spAssetId = findViewById(R.id.spAssetIds);
        tilKilometer = findViewById(R.id.historyKm_text_input_layout);
        etKilometer = findViewById(R.id.etKilometers);

        listFacility = new ArrayList<String>();
        listAssetTypes = new ArrayList<AssetType>();
        listScheduleCodes = new ArrayList<String>();
        listElementarySections = new ArrayList<String>();
        listAssets = new ArrayList<String>();
        listAssetId = new ArrayList<String>();



        listFacility.clear();
        listFacility.addAll(getFacility());
        Log.d(TAG,"facility size--" +listFacility.size());

//        facilityAdapter.notifyDataSetChanged();



        // Facility
        spFacility = (Spinner) findViewById(R.id.spFacility);
        facilityAdapter = new SpinnerFacilityAdapter(AsHistoryActivity.this,
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

                DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsHistoryActivity.this);
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

        assetTypesAdapter = new AsHistoryActivity.SpinnerAssetTypesAdapter(AsHistoryActivity.this,
                android.R.layout.simple_spinner_item, listAssetTypes);

        spAssetTypes.setAdapter(assetTypesAdapter);
        assetTypesAdapter.notifyDataSetChanged();

        spAssetTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {


                new Handler().post(new Runnable()
                {
                    public void run(){
                        aiAsync = new AIAsyncTask(AsHistoryActivity.this,getApplicationContext());
                        aiAsync.execute();
                    }
                });

                AssetType assetTypeObj = assetTypesAdapter.getItem(position);
                assetTypeId = assetTypeObj.getAssetTypeId();
                Log.d(TAG, "assetTypeId: " + assetTypeId);
                globals.setAssetType(assetTypeId);

                     listScheduleCodes.clear();
                     listScheduleCodes.addAll(getScheduleCodes(assetTypeId));
                     scheduleCodeAdapter.notifyDataSetChanged();

                   /*  listAssets.clear();
                     listAssets.addAll(getAsset());
                     assetAdapter.notifyDataSetChanged();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        // Schedule Codes
        scheduleCodeAdapter = new SpinnerScheduleCodeAdapter(AsHistoryActivity.this,
                android.R.layout.simple_spinner_item, listScheduleCodes);
        spScheduleCode.setAdapter(scheduleCodeAdapter);
        scheduleCodeAdapter.notifyDataSetChanged();


        spScheduleCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                scheduleCode = scheduleCodeAdapter.getItem(position);

                Log.d(TAG, "scheduleCode: " + scheduleCode);


                listAssetId.clear();
                //listAssetId.add(0,"Select Asset Id");
                listAssetId.addAll(getAsset());
                assetAdapter.notifyDataSetChanged();

                listAssets.clear();
                listAssets.addAll(getAsset());
                Log.d(TAG,"all assets added");
                assetAdapter.notifyDataSetChanged();


                getKiloMeter();

               /* listElementarySections.clear();
                 listElementarySections.addAll(getElementarySection());
                 elementarySectionAdapter.notifyDataSetChanged();*/

             /*   likeKm = getKiloMeter();
                if(likeKm != null || !likeKm.equals("")){

                    ArrayList<String> filter = new ArrayList<String>();
                    for(String ids: listAssetId){
                        if(ids.startsWith(likeKm)){
                            filter.add(ids);
                        }
                    }
                    Log.d(TAG,"Filter list size--"+filter.size());
                    listAssets.clear();
                    listAssets.addAll(filter);
                    Log.d(TAG,"filtered list added");
                    assetAdapter.notifyDataSetChanged();
                }
                else{
                    listAssets.clear();
                    listAssets.addAll(listAssetId);
                    Log.d(TAG,"all assets added");
                    assetAdapter.notifyDataSetChanged();
                }*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        // Asset Ids

        assetAdapter = new SpinnerAssetsAdapter(AsHistoryActivity.this,
                android.R.layout.simple_spinner_item, listAssets);
        assetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAssetId.setAdapter(assetAdapter);
        assetAdapter.notifyDataSetChanged();

        spAssetId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                assetId = assetAdapter.getItem(position);
                Log.d(TAG,"asset id:" +assetId);
                globals.setAssetId(assetId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSubmitHistory.setBackgroundColor(Color.parseColor("#FFDFE1DA")); // faded Grey
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        historyAsync = new HistoryAsyncTask(AsHistoryActivity.this,getApplicationContext());
                        historyAsync.execute();
                       btnSubmitHistory.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green
                    }
                });
            }
        });

           btnSubmitHistory.setBackgroundColor(Color.parseColor("#738b28")); //Original button color green

    }






    //Get Facility
    private ArrayList<String> getFacility(){
        ArrayList<String> facilityList =  null;
        try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsHistoryActivity.this);

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
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsHistoryActivity.this);

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
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AsHistoryActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            ReportsDAO reportsDAO = ReportsDAO.getInstance();

            scheduleCodesList =   reportsDAO.getScheduleCodes(assetTypeId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleCodesList;
    }


    // Get Assets

    private ArrayList<String> getAsset(){

        ArrayList<String> assetList = new ArrayList<>();
        assetList.add(0,"Select AssetId");
        Log.d(TAG,"get Assets method");
        try{
           // Log.d(TAG,"before:" +globals.getAssetList().size());
               if( globals.getAssetList() != null && globals.getAssetList().size() > 0 ) {
                   Log.d(TAG,"after");
                   for(int i = 0; i < globals.getAssetList().size() ; i++){
                       Log.d(TAG, " AI is --"+globals.getAssetList().get(i));
                       assetList.add(i+1, globals.getAssetList().get(i));
                   }
               }
               else{
                  // Toast.makeText(AsHistoryActivity.this,"No Asset Ids", Toast.LENGTH_SHORT).show();
                   Log.d(TAG,"No Asset Ids");
               }

        }catch(Exception e){
            e.printStackTrace();
        }
        return assetList;
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

                  //  globals.setKilolmeter(likeKm);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

    @Override
    public int getLayoutResource(){return R.layout.activity_as_history;}


}