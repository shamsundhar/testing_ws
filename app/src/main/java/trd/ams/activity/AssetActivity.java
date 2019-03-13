package trd.ams.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLTransactionRollbackException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import trd.ams.BuildConfig;
import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.dao.MeasureDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetType;
import trd.ams.dto.Facility;
import trd.ams.dto.LastRecord;
import trd.ams.dto.Measure;
import trd.ams.dto.PowerBlock;


public class AssetActivity extends TitleBarActivity {

    Button btnNext1;
    TextView tvUserName;
    // Spinner spMeasurementDates;
    EditText spMeasurementDates;

    // Depot
    Spinner spDepo ;
    Spinner spPowerBlocks;
    Spinner spElementarySections;
    Spinner spAssetTypes;
    Spinner spScheduleCodes;
    Spinner spAssets;
    Spinner spGPS;

    Globals globals;
    String TAG = AssetActivity.class.getSimpleName();
    ArrayList<String> listDepo;
    ArrayList<PowerBlock> listPowerBlocks;
    ArrayList<String> listElementarySections;
    ArrayList<AssetType> listAssetTypes;
    ArrayList<String> listScheduleCodes;
    ArrayList<String> listGPS;
   // ArrayList<String> listKilometer;
    ArrayList<String> listAssets;
    ArrayList<String> listMeasurementDates;

    ArrayList<String> listfacilityName;
    ArrayList<String> listfacilityId;


    SpinnerDepoAdapter depoAdapter;
    SpinnerPowerBlocksAdapter powerBlocksAdapter;
    SpinnerElementarySectionsAdapter elementarySectionsAdapter;
    SpinnerAssetTypesAdapter assetTypesAdapter;
    SpinnerScheduleCodesAdapter scheduleCodesAdapter;
    SpinnerGPSAdapter GPSAdapter;
   // SpinnerKilometerAdapter KilometerAdapter;
    SpinnerAssetsAdapter assetsAdapter;



    String measurementDate;
    String depoName;
    String depoId;
    String facilityId;
    String powerBlockId;
    String powerBlockName;
    String elementarySectionCode;
    String assetTypeId;
    String scheduleCode;
    String assetId;
    String userName;
    String facilityName;
    LastRecord lastRecord;
    String gps;
    String kilometer;
    String likeKm;
    EditText etKilometer;
    TextInputLayout floatingKilometer;

    double device_latitude;
    double device_longitude;
    double vicinity = 0.025;
    double dlatplus;
    double dlatminus;
    double dlonplus;
    double dlonminus;
    String latplus;
    String latminus;
    String lonplus;
    String lonminus;
    String shortDate;

    Facility facility = null;

    @BindView(R.id.location_results)
    TextView txtLocationResult;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;


