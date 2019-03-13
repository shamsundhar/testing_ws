package trd.ams.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import trd.ams.R;
import trd.ams.common.Globals;


public class ResultActivity extends TitleBarActivity {

    Button btnStartOver;
    Button btnLogout;
    TextView tvResultMessage;
    String facilityName;
    private static final int RESULT_OK = 1;
    boolean result;
    Globals globals;
    boolean back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_result);

      globals = (Globals)getApplication();



        btnStartOver = (Button)findViewById(R.id.btn_startover);
        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultActivity.this, AssetActivity.class);
                intent.putExtra("userName", globals.getUserName());

                intent.putExtra("facilityId", globals.getFacilityId());
                intent.putExtra("facilityName", globals.getFacilityName());
               // intent.putExtra("AssetType", globals.getAssetTypeId());
                //intent.putExtra("ScheduleCode", globals.getScheduleCode());
                intent.setFlags(Intent.FILL_IN_CATEGORIES);
                startActivity(intent);
                finish();
            }
        });


        btnLogout = (Button)findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
                //GoToPreviousActivity();

            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            result = extras.getBoolean("result");
        //   setTitle(facilityName);

        }

       tvResultMessage = (TextView)findViewById(R.id.tv_result_message);


      //  hideForwardIcon();

        if (result){

          back = false;

            tvResultMessage.setText(R.string.save_success_message);

        } else {

          back = true;
            tvResultMessage.setText( R.string.save_failed_message);

        }

        setupTitleBar(facilityName, back, false, true);

    }

    private void GoToPreviousActivity() {
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_result;
    }

   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alertDialog = builder.create();

            alertDialog
                    .setTitle("TRD_AMS");
            alertDialog.setMessage("Do you want the exit the app now?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {

                            GoToPreviousActivity();
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
}
