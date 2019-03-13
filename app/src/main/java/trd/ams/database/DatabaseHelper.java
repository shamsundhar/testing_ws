package trd.ams.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteOpenHelper;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import trd.ams.R;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dbHandler;
    public static String DB_PATH = "";
    public static String DB_NAME = "trd_ams.sqlite";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase myDB;
    private Context context;
    private static String CREATE_DB_SCRIPT = "trd_ams_tables.txt";
    private static String INSERT_DATA_SCRIPT = "trd_ams_data.txt";
    private static String PASSWORD = "Wf@trd841$ams327";  //237,238
    String TAG = DatabaseHelper.class.getSimpleName();
    String VIEW1,VIEW2,VIEW3;
  /*  public SharedPreferences sharedPref;
    public String syncDT; */

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;


        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        this.context = context;
        SQLiteDatabase.loadLibs(context);

    }

    private void createInitialDatabase() {

        Log.d("FlowCheck", " Database is being created");

        try {
            File databaseFile = new java.io.File(DB_PATH + DB_NAME);

            myDB = SQLiteDatabase.openOrCreateDatabase(databaseFile, PASSWORD, null);

            String sqlCreateDB = IOUtils.toString(context.getAssets().open(CREATE_DB_SCRIPT), StandardCharsets.UTF_8);

            String[] sqldb = sqlCreateDB.split(";");

            for (int i = 0; i < sqldb.length-1; i++) {
                try {
                    Log.d(TAG, "sql**" + i + " " + sqldb[i]);

                    myDB.execSQL(sqldb[i]);


                } catch (Exception e) {
                    Log.e(TAG, "Exception in creating initial db : " + e.getMessage());

                }
            }
            Log.i(TAG,"Database creation is done");


            //  INSERTING DATA

           /* myDB.execSQL("insert into user_login(user_login_id, current_password, enabled) values(?, ?, ?)", new Object[]{"developer",
                  "developer", "Y"});

            String sqlInsertData = IOUtils.toString(context.getAssets().open(INSERT_DATA_SCRIPT), StandardCharsets.UTF_8);

            String[] sqldata = sqlInsertData.split(";");

            for (int i = 0; i < sqldata.length-1; i++) {
                try {
                      Log.e(TAG, "sql:  " + i + " " + sqldata[i]);

                    myDB.execSQL(sqldata[i]);


                } catch (Exception e) {
                    Log.e(TAG, "Exception in inserting data in initial db: " + e.getMessage());

                }
            }*/


              VIEW1 =  " CREATE  VIEW v_asset_monthly_targets AS SELECT amt.seq_id, amt.facility_id, fac.facility_name," +
                    " fac.facility_type_id, fac.depot_type, amt.schedule_type, amt.asset_type, amt.target_jan," +
                    " amt.target_feb,  amt.target_mar, amt.target_apr, amt.target_may, amt.target_june AS target_jun," +
                    " amt.target_july AS target_jul, amt.target_aug, amt.target_sep, amt.target_oct, amt.target_nov," +
                    " amt.target_dec, amt.target_jan + amt.target_feb + amt.target_mar + amt.target_apr + amt.target_may + amt.target_june + amt.target_july + amt.target_aug + amt.target_sep + amt.target_oct + amt.target_nov + amt.target_dec AS total_target_year," +
                    " amt.year,((amt.year|| '-' || (amt.year+ 1) % 100)) AS fy" +
                    " FROM asset_monthly_targets amt,facility fac  WHERE amt.facility_id = fac.facility_id; "   ;

              Log.d(TAG, "** VIEW1 create query**");


             VIEW2 = " CREATE   VIEW v_monthly_cum_targets AS " +
                     " SELECT asmt.seq_id, asmt.facility_id, asmt.facility_name, asmt.facility_type_id, asmt.depot_type," +
                     " asmt.schedule_type, asmt.asset_type, asmt.target_jan, asmt.target_feb, asmt.target_mar, asmt.target_apr," +
                     " asmt.target_may, asmt.target_jun, asmt.target_jul, asmt.target_aug, asmt.target_sep, asmt.target_oct," +
                     " asmt.target_nov, asmt.target_dec, asmt.target_apr AS cum_target_apr," +
                     " asmt.target_apr + asmt.target_may AS cum_target_may," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun AS cum_target_jun," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul AS cum_target_jul," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug AS cum_target_aug," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep AS cum_target_sep," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct AS cum_target_oct," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov AS cum_target_nov," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec AS cum_target_dec," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan AS cum_target_jan," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb AS cum_target_feb," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS cum_target_mar," +
                     " asmt.target_apr + asmt.target_may + asmt.target_jun + asmt.target_jul + asmt.target_aug + asmt.target_sep + asmt.target_oct + asmt.target_nov + asmt.target_dec + asmt.target_jan + asmt.target_feb + asmt.target_mar AS total_year_target," +
                     " asmt.year, asmt.fy  FROM v_asset_monthly_targets asmt";



            VIEW3 = " CREATE  VIEW  v_assets_schedule_history AS " +
                    " SELECT ash.seq_id, ash.asset_id, ash.asset_type, ash.schedule_code, ash.schedule_date," +
                    "strftime('%Y',ash.schedule_date) as year1," +
                    "strftime('%m',ash.schedule_date) as month1," +
                    "strftime('%W',ash.schedule_date) as week1," +
                    "case when strftime('%m',date('now')) <=3 then (strftime('%Y',date('now'))-1)||'-'|| (strftime('%Y',date('now'))%100)" +
                    "else (strftime('%Y',date('now'))) ||'-'|| ((strftime('%Y',date('now'))+1)%100) end as FY," +
                    "ash.status, ash.details_of_maint, ash.done_by, ash.initial_of_incharge, ash.remarks, ash.pb_operation_seq_id," +
                    "ash.facility_id, fac.facility_name, fac.facility_type_id, fac.depot_type "  +
                    " FROM assets_schedule_history ash, facility fac WHERE ash.facility_id = fac.facility_id";


            myDB.execSQL(VIEW1);
            Log.d(TAG, " VIEW1 is created ");

            myDB.execSQL(VIEW2);
            Log.d(TAG, " VIEW2 is created ");

            myDB.execSQL(VIEW3);
            Log.d(TAG, " VIEW3 is created ");





        } catch (Exception e) {

            Log.e(TAG, "Exception in  initial db 2: " + e.getMessage());

        }
        myDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("FlowCheck","In oC method");
        // TODO Auto-generated method stub

       
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


    }

    /**
     * This is static method. This method returns the class object if it is
     * null.
     *
     * @param context Interface to global information about an application
     *                environment. This is an abstract class whose implementation is
     *                provided by the Android system. It allows access to
     *                application-specific resources and classes, as well as
     *                up-calls for application-level operations such as launching
     *                activities, broadcasting and receiving intents, etc.
     * @return returns the class object.
     */
    public static DatabaseHelper getInstance(Context context) {

        if (dbHandler == null)

            dbHandler = new DatabaseHelper(context);

        return dbHandler;
    }


    @Override
    public synchronized void close() {
        if (myDB != null) {
            myDB.close();
        }
        super.close();
    }

    /***
     * Check if the database exist on device or not
     * @return
     */
    public boolean checkDataBase() {
        Log.d("FlowCheck","In checkDB method");
        SQLiteDatabase tempDB = null;

        try {

            String myPath = DB_PATH + DB_NAME;
            //  tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            tempDB = SQLiteDatabase.openDatabase(myPath, PASSWORD, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            //    Log.e(TAG, "Exception while checking db: "+e.getMessage());

        }


        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    /***
     * Copy database from source code assets to device
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        Log.d("FlowCheck","In  copyDB method");
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e(TAG, "Copy Database: " + e.getMessage());
        }
    }

    /***
     * Open database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        Log.d("FlowCheck","In  openDB method");
        String myPath = DB_PATH + DB_NAME;
        //  myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        myDB = SQLiteDatabase.openDatabase(myPath, PASSWORD, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This method provides access to data base in readable and writable mode.
     *
     * @param isWritable integer value passed, 1 for writable, 0 for readable.
     * @return access mode to data base.
     */
    public SQLiteDatabase getDBObject(int isWritable) {
        Log.d("FlowCheck","In  getobjDB method");

        return (isWritable == 1) ? this.getWritableDatabase(PASSWORD) : this
                .getReadableDatabase(PASSWORD);
//        return (isWritable == 1) ? this.getWritableDatabase() : this
  //              .getReadableDatabase(PASSWORD);


    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */

    public void deleteDatabase() throws IOException
    {
        context.deleteDatabase(DB_NAME);
        Log.i(TAG, "Deleted the database");
    }



    public void createDataBase() throws IOException {  // Wf@trd841$ams327
   /*    context.deleteDatabase(DB_NAME);
        Log.i(TAG, "Deleted the database");  */

        boolean dbExist = checkDataBase();

        Log.i(TAG,"DB is present**" +dbExist);
        if (dbExist) {
            Log.d("FlowCheck", "In Create Database() ::Database exists");

            checkDatabaseTables();

        } else {
            Log.d("FlowCheck", "Creating new Database");

            this.getReadableDatabase(PASSWORD);
           // this.getReadableDatabase(null);
            try {

                createInitialDatabase();
                //checkDatabaseTables();

                //    copyDataBase();
            } catch (Exception e) {
                Log.e(TAG, "Create Database: " + e.getMessage());
            }
        }


    }

    public void checkDatabaseTables() {
        Log.d("FlowCheck","In checktablesDB method");
      //  SQLiteDatabase checkDB = null;
        try {

            myDB= SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, PASSWORD, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor c = myDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' order by name asc", null);



            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {

                    if ( c.getString(0) != null) {
                        Cursor c1 = myDB.rawQuery(
                                "SELECT count(*) FROM " + c.getString(0), null);
                        c1.moveToFirst();

                        Log.d(TAG, "Table: " + c.getString(0) + " - " + c1.getInt(0) + " rows");

                        c1.close();
                    }
                    c.moveToNext();
                }

                c.close();
            }

        } catch (Exception e) {

            Log.e(TAG, "Exception in  checking tables: " + e.getMessage());

        }
     myDB.close();
    }

    public void TruncateDatabaseTables() {
        Log.d("FlowCheck","In truncatingDB method");
        //  SQLiteDatabase checkDB = null;
        try {

            myDB= SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, PASSWORD, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor c = myDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' order by name asc", null);



            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {

                    try {

                       if ( c.getString(0) != null) {

                           if (!(c.getString(0).equals("user_login") || c.getString(0).equals("device"))) {

                               myDB.rawExecSQL("delete from " + c.getString(0));

                           }
                       }
                    } catch (Exception e){
                        Log.e(TAG, "Exception in truncating table : " + c.getString(0) + " " + e.getMessage());

                    }
                    c.moveToNext();
                }
                c.close();
            }




        } catch (Exception e) {

            Log.e(TAG, "Exception in truncating tables: " + e.getMessage());

        }
        myDB.close();
    }
}