    private static final int RESULT_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_asset);
        globals = (Globals)getApplication();

        ButterKnife.bind(this);
        // initialize the necessary libraries
        init();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);

        startLocationButtonClick();

        listfacilityId = new ArrayList<>(globals.getFacilityIdList());
        listfacilityName = new ArrayList<>(globals.getFacilityNameList());


        floatingKilometer = (TextInputLayout) findViewById(R.id.username_text_input_layout);
        etKilometer = (EditText) findViewById(R.id.etKilometer);

        btnNext1 = (Button)findViewById(R.id.btn_next_1);
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (assetId != null && assetId.length()>0 && !assetId.equals("Select Asset Id")
                        && assetTypeId != null && assetTypeId.length()>0 && !assetTypeId.equals("Select Asset Type")
                       && scheduleCode != null && scheduleCode.length()>0 && !scheduleCode.equals("Select Schedule Code")){



                    //  if (checkAssetScheduleActivityRecordExists(measurementDate, assetId, scheduleCode) == 0 ) {

                    Intent intent = new Intent(AssetActivity.this, MeasuresActivity.class);


                    globals.setAssetId(assetId);
                    globals.setFacilityId(facilityId);
                    globals.setFacilityName(facilityName);
                    globals.setPowerBlockName(powerBlockName);
                    globals.setPowerBlockId(powerBlockId);
                    globals.setScheduleCode(scheduleCode);
                    globals.setUserName(userName);
                    globals.setMeasurementDate(measurementDate);
                    globals.setElementarySectionCode(elementarySectionCode);
                    globals.setAssetTypeId(assetTypeId);

                    startActivityForResult(intent, 1);

                    //  } else {

                    //     toastMsg("Measures Data already exist for the selected asset and date.");

                    //     }

                } else {

                    toastMsg("Asset Type, Schedule Code and Asset Id are mandatory.");

                }

            }
        });



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userName = extras.getString("userName");
           // facilityId = extras.getString("facilityId");
           // facilityName = extras.getString("facilityName");
          //  Toast.makeText(AssetActivity.this, "AssetType:"+extras.getString("AssetType")+", "+extras.getString("ScheduleCode"), Toast.LENGTH_LONG);
            setupTitleBar("TRD_AMS", false, false, true);
            tvUserName = (TextView)findViewById(R.id.tvUserName);

            tvUserName.setText( userName);

            globals.setFacilityName(facilityName);
            globals.setFacilityId(facilityId);
            globals.setUserName(userName);

        }




        listMeasurementDates = new ArrayList<String>();
        listDepo = new ArrayList<String>();
        listElementarySections = new ArrayList<String>();
        listAssetTypes = new ArrayList<AssetType>();
        listScheduleCodes = new ArrayList<String>();
        listAssets = new ArrayList<String>();
        listPowerBlocks = new ArrayList<PowerBlock>();
        listGPS = new ArrayList<>();
       // listKilometer = new ArrayList<>();



        lastRecord = getLastRecord(depoId);

        globals.setLastRecord(lastRecord);

        spMeasurementDates=(EditText) findViewById(R.id.spMeasurementDate);
       // measurementDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
         measurementDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Calendar.getInstance().getTime());
            shortDate =  new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());

        spMeasurementDates.setText(shortDate);
        globals.setMeasurementDate( measurementDate);



        listDepo.clear();
        listDepo.add(0,"Select Depo");
        listDepo.addAll(listfacilityName);
        Log.d(TAG,"Depo size--" +listDepo.size());



        //  getCurrentDateData();


        spMeasurementDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String days="";
                        String months=""+month;
                        final Calendar c = Calendar.getInstance();
                        int mHour = c.get(Calendar.HOUR_OF_DAY);
                        int mMinute = c.get(Calendar.MINUTE);
                        int msec = c.get(Calendar.SECOND);

                        if(dayOfMonth < 10)
                            days = "0"+dayOfMonth;
                        else
                            days = ""+dayOfMonth;

                        if (String.valueOf(month).length() == 1) {

                           months = "0"+(month+1);
                        }

                        spMeasurementDates.setText(days+"-"+(months)+"-"+year);
                        String mDate = year+"-"+(months)+"-"+days+"   "+mHour+":"+mMinute+":"+msec ;

                        measurementDate = mDate;

                        Log.d(TAG, "measurementDate: " + measurementDate);
                        globals.setMeasurementDate( measurementDate);


                        listDepo.clear();
                        listDepo.add(0,"Select Depo");
                        listDepo.addAll(listfacilityName);
                        Log.d(TAG,"Depo size--" +listDepo.size());
                        if (lastRecord != null && lastRecord.getDepoId() != null && lastRecord.getDepoId().length()>0 ) {

                            // Log.d(TAG, "lastRecord ScheduleCode: " + lastRecord.getScheduleCode());
                            spDepo.setSelection(depoAdapter.getPosition(lastRecord.getDepoId()));
                        }



                        listPowerBlocks.clear();
                        listPowerBlocks.addAll(getPowerBlocks(measurementDate, depoId));
                        powerBlocksAdapter.notifyDataSetChanged();
                        if (lastRecord != null && lastRecord.getPbOperationSeqId() != null && lastRecord.getPbOperationSeqId().length()>0 ) {

                            Log.d(TAG, "lastRecord powerblock Id: " + lastRecord.getPbOperationSeqId());

                            spPowerBlocks.setSelection(powerBlocksAdapter.getPosition(lastRecord.getPbOperationSeqId()));
                        }
                    }
                };

                Calendar day5 = Calendar.getInstance();
                day5.add(Calendar.DAY_OF_YEAR, -10);

                Calendar c1 = Calendar.getInstance();
                DatePickerDialog dpd =
                        new DatePickerDialog(AssetActivity.this,listener,c1.get(Calendar.YEAR),c1.get(Calendar.MONTH),c1.get(Calendar.HOUR_OF_DAY));
                dpd.getDatePicker().setMinDate(day5.getTimeInMillis());
                dpd.getDatePicker().setMaxDate(c1.getTimeInMillis());

                dpd.show();



            }
        });
                   // Depo

          spDepo = (Spinner)findViewById(R.id.spDepot);

        depoAdapter = new SpinnerDepoAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item,listDepo);
        spDepo.setAdapter(depoAdapter);
        depoAdapter.notifyDataSetChanged();

        spDepo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                depoName = depoAdapter.getItem(position);
                Log.d(TAG,"depo: " + depoName);
                globals.setDepoName(depoName);
                String arg[] = {depoName};

                DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);
                SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");

                try{
                    if(db!= null && depoName != "Select Depo"){

                        String sql = "SELECT facility_id from facility  WHERE facility_name = ?";
                        Cursor c = db.rawQuery(sql, arg);

                        if(c != null && c.moveToFirst()){

                            depoId = c.getString(c.getColumnIndex("facility_id"));
                            Log.d(TAG,"depoId--"+depoId);
                            globals.setDepoName(depoName);
                            globals.setDepoId(depoId);
                        }
                        c.close();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                listPowerBlocks.clear();
                listPowerBlocks.addAll(getPowerBlocks(measurementDate, depoId));
                powerBlocksAdapter.notifyDataSetChanged();

                listAssetTypes.clear();
                listAssetTypes.addAll(getAssetTypes(depoId));
                assetTypesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        // Power Blocks
        spPowerBlocks = (Spinner) findViewById(R.id.spPowerBlock);

        powerBlocksAdapter = new SpinnerPowerBlocksAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item, listPowerBlocks);

        powerBlocksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPowerBlocks.setAdapter(powerBlocksAdapter);
       // powerBlocksAdapter.notifyDataSetChanged();
        getCurrentDateData();

        spPowerBlocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {

                PowerBlock powerBlockObj = powerBlocksAdapter.getItem(position);
                powerBlockId = powerBlockObj.getPbOperationSeqId();
                powerBlockName = powerBlockObj.getPowerBlockName();
              //  Log.d(TAG, "powerBlockName: " + powerBlockName);

                listElementarySections.clear();
                listElementarySections.addAll(getElementarySections(depoId, powerBlockId));
                elementarySectionsAdapter.notifyDataSetChanged();
                if (lastRecord != null && lastRecord.getElementarySectionCode() != null && lastRecord.getElementarySectionCode().length()>0 ) {

                    spElementarySections.setSelection(elementarySectionsAdapter.getPosition(lastRecord.getElementarySectionCode()));

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // Elementary Sections
        spElementarySections = (Spinner) findViewById(R.id.spElementarySection);

        elementarySectionsAdapter = new SpinnerElementarySectionsAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item, listElementarySections);

        spElementarySections.setAdapter( elementarySectionsAdapter);

        elementarySectionsAdapter.notifyDataSetChanged();


        spElementarySections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                elementarySectionCode = elementarySectionsAdapter.getItem(position);

              //  Log.d(TAG, "elementarySectionCode: " + elementarySectionCode);

                listAssetTypes.clear();

                listAssetTypes.addAll(getAssetTypes(depoId));

                assetTypesAdapter.notifyDataSetChanged();


                if (lastRecord != null && lastRecord.getAssetTypeId() != null && lastRecord.getAssetTypeId().length()>0 ) {

                    spAssetTypes.setSelection(assetTypesAdapter.getPosition(lastRecord.getAssetTypeId()));

                    AssetType assetTypeObj = (AssetType) spAssetTypes.getSelectedItem();

                    assetTypeId = assetTypeObj.getAssetTypeId();
                }

                listAssets.clear();
                listAssets.addAll(getAssets(depoId, elementarySectionCode, assetTypeId, likeKm, gps, latplus,latminus,lonplus,lonminus));
                assetsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
// Asset Types
        spAssetTypes = (Spinner) findViewById(R.id.spAssetType);

        assetTypesAdapter = new SpinnerAssetTypesAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item, listAssetTypes);

        spAssetTypes.setAdapter( assetTypesAdapter);
        assetTypesAdapter.notifyDataSetChanged();

        spAssetTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                AssetType assetTypeObj = assetTypesAdapter.getItem(position);
                assetTypeId = assetTypeObj.getAssetTypeId();
                Log.d(TAG, "assetTypeId: " + assetTypeId);

                listScheduleCodes.clear();
                listScheduleCodes.addAll(getScheduleCodes(assetTypeId));
                scheduleCodesAdapter.notifyDataSetChanged();

                if (lastRecord != null && lastRecord.getScheduleCode() != null && lastRecord.getScheduleCode().length()>0 ) {

                   // Log.d(TAG, "lastRecord ScheduleCode: " + lastRecord.getScheduleCode());
                    spScheduleCodes.setSelection(scheduleCodesAdapter.getPosition(lastRecord.getScheduleCode()));
                }

              /* listAssets.clear();
               listAssets.addAll(getAssets(facilityId, elementarySectionCode, assetTypeId, kilometer,gps));
               assetsAdapter.notifyDataSetChanged();*/

                listAssets.clear();
                listAssets.addAll(getAssets(depoId, elementarySectionCode, assetTypeId, likeKm,gps,latplus,latminus,lonplus,lonminus));
                assetsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
// Schedule Codes
        spScheduleCodes = (Spinner) findViewById(R.id.spScheduleCode);

        scheduleCodesAdapter = new SpinnerScheduleCodesAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item, listScheduleCodes);

        spScheduleCodes.setAdapter( scheduleCodesAdapter);
        scheduleCodesAdapter.notifyDataSetChanged();


        spScheduleCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                scheduleCode = scheduleCodesAdapter.getItem(position);

               Log.d(TAG, "scheduleCode: " + scheduleCode);

                listGPS.clear();
                listGPS.addAll(getGps());
                GPSAdapter.notifyDataSetChanged();


                listAssets.clear();
                listAssets.addAll(getAssets(depoId, elementarySectionCode, assetTypeId, likeKm,gps,latplus,latminus,lonplus,lonminus));
                assetsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        // GPS

        spGPS = (Spinner) findViewById(R.id.spGPS);

        GPSAdapter = new SpinnerGPSAdapter (AssetActivity.this,android.R.layout.simple_spinner_item, listGPS);

        spGPS.setAdapter(GPSAdapter);
        GPSAdapter.notifyDataSetChanged();

        spGPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gps = GPSAdapter.getItem(position);
                Log.d(TAG,"GPS: " +gps);

                latplus = String.valueOf(dlatplus);
                latminus = String.valueOf(dlatminus);
                lonplus = String.valueOf(dlonplus);
                lonminus = String.valueOf(dlonminus);

                listAssets.clear();
                listAssets.addAll(getAssets(depoId, elementarySectionCode, assetTypeId, likeKm,gps,latplus,latminus,lonplus,lonminus));
                assetsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





