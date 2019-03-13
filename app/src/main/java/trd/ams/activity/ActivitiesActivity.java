package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.dao.MeasureDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.Activity;
import trd.ams.dto.Measure;
import trd.ams.util.Utils;

public class ActivitiesActivity extends TitleBarActivity {

    TextView mAssetId,space1,space2,space3,space4,space5,space6,space7,space8,space9,space10,space11,space12,tv_win;
    ImageView iv_win,iv_trd;
    LinearLayout mLayout,iLayout;
    Button btnSubmit;
    String measurementDate;
    String facilityId;
    String powerBlockId;
    String powerBlockName;
    String elementarySectionCode;
    String assetTypeId;
    String scheduleCode;
    String assetId;
    String userName;
    String facilityName;
    String lastSync;
    String TAG = "ActivitiesActivity";

    ArrayList<Activity> listActivities;
    TextInputEditText[] etActivityValue;
    private static final int RESULT_OK = 1;

    Globals globals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_activities);

        mAssetId = (TextView) findViewById(R.id.tv_asset_id);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitiesActivity.this, MeasuresActivity.class));
            }
        });
        // forward
        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextAction();
            }
        });

        globals = (Globals) getApplication();

        userName = globals.getUserName();
        measurementDate = globals.getMeasurementDate();
        facilityId = globals.getFacilityId();
        facilityName = globals.getFacilityName();
        powerBlockId = globals.getPowerBlockId();
        powerBlockName = globals.getPowerBlockName();
        elementarySectionCode = globals.getElementarySectionCode();
        scheduleCode = globals.getScheduleCode();
        assetTypeId = globals.getAssetTypeId();
        assetId = globals.getAssetId();
        lastSync = globals.getLastSyncTime();


        setupTitleBar(facilityName, true, true, true);


        mAssetId.setText( assetTypeId + " | " + scheduleCode + " | " + assetId );




        listActivities = new ArrayList<Activity>();
        listActivities.addAll(getActivities(measurementDate, facilityId, assetId, assetTypeId, scheduleCode));

        createViews(ActivitiesActivity.this, listActivities);

    }

    @Override
    protected void onPause(){
        super.onPause();
        ArrayList<String> aList = new ArrayList<String>();
        for (int i = 0; i < etActivityValue.length; i++) {


            String a = etActivityValue[i].getText().toString().trim();

            aList.add(a);

        }
        int aListSize = aList.size();

        for (int i = aListSize; i < 50; i++) {

            aList.add("");

        }
        globals.setActivitiesList(aList);

    }


    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<String>  aList = globals.getActivitiesList();
        if (aList != null && aList.size() > 0) {

            for (int i = 0; i < etActivityValue.length; i++) {


                etActivityValue[i].setText(aList.get(i));

            }

        }
    }









    @Override
    public int getLayoutResource() {
        return R.layout.activity_activities;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 3:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    // Get Activities
    private ArrayList<Activity> getActivities(String measurementDate, String facilityId, String assetId, String assetTypeId, String scheduleCode) {

        ArrayList<Activity> activityList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(ActivitiesActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            activityList = measureDAO.getActivities(measurementDate, facilityId, assetId, assetTypeId, scheduleCode, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return activityList;
    }


    // Check Record exists
    private int checkAssetScheduleActivityRecordExists(String measurementDate, String facilityId, String assetTypeId, String assetId, String scheduleCode){


        int result = 0;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(ActivitiesActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result =   measureDAO.checkAssetScheduleActivityRecordExists(measurementDate, facilityId, assetTypeId, assetId, scheduleCode, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    // Insert Measures and Activities
    private boolean insertMeasuresAndActivities(String[] valuesH, String[] valuesR/*, String[] valuesC*/) {

        boolean result = false;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(ActivitiesActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.insertMeasuresAndActivities(valuesH, valuesR,/* valuesC,*/ db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Update Measures and Activities
    private boolean updateMeasuresAndActivities(String[] valuesH, String[] valuesR) {

        boolean result = false;

        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(ActivitiesActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            result = measureDAO.updateMeasuresAndActivities(valuesH, valuesR, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private void createViews(Context context, ArrayList<Activity> activityList){

        mLayout = (LinearLayout) findViewById(R.id.ll_activities);
        iLayout = (LinearLayout) findViewById(R.id.ll_images);

        Log.d(TAG,"Activities page view");

        int listCount = activityList.size();

        LinearLayout.LayoutParams lpTextInputParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,   LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams lpTextViewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTextViewParams.setMarginEnd(20);
        lpTextViewParams.setMarginStart(20);


        LinearLayout.LayoutParams lpEditTextParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
        lpEditTextParams.setMarginEnd(20);
        lpEditTextParams.setMarginStart(20);

        LinearLayout.LayoutParams lpLineParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,    LinearLayout.LayoutParams.WRAP_CONTENT);
        lpLineParams.setMargins(0,20,0,20);

        LinearLayout.LayoutParams lpButtonParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
        lpButtonParams.setMargins(20, 20, 20, 20);

        LinearLayout.LayoutParams lpImageViewWFParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
        lpImageViewWFParams.setMargins(50, 50, 40, 2);


        LinearLayout.LayoutParams lpImageViewIRParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
        lpImageViewIRParams.setMargins(300, 50, 40, 2);



        TextInputLayout[] textInputLayout = new TextInputLayout[listCount];
        etActivityValue = new TextInputEditText[listCount];

        TextView[] tvPreviousRecord = new TextView[listCount];
        TextView[] tvLine = new TextView[listCount];


        for(int i=0; i<listCount; i++)
        {

            textInputLayout[i] = new TextInputLayout(context);
            textInputLayout[i].setLayoutParams(lpTextInputParams);


            etActivityValue[i] = new TextInputEditText(context);
            etActivityValue[i].setTextSize(18);
            etActivityValue[i].setText(activityList.get(i).getActivityValue());
            etActivityValue[i].setId(i);
            etActivityValue[i].setHint(activityList.get(i).getActivityName());
            etActivityValue[i].setLayoutParams(lpEditTextParams);
            etActivityValue[i].setMaxLines(1);
            etActivityValue[i].setSingleLine(true);
            etActivityValue[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {

                    if (!b)
                        hideSoftKeyBoard(findViewById(R.id.layout_activities));

                }

            });


            textInputLayout[i].addView(etActivityValue[i]);
            mLayout.addView(textInputLayout[i]);

            if (activityList.get(i).getPreviousRecord() != null && activityList.get(i).getPreviousRecord().length() > 0) {
                tvPreviousRecord[i] = new TextView(context);
                tvPreviousRecord[i].setTextSize(14);
                tvPreviousRecord[i].setLayoutParams(lpTextViewParams);
                tvPreviousRecord[i].setId(i);
                tvPreviousRecord[i].setText("Previous record: " + activityList.get(i).getPreviousRecord());
                mLayout.addView(tvPreviousRecord[i]);

            }


            tvLine[i] = new TextView(context);
            tvLine[i].setLayoutParams(lpLineParams);
            tvLine[i].setHeight(8);
            tvLine[i].setId(i);
            tvLine[i].setBackgroundColor(Color.parseColor("#738b28"));

            mLayout.addView( tvLine[i]);

        }


        space1 = new TextView(context);
        space1.setText("");
        space1.setLayoutParams(lpTextViewParams);
        mLayout.addView(space1);

        space2 = new TextView(context);
        space2.setText("");
        space2.setLayoutParams(lpTextViewParams);
        mLayout.addView(space2);

        space3 = new TextView(context);
        space3.setText("");
        space3.setLayoutParams(lpTextViewParams);
        mLayout.addView(space3);


        btnSubmit = new Button(context);
        btnSubmit.setText(R.string.next);
        btnSubmit.setTextSize(16);
        btnSubmit.setLayoutParams(lpButtonParams);
        btnSubmit.setBackgroundColor(Color.parseColor("#738b28"));
        btnSubmit.setTextColor(Color.parseColor("#FFFFFF"));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            nextAction();
            }
        });

        mLayout.addView(btnSubmit);


        space4 = new TextView(context);
        space4.setText("");
        space4.setLayoutParams(lpTextViewParams);
        mLayout.addView(space4);

        space5 = new TextView(context);
        space5.setText("");
        space5.setLayoutParams(lpTextViewParams);
        mLayout.addView(space5);

        tv_win = new TextView(context);
        tv_win.setText("             LastSync Date is : "+lastSync);
        tv_win.setTypeface(Typeface.DEFAULT_BOLD);
        tv_win.setLayoutParams(lpTextViewParams);
        tv_win.setTextColor(Color.BLACK);

        mLayout.addView(tv_win);



        space6 = new TextView(context);
        space6.setText("                             powered by WinFocus");
        space6.setTypeface(Typeface.DEFAULT_BOLD);
       // space6.setLayoutParams(params);
        space6.setTextColor(Color.BLACK);
        space6.setLayoutParams(lpTextViewParams);
        mLayout.addView(space6);

        iv_win = new ImageView(context);
        iv_win.setBackgroundResource(R.drawable.winfocus_logo);
        iv_win.setLayoutParams(lpImageViewWFParams);

        iLayout.addView(iv_win);


        iv_trd = new ImageView(context);
        iv_trd.setBackgroundResource(R.drawable.trd_logo);
        iv_trd.setLayoutParams(lpImageViewIRParams);

        iLayout.addView(iv_trd);

    }

 void nextAction() {

     ArrayList<String> aList = new ArrayList<String>();
     ArrayList<String> mList = globals.getMeasuresList();

     Calendar cal = Calendar.getInstance();

     SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
     String todayDate = df.format(cal.getTime());
     boolean result = false;

    if (checkAssetScheduleActivityRecordExists(measurementDate, facilityId, assetTypeId, assetId, scheduleCode)==0) {


    }
     Intent intent = new Intent(ActivitiesActivity.this, StatusActivity.class);

     intent.putExtra("result", result);

     startActivityForResult(intent, 3);


 }
    public void hideSoftKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(android.app.Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}