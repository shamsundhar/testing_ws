package trd.ams.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.xmp.impl.Latin1Converter;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.dao.MeasureDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.LastRecord;
import trd.ams.util.Utils;

public class StatusActivity extends TitleBarActivity {
    Button btn_button;
    TextView mAssetId;
    Globals globals;

    TextInputEditText etDoneBy, etDetailsOfMaint, etRemarks, etIncharge;
    Spinner spEntryStatus;
    String measurementDate;
    String facilityId;
    String powerBlockId;
    String powerBlockName;
    String elementarySectionCode;
    String assetTypeId;
    String scheduleCode;
    String deviceId;
    String seqId;
    String depoId;


    String assetId;
    String userName;
    String facilityName;
    private String doneBy;
    private String detailsOfMaintenance;
    private String remarks;
    private String incharge;
    private String entryStatus;
    ArrayAdapter<CharSequence> entryStatusaAdapter;
  //  private static final int RESULT_OK = 1;

    SharedPreferences sharedPrefHR, getSharedPref;

    int TAKE_PHOTO_CODE = 11;
    int PICK_PHOTO_CODE = 12;
    String root = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).toString();
    File myDir = new File(root + "/saved_images");
    public static int count = 0;
    String fname;
    byte[] theBArray;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_status);


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        }
        deviceId = telephonyManager.getDeviceId(0).toString();

        Log.d(TAG," imei is******" +deviceId);

        mAssetId = (TextView) findViewById(R.id.tv_asset_id);

        globals = (Globals) getApplication();

        spEntryStatus = (Spinner) findViewById(R.id.spEntryStatus);

        userName = globals.getUserName();
        measurementDate = globals.getMeasurementDate();
       // facilityId = globals.getFacilityId();
        depoId = globals.getDepoId();
        //deviceId = globals.getGlobalIMEI();
       // Log.d(TAG,"imei is::::::" +globals.getGlobalIMEI());
       facilityName = globals.getDepoName();
        powerBlockId = globals.getPowerBlockId();
        powerBlockName = globals.getPowerBlockName();
        elementarySectionCode = globals.getElementarySectionCode();
        scheduleCode = globals.getScheduleCode();
        assetTypeId = globals.getAssetTypeId();
        assetId = globals.getAssetId();
        detailsOfMaintenance = globals.getDetailsOfMaint();
        remarks  = globals.getRemarks();
        incharge = globals.getIncharge();
        entryStatus = globals.getEntryStatus();


        entryStatusaAdapter = ArrayAdapter.createFromResource(this,
                R.array.EntryStatus, android.R.layout.simple_spinner_item);
        entryStatusaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEntryStatus.setAdapter(entryStatusaAdapter);

        spEntryStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {


                entryStatus = spEntryStatus.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        setupTitleBar(facilityName, true, true, true);

        mAssetId.setText( assetTypeId + " | " + scheduleCode + " | " + assetId );

        etDoneBy =  findViewById(R.id.etstatus_doneby);

        etDetailsOfMaint =  findViewById(R.id.etstatus_details);

        etRemarks        =  findViewById(R.id.etstatus_remarks);

        etIncharge       =  findViewById(R.id.etstatus_incharge);


        etDoneBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b)
                    hideSoftKeyBoard(findViewById(R.id.slayout));

            }

        });

        etDetailsOfMaint.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b)
                    hideSoftKeyBoard(findViewById(R.id.slayout));

            }

        });

        etRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b)
                    hideSoftKeyBoard(findViewById(R.id.slayout));

            }

        });

        etIncharge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (!b)
                    hideSoftKeyBoard(findViewById(R.id.slayout));

            }

        });



        LastRecord lastRecord = globals.getLastRecord();

        if (lastRecord != null ) {
            etDoneBy.setText(lastRecord.getDoneBy());
            etDetailsOfMaint.setText(lastRecord.getDetailsOfMaintenance());
            etRemarks.setText(lastRecord.getRemarks());
            etIncharge.setText(lastRecord.getIncharge());
        }
        btn_button = (Button) findViewById(R.id.btn_submit);
        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(StatusActivity.this, ResultActivity.class);
                startActivity(intent);*/
                try {
                    nextAction();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatusActivity.this, ResultActivity.class));
            }
        });

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatusActivity.this, ActivitiesActivity.class));
            }
        });

          //FOR IMAGE CAPTURE AND STORING IN EXTERNAL STORAGE
