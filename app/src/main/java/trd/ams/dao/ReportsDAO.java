package trd.ams.dao;

import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import trd.ams.common.Globals;
import trd.ams.dto.Activity;
import trd.ams.dto.AssetType;

public class ReportsDAO {

    private static String TAG = "ReportsDAO";
    private static ReportsDAO reportsDAO;
    private String facilityName;


    public static ReportsDAO getInstance(){
        if(reportsDAO == null)
            reportsDAO = new ReportsDAO();
        return  reportsDAO;
    }

    /**
     * Class Constructor
     */
    private ReportsDAO(){

    }


    public ArrayList<String> getFacility( SQLiteDatabase db) {

        ArrayList<String> facilityList = new ArrayList<>();
        facilityList.add("Select Depot ");
        String sql = "";
        Cursor c = null;

        try {

            if (db != null) {

                //sql = " select facility_name from facility f, asset_master_data amd WHERE f.facility_id = amd.facility_id";

               sql = " select facility_name from facility  where depot_type in ('TSS', 'SP', 'SSP', 'OHE') order by depot_type";



                c = db.rawQuery(sql, null);

                while (c.moveToNext()) {
                    facilityName = c.getString(0);
                    Log.d(TAG, "fac::" + facilityName);
                    facilityList.add(facilityName);
                }
                Log.d(TAG, "fac::" + facilityName);
                //facilityList.add(facilityName);



                /*if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        facilityList.add(c.getString(0));

                        c.moveToNext();
                    }
                }*/
                c.close();
                db.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "getFacility:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "Facility size:" + facilityList.size());
        return facilityList;
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

               /* sql = "select product_id, product_id||' ('||internal_name||')' " +
                        " from product where product_type_id like'%OHE%' order by product_id asc";
*/

                sql =  " select distinct pc.product_id, p.product_id from product_category_member pc, facility f ,  product p "+
                        " where f.facility_id =  ? "+
                        " and p.product_id = pc.product_id "+
                        " and product_category_id  =  case when f.depot_type =  'OHE' then 'OHE_FIXED_ASSET' "+
                        " when f.depot_type =  'TSS' then 'PSI_FIXED_ASSET' "+
                        " when f.depot_type =  'SP' then 'PSI_FIXED_ASSET' "+
                        " when f.depot_type =  'SSP' then 'PSI_FIXED_ASSET' end " ;


                // sql = " select product_id from product_category_member where product_category_id in( 'OHE_FIXED_ASSET' , 'PSI_FIXED_ASSET' , 'RCC_FIXED_ASSET' ) " ;

                // sql = " select product_id, product_id||' ('||internal_name||')'from product where product_type_id in ('OHE_FIXED_ASSET','PSI_FIXED_ASSET' )order by product_id asc " ;

                c = db.rawQuery(sql, args);

                if (c.moveToFirst()) {
                    while (!c.isAfterLast()) {

                        AssetType assetType = new AssetType();
                        assetType.setAssetTypeId(c.getString(0));
                        assetType.setAssetTypeName(c.getString(1));

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

        Log.d(TAG, "getAsset Type size:" + assetTypeList.size());
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

                sql = "select  schedule_code" +
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


    public ArrayList<String> getDates(String facilityId, String assetTypeId, String scheduleCode, String assetId, SQLiteDatabase db) {

        String params[] = {facilityId,assetTypeId,scheduleCode,assetId};
        ArrayList<String> datesList = new ArrayList<>();
        datesList.add("Select Date");

        String sql = "";
        Cursor c = null;

        try{

            if (db != null) {
                sql = "select schedule_date" +
                        " from asset_schedule_history " +
                        " where facility_id = ? and asset_type = ? and schedule_code = ? and asset_id = ?" ;

                c = db.rawQuery(sql, params);

                if(c.moveToFirst()){
                    while(!c.isAfterLast()){
                        datesList.add(c.getString(0));
                        c.moveToNext();
                    }
                }
            }
        } catch (Exception e) {

            Log.e(TAG, "getDates:" + e.getMessage());
            if (c!= null) {

                c.close();
            }
            if (db != null && db.isOpen()){

                db.close();
            }

        }

        Log.d(TAG, "Dates List size:" + datesList.size());
        return datesList;


    }

}
