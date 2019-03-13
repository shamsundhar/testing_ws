package trd.ams.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;

public class TitleBarActivity extends AppCompatActivity  {

    Toolbar toolbar;
    TextView mTitle;
    ImageView ivBack, ivForward, ivSettings;
    String TAG = "TitleBarActivity";
    private static final int RESULT_OK = 1;
    DatabaseHelper dbhelper;
    Context context;
    Globals globals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResource());
      //  setContentView(R.layout.activity_title_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = context;

        globals= (Globals) getApplication();



        // Title
        mTitle = (TextView) findViewById(R.id.title_depot);

      //  setTitle(R.string.app_name);


        // back
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // settings
        ivSettings = (ImageView) findViewById(R.id.iv_settings);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the settings functionality
                PopupMenu popupMenu = new PopupMenu(TitleBarActivity.this, v);
                setTheme(R.style.PopupMenu);

                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();

                //Set on click listener for the menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==  R.id.item_datasync){
                            //Toast.makeText(TitleBarActivity.this, "Data Sync test", Toast.LENGTH_LONG).show();


                            Intent intent =new Intent(getApplicationContext(),DataSyncActivity.class);
                            startActivity(intent);
                            finish();

                        }

                        if (item.getItemId() == R.id.item_terms){
                          //  Toast.makeText(TitleBarActivity.this, "Terms of Use", Toast.LENGTH_LONG).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(TitleBarActivity.this);
                            AlertDialog alertDialog = builder.create();

                            alertDialog
                                    .setTitle("TRD_AMS");
                            alertDialog.setMessage("You are authorized user by Railway Department to use this app and enter the data.Do not share this app with anyone.");
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {


                                        }

                                    });

                            alertDialog.show();


                        }

                        if (item.getItemId() == R.id.item_logout) {
                            /*setResult(RESULT_OK);
                            finish();*/
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }

                        if (item.getItemId() == R.id.item_report) {
                           // Toast.makeText(TitleBarActivity.this, "Table representation" , Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),ReportsActivity.class));

                        }

                        if (item.getItemId() == R.id.item_home) {

                            if(globals.getFacilityNameList()!=null) {
                                startActivity(new Intent(getApplicationContext(), AssetActivity.class));
                            }
                            else{
                              //  Toast.makeText(TitleBarActivity.this,"You have to login" , Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(TitleBarActivity.this);
                                AlertDialog alertDialog = builder.create();

                                alertDialog
                                        .setTitle("TRD_AMS");
                                alertDialog.setMessage("You are not logged in. To enter a schedule you have to login");
                                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                               startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            }

                                        });

                                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int whichButton) {
                                            }
                                        });
                                alertDialog.show();
                            }
                        }

                        if (item.getItemId() == R.id.item_exit) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TitleBarActivity.this);
                            AlertDialog alertDialog = builder.create();

                            alertDialog
                                    .setTitle("TRD_AMS");
                            alertDialog.setMessage("Do you want to exit the app now?");
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {

                                            //GoToPreviousActivity();
                                           // finish();
                                            finishAffinity();
                                            System.exit(0);

                                            // To Minimize
                                           TitleBarActivity.this.moveTaskToBack(true);

                                            /*Intent startMain = new Intent(Intent.ACTION_MAIN);
                                            startMain.addCategory(Intent.CATEGORY_HOME);
                                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(startMain);*/
                                        }

                                    });

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                        }
                                    });
                            alertDialog.show();

                          //  Toast.makeText(TitleBarActivity.this,"Exit button" , Toast.LENGTH_LONG).show();


                        }

                        if (item.getItemId() == R.id.item_reload) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TitleBarActivity.this);
                            AlertDialog alertDialog = builder.create();

                            alertDialog
                                    .setTitle("TRD_AMS");
                            alertDialog.setMessage("This will delete the entire data and restarts the sync procedure. It will take some time. Do you want to proceed?");
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {

                                            DatabaseHelper dbhelper = DatabaseHelper.getInstance(getApplicationContext());
                                            dbhelper.checkDatabaseTables();
                                            dbhelper.TruncateDatabaseTables();
                                            Log.d(TAG,"Deleting data from tables");
                                            dbhelper.checkDatabaseTables();

                                            globals.setReloadTime("31-01-1990 17:26:15.613");
                                          startActivity(new Intent(TitleBarActivity.this,DataSyncActivity.class));
                                        }

                                    });

                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                        }
                                    });
                            alertDialog.show();
                           // Toast.makeText(TitleBarActivity.this,"Goes to datasync page" , Toast.LENGTH_LONG).show();

                        }

                        if (item.getItemId() == R.id.item_local_db) {
                            startActivity(new Intent(getApplicationContext(),LocalDBStatusActivity.class));

                        }



                        return false;
                    }
                });
            }
        });




        // forward
        ivForward = (ImageView) findViewById(R.id.iv_forward);

       /*
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/

    }


    private void GoToPreviousActivity() {
        setResult(RESULT_OK);
        this.finish();
    }


    protected int getLayoutResource() {
        return R.layout.activity_title_bar;

    }
    protected void setupTitleBar(String title, boolean back, boolean forward, boolean settings) {

        setTitle(title);

        if (!back){

           hideBackIcon();
        }

        if (!forward){

            hideForwardIcon();
        }

        if (!settings){

            hideSettingsIcon();
        }

    }




    // Hide the Settings icon
    private void hideSettingsIcon() {
        ivSettings.setVisibility(View.INVISIBLE);
    }


    // Hide the back icon
    private void hideBackIcon() {
        ivBack.setVisibility(View.INVISIBLE);
    }

    // Hide the Forward icon
    private void hideForwardIcon() {
        ivForward.setVisibility(View.INVISIBLE);
    }

    // Set Title
    private void setTitle(String title) {
        try {
            if (title != null && title.length() != 0) {

                if (title.length() > 28) {
                    title = title.substring(0, 28);
                }


                mTitle.setText(title);

                Log.d(TAG, "setTitle : " + title);
            } else {

                mTitle.setText("");

            }
        } catch (Exception e){
            Log.e(TAG, "setTitle " + e.getMessage());

        }
    }



}