/*
        findViewById(R.id.btnCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        findViewById(R.id.btnFetch)
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {

                        // in onCreate or any event where your want the user to
                        // select a file
                        Intent intent = new Intent();
                        intent.setDataAndType(Uri.withAppendedPath(Uri.fromFile(myDir), "/saved_images"), "image/*");
                       // intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), PICK_PHOTO_CODE);
                    }
                });*/



    }

    // Check Measure / Activity Record exists
    private int checkAssetScheduleActivityRecordExists(String measurementDate, String depoId, String assetTypeId, String assetId, String scheduleCode) {


        int result = 0;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(StatusActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.checkAssetScheduleActivityRecordExists(measurementDate, depoId, assetTypeId, assetId, scheduleCode, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // Check History exists
    private int checkAssetsScheduleHistoryExists(String measurementDate, String depoId, String assetTypeId, String assetId, String scheduleCode) {


        int result = 0;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(StatusActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.checkAssetsScheduleHistoryExists(measurementDate, depoId, assetTypeId, assetId, scheduleCode, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




    void nextAction() throws UnsupportedEncodingException {

        ArrayList<String> aList = new ArrayList<String>();
        ArrayList<String> mList = globals.getMeasuresList();

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = df.format(cal.getTime());
        boolean result = false;

        if (checkAssetScheduleActivityRecordExists(measurementDate, depoId, assetTypeId, assetId, scheduleCode) == 0) {

// insert data
            String historyId = String.valueOf(Utils.getRandomNumber());

            ArrayList<String> valuesH = new ArrayList<>();

           // valuesH.add(historyId);
            valuesH.add(depoId);
           // valuesH.add(elementarySectionCode);
            valuesH.add(deviceId);
            valuesH.add(historyId);   //deviceSeqId
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date())); //deviceCreatedStamp
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date())); // deviceLastUpdatedStamp
            valuesH.add(assetTypeId);
            valuesH.add(assetId);
            valuesH.add(scheduleCode);
            valuesH.add(measurementDate); // schedule date
            valuesH.add(spEntryStatus.getSelectedItem().toString());   // status
            valuesH.add(etDetailsOfMaint.getText().toString());  // details of maintenance
            valuesH.add(etDoneBy.getText().toString());  // done by
            valuesH.add(etIncharge.getText().toString()); // incharge
            valuesH.add(etRemarks.getText().toString());  // remarks
            valuesH.add(powerBlockId);
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesH.add(userName);
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesH.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            String[] strValuesH = valuesH.toArray(new String[valuesH.size()]);
            Log.d(TAG, "valuesH Array: " + Arrays.toString(strValuesH));
            Log.d(TAG,"in If (Insert values)");


            //  JSONArray jsonArrayH = new JSONArray(Arrays.asList(valuesH));

            ArrayList<String> valuesR = new ArrayList<>();

            aList = globals.getActivitiesList();
            int aListSize = aList.size();

            for (int i = aListSize; i < 50; i++) {

                aList.add("");

            }

           // valuesR.add(String.valueOf(Utils.getRandomNumber()));
            valuesR.add(depoId);
            valuesR.add(assetTypeId);
            valuesR.add(assetId);
            valuesR.add(scheduleCode);
            valuesR.add(measurementDate);  //scheduleDate
            valuesR.add(measurementDate);  //actualScheduleDate
          //  valuesR.add(historyId);
            valuesR.add(spEntryStatus.getSelectedItem().toString());  //status
            valuesR.add(deviceId);
            valuesR.add(historyId);
            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date())); //deviceCreatedStamp
            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date())); //deviceLastUpdatedStamp

            valuesR.addAll(mList);
            valuesR.addAll(aList);
            valuesR.add(""); //make
            valuesR.add(""); //model
            valuesR.add(etDetailsOfMaint.getText().toString());  // details of maintenance
            valuesR.add(etDoneBy.getText().toString());  // done by
            valuesR.add(etIncharge.getText().toString()); // incharge
            valuesR.add(etRemarks.getText().toString());  // remarks

            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));  //createdOn
            valuesR.add(userName); //createdBy

            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
            valuesR.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));


            String[] strValuesR = valuesR.toArray(new String[valuesR.size()]);
            Log.d(TAG, "valuesR Array: " + Arrays.toString(strValuesR));


            ArrayList valuesC = new ArrayList<>();
            String contentDevSeqId = String.valueOf(Utils.getRandomNumber());

            theBArray = globals.getPicArray();

            if(theBArray != null) {
                Log.d(TAG, "theBArray size - " + theBArray.length);
                String picName = globals.getPicName();
                ByteBuffer buffers = globals.getBuffer();
                //  String strbuf = new String(buffers,"UTF-8");
                //  String str = new String(bArray,"UTF-8");
                String str = new String(theBArray);
                Log.d(TAG, "array to string size - " + str.length());

                // String str =  Base64.encodeToString(theBArray, Base64.DEFAULT);
                valuesC.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date())); // FROM DATE
                valuesC.add(""); //THRU DATE
                valuesC.add(deviceId);
                valuesC.add(contentDevSeqId);
                valuesC.add(historyId); // deviceAshSeqId
                valuesC.add(str); //CONTENT
                valuesC.add(picName);//FILE NAME OF IMAGE
                valuesC.add(userName); //User login id


                String[] strValuesC = (String[]) valuesC.toArray(new String[valuesC.size()]);
                Log.d(TAG, "valuesC Array: " + Arrays.toString(strValuesC));
            }

            result = insertMeasuresAndActivities(strValuesH, strValuesR /*,strValuesC*/);
            Log.d(TAG, "Result: " + result);




        } else {

// update
            ArrayList<String> valuesH = new ArrayList<>();
            valuesH.add(spEntryStatus.getSelectedItem().toString()); // status
            valuesH.add(etDetailsOfMaint.getText().toString());  // details of maintenance
            valuesH.add(etDoneBy.getText().toString()); // done by
            valuesH.add(etIncharge.getText().toString());   // in-charge
            valuesH.add(etRemarks.getText().toString());   // remarks
            valuesH.add(todayDate);  // last_updated_stamp
            valuesH.add(measurementDate); //schedule date
            valuesH.add(assetTypeId);
            valuesH.add(assetId);
            valuesH.add(scheduleCode);

            String[] strValuesH = valuesH.toArray(new String[valuesH.size()]);
            Log.d(TAG, "valuesH Array: " + Arrays.toString(strValuesH));
            Log.d(TAG,"in else (update values)");


            ArrayList<String> valuesR = new ArrayList<>();

            aList = globals.getActivitiesList();
            int aListSize = aList.size();

            for (int i = aListSize; i < 50; i++) {

                aList.add("");

            }


            valuesR.addAll(mList);
            valuesR.addAll(aList);
            valuesR.add(todayDate);
            valuesR.add(userName);
            valuesR.add(measurementDate);
            valuesR.add(depoId);
            valuesR.add(assetTypeId);
            valuesR.add(assetId);
            valuesR.add(scheduleCode);


            String[] strValuesR = valuesR.toArray(new String[valuesR.size()]);
            Log.d(TAG, "valuesR Array: " + Arrays.toString(strValuesR));


           /* ArrayList<String> valuesC =new ArrayList<>();

            valuesC.add(seqId);
            valuesC.add(contentId);*/

            result = updateMeasuresAndActivities(strValuesH, strValuesR);
            Log.d(TAG, "Result: " + result);


        }
        Intent intent = new Intent(StatusActivity.this, ResultActivity.class);

        intent.putExtra("result", result);

        startActivityForResult(intent, 4);


    }

    // Insert Measures and Activities
    private boolean insertMeasuresAndActivities(String[] valuesH, String[] valuesR/*, String[] valuesC*/) {

        boolean result = false;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(StatusActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.insertMeasuresAndActivities(valuesH, valuesR, /*valuesC,*/ db);
            Log.d(TAG, "entering data to local db");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // update Measures and Activities
    private boolean updateMeasuresAndActivities(String[] valuesH, String[] valuesR) {

        boolean result = false;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(StatusActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.updateMeasuresAndActivities(valuesH, valuesR, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
   protected void onPause() {
        super.onPause();
        globals.setDoneBy(etDoneBy.getText().toString());
        globals.setDetailsOfMaint(etDetailsOfMaint.getText().toString());
        globals.setRemarks(etRemarks.getText().toString());
        globals.setIncharge(etIncharge.getText().toString());
        globals.setEntryStatus(spEntryStatus.getSelectedItem().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   spMeasureStatus.setSelection();
        etDoneBy.setText(globals.getDoneBy());
        etDetailsOfMaint.setText(globals.getDetailsOfMaint());
        etRemarks.setText(globals.getRemarks());
        etIncharge.setText(globals.getIncharge());
        spEntryStatus.setSelection(entryStatusaAdapter.getPosition(globals.getEntryStatus()));
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


    private void takePhoto(){
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)) );
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }

    private File getTempFile(Context context){
        //it will return /sdcard/image.tmp
        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, "image.tmp");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(requestCode == 4 && resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK);
                finish();

            }else if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK) {

                final File file = getTempFile(this);
                try {
                    Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
                    // do whatever you want with the bitmap (Resize, Rename, Add To Gallery, etc)

                    saveImage(captureBmp);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Log.d("CameraDemo", "Pic saved-"+count);
            }
            else if(requestCode == PICK_PHOTO_CODE && resultCode == Activity.RESULT_OK) {

                Uri selectedImage = data.getData();
                 Log.d("IMAGE","**IMAGE is--"+selectedImage.toString());
                 Log.d("IMAGE","Uri of selected image --"+selectedImage);

                Toast.makeText(this,"Image is loading",Toast.LENGTH_SHORT).show();

                try (Cursor returnCursor = getContentResolver().query(selectedImage, null, null, null, null)) {
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    returnCursor.moveToFirst();
                       String uriFileName = returnCursor.getString(nameIndex);
                         long uriFileSize = returnCursor.getLong(sizeIndex);
                    Log.d(TAG, "file name is--" + returnCursor.getString(nameIndex));
                    Log.d(TAG, "file size is--" +uriFileSize);
                    globals.setPicName(uriFileName);
                }



                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);

                      ByteArrayOutputStream baos = new ByteArrayOutputStream();

                      bitmap.compress(Bitmap.CompressFormat.JPEG,0,baos);
                   byte[] bArray = baos.toByteArray();
                    Log.d(TAG,"barray Size--"+bArray.length);


                    ByteBuffer buf = ByteBuffer.wrap(bArray);
                   globals.setBuffer(buf);
                    globals.setPicArray(bArray);

                   /* Intent i = new Intent(this, ImageActivity.class);
                      i.putExtra("bArray",bArray);
                    startActivity(i);
                    Log.d("pick photo","bArray sent");*/

                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


               Intent i = new Intent(this, ImageActivity.class);
                i.putExtra("imageUri",selectedImage.toString());
                startActivity(i);
                Log.d("pick photo","pic picked");

            }
        }



    //  to store images into a particular folder which is saved_images and that folder images show in gallery immediately.

    private void saveImage(Bitmap finalBitmap) {

       /* String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");*/
        myDir.mkdirs();
          String date = new SimpleDateFormat("ddMMyyyy-HHmm").format(new Date());
        int n = 100;
        Random generator = new Random();
        n = generator.nextInt(n);
        fname = "Trd-" + date + "/"+n+".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("ExternalStorage", "Scanned " + path + ":");
                        Log.d("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_status;
    }

    public void hideSoftKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(android.app.Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}