// Assets
        spAssets = (Spinner) findViewById(R.id.spAsset);

        assetsAdapter = new SpinnerAssetsAdapter(AssetActivity.this,
                android.R.layout.simple_spinner_item, listAssets);

        spAssets.setAdapter(assetsAdapter);
        assetsAdapter.notifyDataSetChanged();

        spAssets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                assetId = assetsAdapter.getItem(position);


                Log.d(TAG, "assetId: " + assetId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


    }




    private void init() {
        Log.d(TAG,"in init method");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        Log.d(TAG,"in restore values");
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }

    private void updateLocationUI() {
        Log.d(TAG,"in update UI");
        if (mCurrentLocation != null) {
            txtLocationResult.setText("Lat: " + mCurrentLocation.getLatitude()+"\n"+"Lng: " + mCurrentLocation.getLongitude());
            txtLocationResult.setTextSize(18);

            device_latitude = mCurrentLocation.getLatitude();
            device_longitude = mCurrentLocation.getLongitude();

            dlatplus = device_latitude + vicinity;
            dlatminus = device_latitude + vicinity;
            dlonplus = device_longitude + vicinity;
            dlonminus = device_longitude - vicinity;


            globals.setDevice_latitude(device_latitude);
            globals.setDevice_longitude(device_longitude);
            globals.setVicinity(0.025);



            // giving a blink animation on TextView
            txtLocationResult.setAlpha(0);
            txtLocationResult.animate().alpha(1).setDuration(300);

            // location last updated time

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"in saved instance");

        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }


    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        Log.d(TAG,"in location updates");

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(AssetActivity.this,100);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(AssetActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    public void startLocationButtonClick() {
        Log.d(TAG,"start button click");

        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                    }

                    // @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"on activity result");
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;

        }


    }


    private void openSettings() {
        Log.d(TAG,"open settings");
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        Log.d(TAG,"in resume");
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {

        Log.d(TAG,"in check permissions");
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    protected void onPause() {
        Log.d(TAG,"on pause");
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }*/


    @Override
    public int getLayoutResource() {
        return R.layout.activity_asset;
    }

    // Get Last Record
    private LastRecord getLastRecord(String depoId){

        LastRecord lastRecord = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            lastRecord =   measureDAO.getLastRecord(depoId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastRecord;
    }

    // Get Depo

    private ArrayList<String> getDepo(){
        ArrayList<String> depoList = null;
        depoList.add("Select Depo");
       /* try{
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            depoList = measureDAO.getDepo(db);

        }catch (Exception e){
            e.printStackTrace();
        }*/
        return depoList;
    }




    // Get Power Blocks
    private ArrayList<PowerBlock> getPowerBlocks(String measurementDate, String depoId){

        ArrayList<PowerBlock> powerBlockList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            powerBlockList =   measureDAO.getPowerBlocks(measurementDate, depoId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return powerBlockList;
    }
    // Get Elementary Sections
    private ArrayList<String> getElementarySections(String depoId, String powerBlockId){

        ArrayList<String> elementarySectionList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            elementarySectionList =   measureDAO.getElementarySections(depoId, powerBlockId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementarySectionList;
    }

    // Get Asset Types
    private ArrayList<AssetType> getAssetTypes(String depoId){

        ArrayList<AssetType> assetTypeList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            assetTypeList =   measureDAO.getAssetTypes(depoId,db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assetTypeList;
    }

    // Get Schedule Codes
    private ArrayList<String> getScheduleCodes(String assetTypeId){

        ArrayList<String> scheduleCodesList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            scheduleCodesList =   measureDAO.getScheduleCodes(assetTypeId, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleCodesList;
    }

    //Get GPS

    private ArrayList<String> getGps() {

        ArrayList<String> gpsList = new ArrayList<>();
        try{
            MeasureDAO measureDAO = MeasureDAO.getInstance();
            gpsList = measureDAO.getGps();
            updateLocationUI();
            getKilometer();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return gpsList;
    }


    //Get Kilometer
      public String getKilometer(){


      floatingKilometer.getEditText().addTextChangedListener(new TextWatcher() {
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
              listAssets.clear();
              listAssets.addAll(getAssets(depoId, elementarySectionCode, assetTypeId,likeKm,gps,latplus,latminus,lonplus,lonminus));
              assetsAdapter.notifyDataSetChanged();
          }
      });

      return kilometer;

  }






    // Get Assets
    private ArrayList<String> getAssets(String depoId, String elementarySectionCode, String assetTypeId,String likeKm, String gps, String latplus, String latminus, String lonplus, String lonminus){

        ArrayList<String> assetList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(AssetActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            assetList =   measureDAO.getAssets(depoId, elementarySectionCode, assetTypeId, likeKm, gps,latplus,latminus,lonplus,lonminus, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assetList;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alertDialog = builder.create();

            alertDialog
                    .setTitle("TRD_AMS");
            alertDialog.setMessage("Do you want to exit the app now?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            finish();
                        }

                    });

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                        }
                    });
            alertDialog.show();



            return true;
        } else
            return super.onKeyDown(keyCode, event);

    }

    // Depot

    class SpinnerDepoAdapter extends ArrayAdapter<String> {


        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerDepoAdapter(Context context, int textViewResourceId,
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
            String depo = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(depo);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }

    // Power Blocks Adapter
    class SpinnerPowerBlocksAdapter extends ArrayAdapter<PowerBlock> {


        private Context context;
        private ArrayList<PowerBlock> values;
        LayoutInflater inflater;

        public SpinnerPowerBlocksAdapter(Context context, int textViewResourceId,
                                         ArrayList<PowerBlock> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return values.size();
        }

        public PowerBlock getItem(int position) {
            return values.get(position);
        }

        public long getItemId(int position) {
            return position;
        }


        public int getPosition(String powerblockId){
            int position = 0;
            Log.d(TAG, "getPosition - powerblockId  - " + powerblockId);
            for(int i = 0; i < values.size(); i++) {
                Log.d(TAG, "getPosition - powerblockId values - " + i +  " : " + values.get(i).getPbOperationSeqId());
                if (values.get(i).getPbOperationSeqId().equals(powerblockId)) {
                    position = i;
                    break;
                }
            }

          return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            PowerBlock itemObj = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);

            //  tvName.setText(itemObj.getPbOperationSeqId());
            tvName.setText(itemObj.getPowerBlockName());

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }
    }

    // Elementary Sections adapter
    class SpinnerElementarySectionsAdapter extends ArrayAdapter<String> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerElementarySectionsAdapter(Context context, int textViewResourceId,
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
    class SpinnerScheduleCodesAdapter extends ArrayAdapter<String> {


        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerScheduleCodesAdapter(Context context, int textViewResourceId,
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


    //GPS adapter

    class SpinnerGPSAdapter extends ArrayAdapter<String> {

        private Context context;

        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerGPSAdapter(Context context, int textViewResourceId,ArrayList<String> values){
            super(context,textViewResourceId,values);
            this.context = context;
            this.values = values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {return values.size();}

        public String getItem(int position) { return values.get(position); }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner,parent,false);
            String gps = values.get(position);
            TextView tvName = (TextView)itemView.findViewById(R.id.tv_name);
            tvName.setText(gps);

            return itemView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position,convertView,parent);
        }
    }


    //Kilometer Adapter

/*
    class SpinnerKilometerAdapter extends ArrayAdapter<String> {

        private Context context;
        private ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerKilometerAdapter(Context context,int textViewResourceId,ArrayList<String> values) {
            super(context,textViewResourceId,values);
            this.context = context;
            this.values =values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public int getCount() {return values.size();}

        @Nullable
        @Override
        public String getItem(int position) {
            return values.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = inflater.inflate(R.layout.measure_spinner, parent, false);
            String kilometer = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(kilometer);

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent) {
            return getView(position, convertView, parent);
        }

    }*/

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


    void toastMsg(String message) {
        Toast.makeText(AssetActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    void getCurrentDateData(){
        globals.setMeasurementDate(spMeasurementDates.getText().toString());
        //Toast.makeText(AssetActivity.this, "getCurrentDateData"+spMeasurementDates.getText().toString(), Toast.LENGTH_LONG).show();
        //listPowerBlocks.clear();
        listPowerBlocks.addAll(getPowerBlocks(spMeasurementDates.getText().toString(), facilityId));
        //powerBlocksAdapter.notifyDataSetChanged();

        powerBlocksAdapter.notifyDataSetChanged();
        if (lastRecord != null && lastRecord.getPbOperationSeqId() != null && lastRecord.getPbOperationSeqId().length()>0 ) {

            Log.d(TAG, "lastRecord powerblock Id: " + lastRecord.getPbOperationSeqId());

            spPowerBlocks.setSelection(powerBlocksAdapter.getPosition(lastRecord.getPbOperationSeqId()));
        }


    }
}
