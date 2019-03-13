package trd.ams.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.dao.MeasureDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.Measure;
import trd.ams.util.MeasureValidator;



public class MeasuresActivity extends TitleBarActivity  {


    TextView mAssetId, space1,space2,space3,space4,space5,space6,space7,space8,space9,space10,space11,space12,tv_win;
    ImageView iv_win,iv_trd;
    LinearLayout mLayout, iLayout;
    Button btnNext2;
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
    String depoId;
    String depoName;

    int i;
    Globals globals;
    TextInputEditText[] etMeasureValue;

    SQLiteDatabase db;

    String asset_model;
    String asset_make;

    ArrayList<Measure> listMeasures;
    private static final int RESULT_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_measures);

        mAssetId = (TextView) findViewById(R.id.tv_asset_id);


            // forward
        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               nextAction();
            }
        });
        //backward
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeasuresActivity.this,AssetActivity.class));

            }
        });




        globals = (Globals) getApplication();


        userName = globals.getUserName();
        measurementDate = globals.getMeasurementDate();
       // facilityId = globals.getFacilityId();
        //facilityName = globals.getFacilityName();
        powerBlockId = globals.getPowerBlockId();
        powerBlockName = globals.getPowerBlockName();
        elementarySectionCode = globals.getElementarySectionCode();
        scheduleCode = globals.getScheduleCode();
        assetTypeId = globals.getAssetTypeId();
        assetId = globals.getAssetId();
        lastSync = globals.getLastSyncTime();
        depoId = globals.getDepoId();
        depoName = globals.getDepoName();

        setupTitleBar(depoName, true, true, true);

        mAssetId.setText( assetTypeId + " | " + scheduleCode + " | " + assetId );
        Log.d(TAG,"displaying 3 entries");



    listMeasures =new ArrayList<Measure>();

        listMeasures.addAll(

    getMeasures(measurementDate, depoId, assetId, assetTypeId, scheduleCode/*, asset_make, asset_model*/));

    createViews(MeasuresActivity.this, listMeasures);

}

    @Override
    protected void onPause(){
        super.onPause();
        ArrayList<String> mList = new ArrayList<String>();
        for (int i = 0; i < etMeasureValue.length; i++) {


            String a = etMeasureValue[i].getText().toString().trim();
      //  if (a!= null && a.length() > 0) {
            mList.add(a);

       // } else {

       //     mList.add("");
       }

        int mListSize = mList.size();

        for (int i = mListSize; i < 50; i++) {

            mList.add("");

        }

        globals.setMeasuresList(mList);


    }


    @Override
    protected void onResume(){
        super.onResume();
        ArrayList<String> mList;
        mList = globals.getMeasuresList();

        if (mList != null && mList.size() > 0) {

            for (int i = 0; i < etMeasureValue.length; i++) {


                etMeasureValue[i].setText(mList.get(i));

            }

        }
    }




    @Override
    public int getLayoutResource() {
        return R.layout.activity_measures;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode ==  RESULT_OK ) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }




    // Get Measures
    private ArrayList<Measure> getMeasures(String measurementDate, String depoId, String assetId, String assetTypeId, String scheduleCode ){

        ArrayList<Measure> measureList = null;
        try {
            DatabaseHelper dbhelper = DatabaseHelper.getInstance(MeasuresActivity.this);

            SQLiteDatabase db = dbhelper.getDBObject(0);

            MeasureDAO measureDAO = MeasureDAO.getInstance();

            measureList =   measureDAO.getMeasures(measurementDate, depoId, assetId, assetTypeId, scheduleCode, db);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return measureList;
    }

   private void createViews(Context context, final ArrayList<Measure> measureList){

       mLayout = (LinearLayout) findViewById(R.id.ll_measures);
       iLayout = (LinearLayout) findViewById(R.id.ll_images);

       Log.d(TAG,"measures page view");

       int listCount = measureList.size();

       Log.d(TAG,"measures list-- "+measureList.size());

      LayoutParams lpTextInputParams = new LayoutParams( LayoutParams.MATCH_PARENT,   LayoutParams.WRAP_CONTENT);

       LinearLayout.LayoutParams lpTextViewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,   LinearLayout.LayoutParams.WRAP_CONTENT);
       lpTextViewParams.setMarginEnd(20);
       lpTextViewParams.setMarginStart(20);

       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       params.setMargins(0,0,0,25);

      LayoutParams lpEditTextParams = new LayoutParams( LayoutParams.MATCH_PARENT,   LayoutParams.WRAP_CONTENT);
      lpEditTextParams.setMarginEnd(20);
      lpEditTextParams.setMarginStart(20);

      LayoutParams lpLineParams= new LayoutParams( LayoutParams.MATCH_PARENT,    LayoutParams.WRAP_CONTENT);
      lpLineParams.setMargins(0,20,0,20);

       LayoutParams lpButtonParams = new LayoutParams( LayoutParams.MATCH_PARENT,   LayoutParams.WRAP_CONTENT);
       lpButtonParams.setMargins(20, 20, 20, 20);

       LayoutParams lpImageViewWFParams = new LayoutParams( LayoutParams.WRAP_CONTENT,   LayoutParams.WRAP_CONTENT);
       lpImageViewWFParams.setMargins(50, 50, 40, 2);


       LayoutParams lpImageViewIRParams = new LayoutParams( LayoutParams.WRAP_CONTENT,   LayoutParams.WRAP_CONTENT);
       lpImageViewIRParams.setMargins(300, 50, 40, 2);




       TextInputLayout[] textInputLayout = new TextInputLayout[listCount];
       etMeasureValue = new TextInputEditText[listCount];
       TextView[] tvRequiredRange = new TextView[listCount];
       TextView[] tvPreviousValue = new TextView[listCount];
       TextView[] tvLine = new TextView[listCount];


       for( i=0; i<listCount; i++)
       {
           textInputLayout[i] = new TextInputLayout(context);
           textInputLayout[i].setLayoutParams(lpTextInputParams);


           etMeasureValue[i] = new TextInputEditText(context);
           etMeasureValue[i].setTextSize(18);
           etMeasureValue[i].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
           etMeasureValue[i].setText( measureList.get(i).getMeasureValue());
           etMeasureValue[i].setText("");
           etMeasureValue[i].setId(i);
           etMeasureValue[i].setHint(measureList.get(i).getMeasureName());
           etMeasureValue[i].setLayoutParams(lpEditTextParams);
           etMeasureValue[i].setMaxLines(1);
           etMeasureValue[i].setSingleLine(true);
           etMeasureValue[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View view, boolean b) {

                   if (!b)
                       hideSoftKeyBoard(findViewById(R.id.layout_measures));

               }

           });
           etMeasureValue[i].setHorizontalScrollBarEnabled(true);


           etMeasureValue[i].addTextChangedListener(new MeasureValidator( etMeasureValue[i],  measureList.get(i).getLowerLimit(), measureList.get(i).getUpperLimit() ) {
               @Override public void validate(TextView textView, String text, String lowerLimit, String upperLimit) {


                   try {
                       if (text != null && text.length() > 0 && lowerLimit != null && lowerLimit.length() > 0 && upperLimit != null && upperLimit.length() > 0) {

                           if (Double.parseDouble(text) < Double.parseDouble(lowerLimit) || Double.parseDouble(text) > Double.parseDouble(upperLimit)) {

                               textView.setTextColor(Color.RED);

                           } else {

                               textView.setTextColor(Color.BLACK);
                           }

                       }
                   } catch (NumberFormatException e) {
                       e.printStackTrace();
                   }

               }
           });

           textInputLayout[i].addView(etMeasureValue[i]);
           mLayout.addView(textInputLayout[i]);

          if (measureList.get(i).getRequiredRange() != null && measureList.get(i).getRequiredRange().length() > 0) {
              tvRequiredRange[i] = new TextView(context);
              tvRequiredRange[i].setTextSize(14);
              tvRequiredRange[i].setLayoutParams(lpTextViewParams);
              tvRequiredRange[i].setId(i);
              tvRequiredRange[i].setText( "Required Range: " + measureList.get(i).getRequiredRange());
              mLayout.addView(tvRequiredRange[i]);

          }

           if (measureList.get(i).getPreviousValues() != null && measureList.get(i).getPreviousValues().length() > 0) {
               tvPreviousValue[i] = new TextView(context);
               tvPreviousValue[i].setTextSize(14);
               tvPreviousValue[i].setId(i);
               tvPreviousValue[i].setLayoutParams(lpTextViewParams);
               tvPreviousValue[i].setText( "Previous values: " + measureList.get(i).getPreviousValues());
               mLayout.addView(tvPreviousValue[i]);

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




       btnNext2 = new Button(context);
       btnNext2.setText(R.string.next);
       btnNext2.setTextSize(16);
       btnNext2.setLayoutParams(lpButtonParams);
       btnNext2.setBackgroundColor(Color.parseColor("#738b28"));
       btnNext2.setTextColor(Color.parseColor("#FFFFFF"));

       btnNext2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             nextAction();
           }
       });

       mLayout.addView(btnNext2);


       space11 = new TextView(context);
       space11.setText("");
       space11.setLayoutParams(lpTextViewParams);
       mLayout.addView(space11);

       space12 = new TextView(context);
       space12.setText("");
       space12.setLayoutParams(lpTextViewParams);
       mLayout.addView(space12);


       tv_win = new TextView(context);
       tv_win.setText("             LastSync Date is : "+lastSync);
       tv_win.setTypeface(Typeface.DEFAULT_BOLD);
       tv_win.setGravity(Gravity.CENTER);
       tv_win.setLayoutParams(params);
       tv_win.setTextColor(Color.BLACK);

       mLayout.addView(tv_win);

       space6 = new TextView(context);
       space6.setText("                              powered by WinFocus");
       space6.setTypeface(Typeface.DEFAULT_BOLD);
       space6.setLayoutParams(params);
       space6.setTextColor(Color.BLACK);
       // space6.setLayoutParams(lpTextViewParams);
       mLayout.addView(space6);

       space2 = new TextView(context);
       space2.setText("");
       space2.setLayoutParams(lpTextViewParams);
       mLayout.addView(space2);

       space3 = new TextView(context);
       space3.setText("");
       space3.setLayoutParams(lpTextViewParams);
       mLayout.addView(space3);

       space4 = new TextView(context);
       space4.setText("");
       space4.setLayoutParams(lpTextViewParams);
       mLayout.addView(space4);

       space5 = new TextView(context);
       space5.setText("");
       space5.setLayoutParams(lpTextViewParams);
       mLayout.addView(space5);

       space6 = new TextView(context);
       space6.setText("");
       space6.setLayoutParams(lpTextViewParams);
       mLayout.addView(space6);

       space7 = new TextView(context);
       space7.setText("");
       space7.setLayoutParams(lpTextViewParams);
       mLayout.addView(space7);

      /* space8 = new TextView(context);
       space8.setText("");
       space8.setLayoutParams(lpTextViewParams);
       mLayout.addView(space8);

       space9 = new TextView(context);
       space9.setText("");
       space9.setLayoutParams(lpTextViewParams);
       mLayout.addView(space9);

       space10 = new TextView(context);
       space10.setText("");
       space10.setLayoutParams(lpTextViewParams);
       mLayout.addView(space10);*/


       iv_win = new ImageView(context);
       iv_win.setBackgroundResource(R.drawable.winfocus_logo);
       iv_win.setLayoutParams(lpImageViewWFParams);

       iLayout.addView(iv_win);


       iv_trd = new ImageView(context);
       iv_trd.setBackgroundResource(R.drawable.trd_logo);
       iv_trd.setLayoutParams(lpImageViewIRParams);

       iLayout.addView(iv_trd);



    }

private void nextAction() {

ArrayList<String> mList = new ArrayList<String>();
    for (int i = 0; i < etMeasureValue.length; i++) {


        String m = etMeasureValue[i].getText().toString().trim();

        mList.add(m);

    }
    int mListSize = mList.size();

    for (int i = mListSize; i < 50; i++) {

        mList.add("");

    }

    globals.setMeasuresList(mList);

    Intent intent = new Intent(MeasuresActivity.this, ActivitiesActivity.class);

  //  overridePendingTransition(R.anim.grow_from_middle,R.anim.shrink_to_middle);

    startActivityForResult(intent, 2);


}
    public void hideSoftKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


}