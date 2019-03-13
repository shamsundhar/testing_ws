package trd.ams.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;

import trd.ams.R;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;

public class LocalDBStatusActivity extends TitleBarActivity {

    Globals globals;
    String TAG = "LocalDBStatusActivity";
    String fac;
    TextView tvproduct,tvfacility,tvschedule,tv_asa,tv_mal,tv_asaa,tv_pcm,tv_ohe,tv_ash,tv_asar,tv_amd,tv_amt,tv_asu,tv_es,tv_pb,tv_ul;
    String sqlp,sqlf,sqls, sqlAsa, sqlMal, sqlAsaa, sqlPcm, sqlOhe, sqlAsh, sqlAsar, sqlAmd, sqlAmt, sqlAsu, sqlEs, sqlPb, sqlUl;
    Cursor cursor = null ;
    DatabaseHelper dbhelper = null;
    SQLiteDatabase database;
    int productNum;
    int facilityNum;
    int scheduleNum,asaNum,malNum,asaaNum,pcmNum,oheNum,ashNum,asarNum,amdNum,amtNum, asuNum,esNum,pbNum,ulNum ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globals = (Globals) getApplication();
        fac = globals.getFacilityName();

        if(fac != null) {
            setupTitleBar(fac, true, false, true);
        }
        else{
            setupTitleBar("TRD_AMS", true, false, true);

        }

        try {
            dbhelper = DatabaseHelper.getInstance(LocalDBStatusActivity.this);
           // dbhelper.createDataBase();
            database = dbhelper.getReadableDatabase("Wf@trd841$ams327");

        } catch (Exception e){

            Log.e(TAG, "accessing database - "+ e.getMessage());
        }

        try{
            if(database != null)
            {

                Log.d(TAG,"fetching each table count");
                sqlp = "select * from product" ;
                cursor = database.rawQuery(sqlp,null);
                productNum = cursor.getCount();
                //cursor.close();

                sqlf = "select * from facility";
                cursor = database.rawQuery(sqlf,null);
                facilityNum = cursor.getCount();
               // cursor.close();

                sqls = "select * from schedule";
                cursor = database.rawQuery(sqls,null);
                scheduleNum = cursor.getCount();

                sqlAsa = "select * from asset_schedule_assoc";
                cursor = database.rawQuery(sqlAsa,null);
                asaNum = cursor.getCount();

                sqlMal = "select * from measure_or_activity_list";
                cursor = database.rawQuery(sqlMal,null);
                malNum = cursor.getCount();

                sqlAsaa = "select * from asset_schedule_activity_assoc";
                cursor = database.rawQuery(sqlAsaa,null);
                asaaNum = cursor.getCount();

                sqlPcm = "select * from product_category_member";
                cursor = database.rawQuery(sqlPcm,null);
                pcmNum = cursor.getCount();

                sqlOhe = "select * from ohe_location";
                cursor = database.rawQuery(sqlOhe,null);
                oheNum = cursor.getCount();

                sqlAsh = "select * from assets_schedule_history";
                cursor = database.rawQuery(sqlAsh,null);
                ashNum = cursor.getCount();

                sqlAsar = "select * from asset_schedule_activity_record";
                cursor = database.rawQuery(sqlAsar,null);
                asarNum = cursor.getCount();

                sqlAmd = "select * from asset_master_data";
                cursor = database.rawQuery(sqlAmd,null);
                amdNum = cursor.getCount();

                sqlAmt = "select * from asset_monthly_targets";
                cursor = database.rawQuery(sqlAmt,null);
                amtNum = cursor.getCount();

                sqlAsu = "select * from asset_status_update";
                cursor = database.rawQuery(sqlAsu,null);
                asuNum = cursor.getCount();

                sqlEs = "select * from elementary_sections";
                cursor = database.rawQuery(sqlEs,null);
                esNum = cursor.getCount();

                sqlPb = "select * from power_blocks";
                cursor = database.rawQuery(sqlPb,null);
                pbNum = cursor.getCount();

                sqlUl = "select * from user_login";
                cursor = database.rawQuery(sqlUl,null);
                ulNum = cursor.getCount();

                cursor.close();
                database.close();

            }
        }catch(Exception e){
            e.printStackTrace();
        }


        tvproduct = findViewById(R.id.tvproduct);
        tvfacility = findViewById(R.id.tvfacility);
        tvschedule = findViewById(R.id.tvschedule);
        tv_asa = findViewById(R.id.tv_asa);
        tv_mal = findViewById(R.id.tv_mal);
        tv_asaa = findViewById(R.id.tv_asaa);
        tv_pcm = findViewById(R.id.tv_pcm);
        tv_ohe = findViewById(R.id.tv_ohe);
        tv_ash = findViewById(R.id.tv_ash);
        tv_asar = findViewById(R.id.tv_asar);
        tv_amd = findViewById(R.id.tv_amd);
        tv_amt = findViewById(R.id.tv_amt);
        tv_asu = findViewById(R.id.tv_asu);
        tv_es = findViewById(R.id.tv_es);
        tv_pb = findViewById(R.id.tv_pb);
        tv_ul = findViewById(R.id.tv_ul);

        tvproduct.setText("PRO - " +productNum);
        tvfacility.setText("FAC - " +facilityNum);
        tvschedule.setText("SCH - " +scheduleNum);
        tv_asa.setText("ASA - " +asaNum);
        tv_mal.setText("MAL - " +malNum);
        tv_asaa.setText("ASAA - " +asaaNum);
        tv_pcm.setText("PCM - " +pcmNum);
        tv_ohe.setText("OL - " +oheNum);
        tv_ash.setText("ASH - " +ashNum);
        tv_asar.setText("ASAR - " +asarNum);
        tv_amd.setText("AMD - " +amdNum);
        tv_amt.setText("AMT - " +amtNum);
        tv_asu.setText("ASU - " +asuNum);
        tv_es.setText("ES - " +esNum);
        tv_pb.setText("PB - " +pbNum);
        tv_ul.setText("UL - " +ulNum);


    }

    @Override
    public int getLayoutResource(){return R.layout.activity_local_db_status;}
}
