package trd.ams.activity;

import android.app.Activity;
import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.common.util.Hex;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.dao.UserDAO;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.Facility;
import trd.ams.dto.FacilityDto;
import trd.ams.dto.Measure;
import trd.ams.dto.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends TitleBarActivity {


    ImageView ivBack, ivForward, ivSettings;
    Button btnLogin;
    TextView textView;
    AutoCompleteTextView actv_login;
    TextInputEditText etPassword;
    String IMEI1,IMEI2,operation,tablePassword,shaPassword, pickUser, lastSync;

    String sql;
    Cursor cursor = null ;
    Cursor cursors = null;

    ArrayList<String> userList = new ArrayList<>();

    DatabaseHelper dbhelper = null;
    SQLiteDatabase database;
    User user = new User();
    UserDAO userDAO = null;
    Facility facility = null;

    private static String TAG = "LoginActivity";
    Globals globals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globals = (Globals)getApplication();
        lastSync = globals.getLastSyncTime();
       // textView = new TextView(context);

     /*   if(globals.getFacilityName()!= null) {
            setupTitleBar(globals.getFacilityName(), false, false, true);
        }
        else{
            setupTitleBar("TRD_AMS",false,false,true);
        }*/

        setupTitleBar("TRD_AMS",false,false,true);

        Log.d(TAG,"facility name displaying as::" +globals.getFacilityName());

        try {
            dbhelper = DatabaseHelper.getInstance(LoginActivity.this);
            dbhelper.createDataBase();
           database = dbhelper.getReadableDatabase("Wf@trd841$ams327");

        } catch (Exception e){

            Log.e(TAG, "creating database - "+ e.getMessage());
        }


        //setContentView(R.layout.activity_login);


        SharedPreferences details = getSharedPreferences("PREFS",0);
        IMEI1 = details.getString("IMEI1", "");
        IMEI2 = details.getString("IMEI2", "");
        operation = details.getString("operation", "");



        btnLogin = (Button) findViewById(R.id.btnlogin);
        actv_login = (AutoCompleteTextView) findViewById(R.id.actv_login);

       // etUsername =  findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        textView = (TextView) findViewById(R.id.textViewLogin);
        String sync = lastSync.substring(0,19);
        Log.d(TAG,"subString-"+sync);

        textView.setText(" LastSync Date is : "+sync);


        try{
            if(database != null)
            {

                Log.d(TAG,"fetching user id");
                sql = "select user_login_id,current_password from user_login";
                cursor = database.rawQuery(sql,null);
                if(cursor.moveToFirst()){
                    while(! cursor.isAfterLast()){
                        userList.add(cursor.getString(0));
                        //userList.add(cursor.getString(1));
                        cursor.moveToNext();
                    }
                    Log.d(TAG,"done"+userList);
                }
                cursor.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userList);
        actv_login.setAdapter(adapter);
        Log.d(TAG,"added to actv");

        actv_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                actv_login.showDropDown();
                Log.d(TAG,"showing");
                actv_login.setTextColor(Color.BLACK);
                actv_login.setEnabled(false);
                return false;
            }
        });

             btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }



    // login function
   boolean login() {

        boolean result = true;
        String username = actv_login.getText().toString().replaceAll(" ", "");
        String password = etPassword.getText().toString().replaceAll(" ", "");
       // username = username.replaceAll("\\s", "");
        password = password.replaceAll("\\s", "");

        if(username.equals("")){
            actv_login.setError("user name should not be empty");
            actv_login.requestFocus();

            actv_login.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    actv_login.showDropDown();
                    Log.d(TAG,"showing");
                    actv_login.setError(null);
                    actv_login.setEnabled(false);
                    return false;
                }
            });

        }
        else {
            etPassword.requestFocus();
        }

        shaPassword = sha1(password);
        Log.d(TAG,"password after SHA--"+shaPassword);
       try{
           if(database != null)
           {

               Log.d(TAG,"fetching password");
               sql = "select current_password from user_login";
               cursors = database.rawQuery(sql,null);
               if(cursors.moveToFirst()){
                   while(! cursors.isAfterLast()){
                      tablePassword = cursors.getString(0);
                      Log.d(TAG,"password from userLogin table--"+tablePassword);
                       cursors.moveToNext();
                   }
                   Log.d(TAG,"done");
               }
               cursors.close();
           }
       }catch(Exception e){
           e.printStackTrace();
       }


       user.setPasswordUsed(password);
       user.setUserLoginId(username);

          if (isLoginSuccessful(user)) {

              if(shaPassword.equals(tablePassword) && !username.equals(""))
              {

                  globals.setFacilityNameList(facility.getFacilityNameList());
                  globals.setFacilityIdList(facility.getFacilityIdList());

                  Intent intent = new Intent(LoginActivity.this, AssetActivity.class);
                  //  intent.putExtra("userName",username);
                 /* intent.putExtra("facilityId", facility.getFacilityId());
                  intent.putExtra("facilityName", facility.getFacilityName());*/
                  intent.putExtra("username", username);

               //   Log.d(TAG, "facility - " +facility.getFacilityId());
                  startActivity(intent);
                  finish();
              }

              else if(username.equals("") && !shaPassword.equals(tablePassword) ) {

                  etPassword.setText("");
                  etPassword.requestFocus();
                  etPassword.setError("Enter correct password");


              }

              else if(!username.equals("") && !shaPassword.equals(tablePassword))
              {
                  Log.d(TAG,"password mismatch");
                  etPassword.setText("");
                  etPassword.requestFocus();
                  etPassword.setError("Enter correct password");

              }

              else if(username.equals("")) {
                  actv_login.setError("user name should not be empty");
                  actv_login.requestFocus();

                  actv_login.setOnTouchListener(new View.OnTouchListener() {
                      @Override
                      public boolean onTouch(View view, MotionEvent motionEvent) {
                          actv_login.showDropDown();
                          Log.d(TAG,"showing");
                          actv_login.setError(null);
                          actv_login.setEnabled(false);
                          return false;
                      }
                  });

              }


            /*  Intent intent = new Intent(LoginActivity.this, AssetActivity.class);
              //  intent.putExtra("userName",username);
              intent.putExtra("facilityId", facility.getFacilityId());
              intent.putExtra("facilityName", facility.getFacilityName());
              intent.putExtra("username", username);

              Log.d(TAG, "facility - " + facility.getFacilityId());
              startActivity(intent);
              finish();*/

          } else {

              toastMsg("Sign In failed. Please try again.");

          }



     return result;
    }


    private String sha1(String password)
    {
        String hash = null;

        try{

            MessageDigest md = MessageDigest.getInstance("SHA");
            byte [] bytes = password.getBytes("UTF-8");
              md.update(bytes,0,bytes.length);
              bytes = md.digest();
              StringBuilder sb = new StringBuilder();
              sb.append("{").append("SHA").append("}");
              for(byte b : bytes){
                  sb.append(String.format("%02x",b));
              }
              hash = sb.toString();

              Log.d(TAG,"encrypted is--"+hash);

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return hash;
    }



    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }


    void toastMsg(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    boolean  isLoginSuccessful(User user) {

      boolean  result = false;

      try {


          SQLiteDatabase db = dbhelper.getDBObject(1);

          UserDAO userDAO = UserDAO.getInstance();
          facility = userDAO.getUserFacility(user, db);


          if (facility != null ) {

              result = true;

              user.setSuccessfulLogin("Y");
              user.setHasLoggedOut("N");

          }

         /* user.setSuccessfulLogin("Y");
          user.setHasLoggedOut("N");*/

          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
          String timestamp = simpleDateFormat.format(new Date());
          user.setCreatedStamp(timestamp);
          user.setFromDate(timestamp);
          db = dbhelper.getDBObject(1);
          userDAO.insertLoginHistory(user, db);


      } catch (Exception e){

          Log.e(TAG, "isLoginSuccessful() - "+ e.getMessage());
      }


      return result;
    }


//    public void hideSoftKeyBoard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//    }


}





