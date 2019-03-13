package trd.ams.dao;

import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;


import trd.ams.common.Globals;
import trd.ams.dto.Activity;
import trd.ams.dto.AssetType;
import trd.ams.dto.LastRecord;
import trd.ams.dto.Measure;
import trd.ams.dto.PowerBlock;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class MeasureDAO {
    Globals globals;
    double vicinity;
    double device_latitude;
    double device_longitude;

    private static String TAG = "MeasureDAO";

    private static MeasureDAO measureDAO;

    public static MeasureDAO getInstance() {

        if (measureDAO == null)
            measureDAO = new MeasureDAO();

        return  measureDAO;
    }

    /**
     * Class Constructor
     */
    private MeasureDAO() {

    }


    public LastRecord getLastRecord(String depoId, SQLiteDatabase db) {

        String  facility[] = {depoId};
        LastRecord lastRecord = null;
        Cursor c = null;
        try {

            if (db != null) {

                String sql = "select pb_operation_seq_id, asset_type, schedule_code, status," +
                        " details_of_maint, done_by,initial_of_incharge, remarks from assets_schedule_history" +
                        " where facility_id = ? order by ROWID DESC LIMIT 1";


                c = db.rawQuery(sql, facility);

                if (c.moveToFirst()) {
                    lastRecord = new LastRecord();
                    lastRecord.setPbOperationSeqId(c.getString(0));
                    lastRecord.setAssetTypeId(c.getString(1));
                    lastRecord.setScheduleCode(c.getString(2));
                    lastRecord.setStatus(c.getString(3));
                    lastRecord.setDetailsOfMaintenance(c.getString(4));
                    lastRecord.setDoneBy(c.getString(5));
                    lastRecord.setIncharge(c.getString(6));
                    lastRecord.setRemarks(c.getString(7));

                }

                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getLastRecord:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }


        return lastRecord;

    }

  /*  public ArrayList<String> getDepo(SQLiteDatabase db){
        ArrayList<String> depoList = new ArrayList<>();
         //depoList.add("Select Depo");

         Cursor c = null;
         try{
             if(db != null){
                 String  sql = " SELECT facility_name FROM facility f, app_device_unit adu"+
                         " WHERE f.facility_id = adu.unit_id";
                 c = db.rawQuery(sql,null);
                 Log.d(TAG,"Depo query");

                 if(c.moveToFirst()){
                     while (!c.isAfterLast()){
                         depoList.add(c.getString(0));
                         c.moveToNext();
                     }
                 }
                 c.close();
                 db.close();
             }
         }catch (Exception e){
             e.printStackTrace();
             Log.e(TAG, "getDepo:" + e.getMessage());
             if (c!= null) {

                 c.close();
             }
             if (db != null && db.isOpen()){

                 db.close();
             }
         }
        Log.d(TAG, "depo size:" + depoList.size());
         return depoList;
    }*/





    public ArrayList<PowerBlock> getPowerBlocks(String measurementDate, String depoId, SQLiteDatabase db) {

        String  facility[] = {measurementDate,depoId};

        ArrayList<PowerBlock> PowerBlockList = new ArrayList<>();
        PowerBlock nonPowerBlock = new PowerBlock();
        nonPowerBlock.setPbOperationSeqId("NoPb");
        nonPowerBlock.setPowerBlockName("Non - Power Block");
        PowerBlockList.add(nonPowerBlock);
        Cursor c = null;
        try {

            if (db != null) {

                String sql = "select pb_operation_seq_id, pb_operation_seq_id||' ('||substr(created_date, 0, 11)||')'" +
                        " from power_blocks" +
                        " where substr(created_date,0,11)  = ? and facility_id = ? order by pb_operation_seq_id asc";


                c = db.rawQuery(sql, facility);

                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        PowerBlock powerBlock = new PowerBlock();

                        powerBlock.setPbOperationSeqId(c.getString(0));
                        powerBlock.setPowerBlockName(c.getString(1));
                        PowerBlockList.add(powerBlock);
                        c.moveToNext();
                    }
                }
                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getPowerBlocks:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "getPowerBlocks size:" + PowerBlockList.size());
        return PowerBlockList;

    }


    public ArrayList<String> getElementarySections(String depoId, String powerBlockId, SQLiteDatabase db) {

        String  facility[] = {depoId};
        String  powerBlock[] = {powerBlockId};
        ArrayList<String> elementarySectionList = new ArrayList<>();
        elementarySectionList.add("Select Elementary Section");

        String sql = "";

        Cursor c = null;
        try {

            if (db != null) {

                if (!powerBlockId.equals("NoPb")) {

                    sql = "select distinct elementary_section_code " +
                            " from power_blocks pb" +
                            " where pb.pb_operation_seq_id = ? order by elementary_section_code asc";

                    c = db.rawQuery(sql, powerBlock);

                } else {

                    sql = " select distinct elementary_section_code "+
                            " from elementary_sections " +
                            " where facility_id = ? order by  elementary_section_code asc";

                    c = db.rawQuery(sql, facility);
                }



                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {
                        elementarySectionList.add(c.getString(0));

                        c.moveToNext();
                    }
                }
                c.close();
                db.close();

            }
        } catch (Exception e) {

            Log.e(TAG, "getElementarySections:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "getAssets Id size:" + elementarySectionList.size());
        return elementarySectionList;

    }

    public ArrayList<AssetType> getAssetTypes(String depoId,SQLiteDatabase db) {


        ArrayList<AssetType> assetTypeList = new ArrayList<>();
        AssetType assetType0 = new AssetType();
        assetType0.setAssetTypeId("0");
        assetType0.setAssetTypeName("Select Asset Type");
        assetTypeList.add(assetType0);
        /*ArrayList<String> assetTypeList = new ArrayList<>();
        assetTypeList.add("Select Asset Type");*/

        String sql = "";

        String args[] = {depoId};

        Cursor c = null;
        try {

            if (db != null) {



            /*    sql = "select product_id, product_id " +
                        " from product where product_type_id like'%OHE%' order by product_id asc";*/

                    sql =  " select distinct  pc.product_id from product_category_member pc, facility f , app_device_unit adu "+
                " where f.facility_id = ?"+
                " and product_category_id  =  case when f.depot_type =  'OHE' then 'OHE_FIXED_ASSET' "+
                    " when f.depot_type =  'TSS' then 'PSI_FIXED_ASSET' "+
                    " when f.depot_type =  'SP' then 'PSI_FIXED_ASSET' "+
                    " when f.depot_type =  'SSP' then 'PSI_FIXED_ASSET' end " ;


             //    sql = " select product_id, product_id||'('||internal_name||')'from product where product_type_id in ('OHE_FIXED_ASSET','PSI_FIXED_ASSET' )order by product_id asc " ;

                /* sql = " select DISTINCT product_id, product_id||'('||internal_name||')'from product, facility where product_type_id  =  case when depot_type =  'OHE' then 'OHE_FIXED_ASSET' " +
                         " when depot_type =  'TSS' then 'PSI_FIXED_ASSET' "+
                         " when depot_type =  'SP' then 'PSI_FIXED_ASSET' "+
                         " when depot_type =  'SSP' then 'PSI_FIXED_ASSET' end " ;*/

                Log.d(TAG, "getAssettypes SQL:" + sql.toString());
                c = db.rawQuery(sql, args);

                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        AssetType assetType = new AssetType();
                        assetType.setAssetTypeId(c.getString(0));
                        assetType.setAssetTypeName(c.getString(0));

                        assetTypeList.add(assetType);

                        //  assetTypeList.add(c.getString(0));

                        c.moveToNext();
                    }
                }
                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getAssetTypes:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "getAsset type size:" + assetTypeList.size());
        return assetTypeList ;

    }

    public ArrayList<String> getScheduleCodes(String assetTypeId, SQLiteDatabase db) {

        String  assetType[] = {assetTypeId};

        ArrayList<String> scheduleList = new ArrayList<>();
        scheduleList.add("Select Schedule Code");
        String sql = "";
        Cursor c = null;
        try {

            if (db != null) {

                sql = "select distinct schedule_code" +
                        " from asset_schedule_assoc" +
                        " where asset_type = ? order by schedule_code asc";

                c = db.rawQuery(sql, assetType);



                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        scheduleList.add(c.getString(0));

                        c.moveToNext();
                    }
                }
                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getScheduleCodes:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "schedule codes size:" + scheduleList.size());
        return scheduleList;

    }


    public ArrayList<String> getGps(){

        ArrayList<String>gpsList = new ArrayList<>();
        gpsList.add("Select GPS ");
        gpsList.add("Yes");
        gpsList.add("No");

        Log.d(TAG,"gps size:" +gpsList.size());
        return gpsList;
    }


   /* public ArrayList<String> getKilometer(){
        ArrayList<String> likeKmList = new ArrayList<>();
        likeKmList.add("Select KiloMeter");

        return  likeKmList;
    }*/


    public ArrayList<String> getAssets(String depoId, String elementarySectionCode, String assetTypeId, String likeKm, String gps, String latplus, String latminus, String lonplus, String lonminus, SQLiteDatabase db)
    {


        String  assetType[] = {assetTypeId};
        ArrayList<String> paramList = new ArrayList<String>();

        ArrayList<String> assetList = new ArrayList<>();
        assetList.add("Select Asset Id");

        StringBuffer sql = new StringBuffer();

        Cursor c = null;
        try {

            if (db != null) {

                sql.append("select asset_id from asset_master_data where facility_id = ? and asset_type = ?");

                paramList.add(depoId);
                paramList.add(assetTypeId);
                Log.d(TAG," f & a are--" +depoId+" AT--"+assetTypeId);

                if (elementarySectionCode != null && elementarySectionCode.length() > 0 &&  !elementarySectionCode.equals("Select Elementary Section")) {

                    sql.append(" and elementary_section = ?");
                    paramList.add(elementarySectionCode);
                }


                if (likeKm != null && likeKm.length() > 0 &&  !likeKm.equals("Enter Kilometer") ) {
                    Log.d(TAG,"in likeKm query");

                    sql.append(" and asset_id like ?");
                    // sql.append("%");
                    paramList.add(likeKm+"%");
                }

                sql.append(" order by asset_id asc");

                if (gps != null && gps.length() > 0 &&  gps.equals("Yes")) {

                    Log.d(TAG,"gps is yes");

                    sql.delete(0,121);
                    // sql.append("Select ohe_mast from  ohe_location where latitude < '1727.2652' + '0.005' and latitude > '1727.2652' - '0.005' and longitude < '7838.6563' + '0.005' and longitude > '7838.6563' - '0.005'");

                    sql.append("select ohe_mast from ohe_location where latitude" +"<"+"? and latitude" +">"+ "? and longitude"+"<"+ "? and longitude" +">"+"?");

                    paramList.clear();
                    paramList.add(latplus);
                    paramList.add(latminus);
                    paramList.add(lonplus);
                    paramList.add(lonminus);

                    // paramList.add(gps);
                }


                Log.d(TAG, "getAssets SQL:" + sql.toString());

                c = db.rawQuery(sql.toString(), paramList.toArray(new String[paramList.size()]));

                //  Log.d(TAG, "Assets before cursor");


                if (c.moveToFirst()) {  // Log.d(TAG, "Assets After cursor");
                    while (!c.isAfterLast()) {
                        // Log.d(TAG, "assets after while");
                        assetList.add(c.getString(0));

                        c.moveToNext();
                    }
                }
                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getAssets:" + e.getMessage());
            if (c!= null) {

                c.close();
            }

            if (db!= null && db.isOpen()) {

                db.close();
            }


        }

        Log.d(TAG, "getAssets Id size:" + assetList.size());
        return assetList;

    }

    public ArrayList<Measure> getMeasures(String measurementDate, String depoId, String assetId, String assetTypeId, String scheduleCode, SQLiteDatabase db) {

        String  as[] = {assetTypeId, scheduleCode};

        String  as2[] = {depoId, assetTypeId, assetId, scheduleCode};

        ArrayList<Measure> measureList = new ArrayList<>();

        String sql = "";
        String sql2 = "";

        Cursor c = null;
        Cursor c2 = null;
        try {

            if (db != null) {


                sql =  " SELECT asa.asset_type, asa.schedule_code,asaa.activity_id,asaa.activity_position_id, mal.activity_name," +
                        " asaa.make_code, asaa.model_code,  asaa.lower_limit, asaa.upper_limit, asaa.description,asaa.seq_id,asaa.sub_asset_type," +
                        " asaa.make_code,asaa.asa_seq_id" +
                        " FROM asset_schedule_activity_assoc asaa, asset_schedule_assoc asa, measure_or_activity_list mal" +
                        " WHERE asaa.asa_seq_id = asa.asa_seq_id AND asaa.activity_id = mal.activity_id" +
                        " AND mal.activity_type = 'measurement'" +
                        " AND asa.asset_type = ? AND asa.schedule_code = ? ";


                c = db.rawQuery(sql, as);



                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        Measure measure = new Measure();
                        measure.setMeasureName(c.getString(4));
                        measure.setRequiredRange(c.getString(7) + " - " +  c.getString(8));
                        measure.setLowerLimit(c.getString(7));
                        measure.setUpperLimit(c.getString(8));

                        measureList.add(measure);

                        c.moveToNext();
                    }
                }
                c.close();


                sql2 =  " SELECT  m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22,"+
                        "m23, m24, m25, m26, m27, m28, m29, m30, m31, m32, m33, m34, m35, m36, m37, m38, m39, m40, m41, m42, " +
                        "m43, m44, m45, m46, m47,m48, m49, m50, substr(schedule_date, 0, 11)" +
                        " FROM asset_schedule_activity_record" +
                        " WHERE facility_id = ? AND asset_type = ? AND asset_id = ? AND schedule_code = ?  ORDER BY ROWID DESC LIMIT 4";

                c2 = db.rawQuery(sql2, as2);
                if (c2.moveToFirst()) {

                    if (c2.getString(50).equals(measurementDate)) {

                        Log.d(TAG, "getting measures: in first if");


                        for (int i = 0; i < measureList.size(); i++) {
                            measureList.get(i).setMeasureValue(c2.getString(i));
                        }

                    } else {

                        Log.d(TAG, " getting measures: in else");

                        for (int i = 0; i < measureList.size(); i++) {

                            if (c2.getString(i) != null && c2.getString(i).length() > 0)

                                measureList.get(i).setPreviousValues(c2.getString(i));

                        }

                    }

                    c2.moveToNext();


                    while (!c2.isAfterLast()) {

                        Log.d(TAG, "getting measures: in while");

                        for (int i = 0; i < measureList.size(); i++) {
                            measureList.get(i).setPreviousValues( measureList.get(i).getPreviousValues() + "     " +  c2.getString(i));
                        }


                        c2.moveToNext();
                    }
                }
                c2.close();
                db.close();

            }
        } catch (Exception e) {

            Log.e(TAG, "getMeasures:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (c2!= null) {

                c2.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "getMeasures - measure list:" + measureList.size());
        return measureList;

    }


    public ArrayList<Activity> getActivities(String measurementDate, String depoId, String assetId, String assetTypeId, String scheduleCode, SQLiteDatabase db) {

         String  as[] = {assetTypeId, scheduleCode};

      //  String  as[] = {};
        String  as2[] = {depoId, assetTypeId, assetId, scheduleCode};

        ArrayList<Activity> activityList = new ArrayList<>();

        String sql = "";
        String sql2 = "";

        Cursor c = null;
        Cursor c2 = null;
        try {

            if (db != null) {


                sql =  " SELECT asa.asset_type, asa.schedule_code,asaa.activity_id,asaa.activity_position_id, mal.activity_name," +
                        " asaa.make_code, asaa.model_code,  asaa.lower_limit, asaa.upper_limit, asaa.description,asaa.seq_id,asaa.sub_asset_type," +
                        " asaa.make_code,asaa.asa_seq_id" +
                        " FROM asset_schedule_activity_assoc asaa, asset_schedule_assoc asa, measure_or_activity_list mal" +
                        " WHERE asaa.asa_seq_id = asa.asa_seq_id AND asaa.activity_id = mal.activity_id" +
                        " AND mal.activity_type = 'activity'" +
                        " AND asa.asset_type = ? AND asa.schedule_code = ? ";



                c = db.rawQuery(sql, as);



                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        Activity activity = new Activity();
                        activity.setActivityName(c.getString(4));

                        activityList.add(activity);

                        c.moveToNext();
                    }
                }
                else{
                    Log.d(TAG,"NO activities");
                }
                c.close();

                sql2 =  " SELECT  a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, " +
                        "a23, a24, a25, a26, a27, a28, a29, a30, a31, a32, a33, a34, a35, a36, a37, a38, a39, a40, a41, a42, a43, a44, " +
                        " a45, a46, a47, a48, a49, a50, substr(schedule_date, 0, 11)" +
                        " FROM asset_schedule_activity_record" +
                        " WHERE facility_id = ? AND asset_type = ? AND asset_id = ? AND schedule_code = ?  ORDER BY ROWID DESC LIMIT 2";

                c2 = db.rawQuery(sql2, as2);
                if (c2.moveToFirst()) {

                    if (c2.getString(50).equals(measurementDate)) {


                        for (int i = 0; i < activityList.size(); i++) {
                            activityList.get(i).setActivityValue(c2.getString(i));
                        }

                    } else {

                        for (int i = 0; i < activityList.size(); i++) {

                            if (c2.getString(i) != null && c2.getString(i).length() > 0)

                                activityList.get(i).setPreviousRecord(c2.getString(i));

                        }

                    }

                    c2.moveToNext();


                    while (!c2.isAfterLast()) {

                        for (int i = 0; i < activityList.size(); i++) {
                            activityList.get(i).setPreviousRecord(c2.getString(i));
                        }


                        c2.moveToNext();
                    }
                }
                c2.close();
                db.close();

            }
        } catch (Exception e) {

            Log.e(TAG, "getActivities:" + e.getMessage());

            if (c!= null) {

                c.close();
            }

            if (c2!= null) {

                c2.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }
        Log.d(TAG, "getActivities - activity list:" + activityList.size());
        return activityList;

    }


    public boolean insertMeasuresAndActivities(String[] valuesH, String[] valuesR, /*String[] valuesC,*/ SQLiteDatabase db) {

        boolean result = false;
        String sqlH = "";
        String sqlR = "";
        String sqlC = "";
        SQLiteStatement stmt = null;

        try {

            if (db != null) {



                sqlH = "insert into assets_schedule_history (facility_id, device_id, device_seq_id,  device_created_stamp, device_last_updated_stamp, asset_type, asset_id, schedule_code, schedule_date, status," +
                        "details_of_maint, done_by,initial_of_incharge, remarks, pb_operation_seq_id, created_on, created_by, last_updated_stamp," +
                        "last_updated_tx_stamp, created_stamp, created_tx_stamp)" +
                        " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;

                stmt = db.compileStatement(sqlH);
                Log.d(TAG, " in measureDAO valuesH array****");

                for (int i=0; i < valuesH.length; i++) {

                    String value =  valuesH[i];

                    if (value == null) {

                        stmt.bindNull(i+1);
                    } else {

                        stmt.bindString(i+1, value);
                    }


                }


                if (stmt.executeInsert() > 0 ) {


                    sqlR = "insert into asset_schedule_activity_record (" +
                            "  facility_id, asset_type, asset_id, schedule_code, schedule_date, schedule_actual_date," +
                            "  status,device_id,device_seq_id, device_created_stamp, device_last_updated_stamp,  " +
                            " m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24, m25, m26, m27, m28, m29, m30, m31, m32, m33, m34, m35, m36, m37, m38, m39, m40, m41, m42, m43, m44, m45, m46, m47, m48, m49, m50," +
                            " a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30, a31, a32, a33, a34, a35, a36, a37, a38, a39, a40, a41, a42, a43, a44, a45, a46, a47, a48, a49, a50," +
                            " make, model, details_of_maint, done_by,initial_of_incharge, remarks, created_on, created_by, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp)" +
                            " values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?," +
                            " ?,?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    stmt = db.compileStatement(sqlR);
                    Log.d(TAG,"in measureDAO valuesR array******");

                    for (int i = 0; i < valuesR.length; i++) {



                        String value =  valuesR[i];

                        if (value == null) {
                            Log.d(TAG,"null********");

                            stmt.bindNull(i+1);

                        } else {
                            stmt.bindString(i+1, value);
                        }
                    }
                    Log.d(TAG,"ABOVE RESULT");
                    result = stmt.executeInsert() > 0;
                    Log.d(TAG,"BELOW RESULT********");

                }

               // if(stmt.executeInsert() > 0 ){

              /*  if(valuesR.length > 0) {

                    sqlC = "insert into asset_schedule_content (" +
                            " from_date,thru_date,device_id,device_seq_id,device_ash_seq_id,content,file_name,user_name)" +
                            " values (?,?,?,?,?,?,?,?)";

                    stmt = db.compileStatement(sqlC);
                    Log.d(TAG,"in measureDAO valuesC array******");

                    for (int i = 0; i < valuesC.length; i++) {



                        String valuec =  valuesC[i];

                        if (valuec == null) {
                            Log.d(TAG,"null********");

                            stmt.bindNull(i+1);

                        } else {
                            stmt.bindString(i+1, valuec);
                        }
                    }
                    Log.d(TAG,"ABOVE RESULT C");
                    result = stmt.executeInsert() > 0;
                    Log.d(TAG,"BELOW RESULT*** C *****");

                }*/

                db.close();

            }
        } catch (Exception e) {

            Log.e(TAG, "insertMeasuresAndActivities:" + e.getMessage());

            result = false;

            if (stmt != null ) {
                stmt.close();
            }

            if (db != null && db.isOpen()){

                db.close();
            }

        }

        return result;

    }

    public boolean updateMeasuresAndActivities(String[] valuesH, String[] valuesR, SQLiteDatabase db) {

        boolean result = false;
        String sqlH = "";
        String sqlR = "";
        SQLiteStatement stmt = null;

        try {

            if (db != null) {



                sqlH = "UPDATE assets_schedule_history SET  details_of_maint = ?,  done_by = ?, initial_of_incharge = ?,  " +
                        " remarks = ?, last_updated_stamp = ?" +
                        " WHERE substr(schedule_date, 0, 11) = ? and facility_id = ? and asset_type = ? and asset_id = ? and  schedule_code = ?" ;

                stmt = db.compileStatement(sqlH);
                Log.d(TAG,"in update valuesH array****");

                for (int i=0; i < valuesH.length; i++) {

                    String value =  valuesH[i];

                    if (value == null) {

                        stmt.bindNull(i+1);
                    } else {

                        stmt.bindString(i+1, value);
                    }

                }

                result = stmt.executeUpdateDelete() > 0;


                sqlR = "UPDATE asset_schedule_activity_record   SET " +
                        " m1 = ?, m2 = ?, m3 = ?, m4 = ?, m5 = ?, m6 = ?, m7 = ?, m8 = ?, m9 = ?, m10 = ?, m11 = ?, m12 = ?, m13 = ?, m14 = ?, m15 = ?, m16 = ?, m17 = ?, m18 = ?, m19 = ?, m20 = ?, m21 = ?, m22 = ?, m23 = ?, m24 = ?, m25 = ?," +
                        "m26 = ?, m27 = ?, m28 = ?, m29 = ?, m30 = ?, m31 = ?, m32 = ?, m33 = ?, m34 = ?, m35 = ?, m36 = ?, m37 = ?, m38 = ?, m39 = ?, m40 = ?, m41 = ?, m42 = ?, m43 = ?, m44 = ?, m45 = ?, m46 = ?, m47 = ?, m48 = ?, m49 = ?, m50 = ?," +
                        "a1 = ?, a2 = ?, a3 = ?, a4 = ?, a5 = ?, a6 = ?, a7 = ?, a8 = ?, a9 = ?, a10 = ?, a11 = ?, a12 = ?, a13 = ?, a14 = ?, a15 = ?, a16 = ?, a17 = ?, a18 = ?, a19 = ?, a20 = ?, a21 = ?, a22 = ?, a23 = ?, a24 = ?, a25 = ?," +
                        "a26 = ?, a27 = ?, a28 = ?, a29 = ?, a30 = ?, a31 = ?, a32 = ?, a33 = ?, a34 = ?, a35 = ?, a36 = ?, a37 = ?, a38 = ?, a39 = ?, a40 = ?, a41 = ?, a42 = ?, a43 = ?, a44 = ?, a45 = ?, a46 = ?, a47 = ?, a48 = ?, a49 = ?, a50 = ?, created_by = ?, last_updated_stamp = ?" +
                        " WHERE substr(schedule_date, 0, 11) = ? and facility_id = ? and asset_type = ? and asset_id = ? and schedule_code = ?" ;


                stmt = db.compileStatement(sqlR);
                Log.d(TAG,"in update valuesR array****");


                for (int i = 0; i < valuesR.length; i++) {

                    String value =  valuesR[i];

                    if (value == null) {

                        stmt.bindNull(i+1);

                    } else {

                        stmt.bindString(i+1, value);
                    }
                }

                result = stmt.executeUpdateDelete() > 0;

                db.close();
            }



        } catch (Exception e) {

            Log.e(TAG, "updateMeasuresAndActivities:" + e.getMessage());

            result = false;

            if (stmt != null ) {
                stmt.close();
            }

            if (db != null && db.isOpen()){

                db.close();
            }

        }

        return result;

    }

    public int checkAssetScheduleActivityRecordExists(String measurementDate, String depoId,  String assetTypeId, String assetId, String scheduleCode, SQLiteDatabase db) {

        int result = 0;
        try {

            String sql = "select 1 from asset_schedule_activity_record where substr(schedule_date, 0, 11) = ? and facility_id = ? and asset_type = ? and asset_id = ? and schedule_code = ?";

            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, measurementDate);

            stmt.bindString(2, depoId);

            stmt.bindString(3, assetTypeId);

            stmt.bindString(4, assetId);

            stmt.bindString(5, scheduleCode);

            Log.d(TAG,"just checking");
            result = Integer.parseInt(stmt.simpleQueryForString());


            stmt.execute();
            Log.d(TAG,"just ++++++++++++++++");
            db.close();

        } catch (Exception e) {

            Log.e(TAG, "checkAssetScheduleActivityRecordExists:" + e.getMessage());
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        return result;

    }

    public int checkAssetsScheduleHistoryExists(String measurementDate, String depoId,  String assetTypeId, String assetId, String scheduleCode, SQLiteDatabase db) {

        int result = 0;
        try {

            String sql = "select 1 from assets_schedule_history where substr(schedule_date, 0, 11) = ? and facility_id = ? and asset_type = ? and asset_id = ? and schedule_code = ?";

            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, measurementDate);

            stmt.bindString(2, depoId);

            stmt.bindString(3, assetTypeId);

            stmt.bindString(4, assetId);

            stmt.bindString(5, scheduleCode);


            result = Integer.parseInt(stmt.simpleQueryForString());

            db.close();

        } catch (Exception e) {

            Log.e(TAG, "checkAssetsScheduleHistoryExists:" + e.getMessage());
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        return result;

    }
}
