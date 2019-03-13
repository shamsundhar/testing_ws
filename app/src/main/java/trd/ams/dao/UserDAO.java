package trd.ams.dao;

/**
 * Created by hanu on 30-10-2017.
 */


import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import trd.ams.dto.Facility;
import trd.ams.dto.User;

public class UserDAO {



    private static String TAG = "UserDAO";

    private static UserDAO userDAO;
    Cursor cursor = null ;
    Cursor cursors = null;
    SQLiteDatabase database;

    public static UserDAO getInstance() {

        if (userDAO == null)
            userDAO = new UserDAO();

        return userDAO;
    }

    /**
     * Class Constructor
     */
    private UserDAO() {

    }


    public boolean insertLoginHistory(User user, SQLiteDatabase dbObject) {

        boolean result = false;
        try {

            String sql = "INSERT INTO user_login_history (user_login_id, from_date, password_used, successful_login," +
                   " created_stamp ) VALUES (?,?,?,?,?)";

            SQLiteStatement stmt = dbObject.compileStatement(sql);

            stmt.bindString(1, user.getUserLoginId());


            stmt.bindString(2, user.getFromDate());

            stmt.bindString(3, user.getPasswordUsed());

            stmt.bindString(4, user.getSuccessfulLogin());

            stmt.bindString(5, user.getCreatedStamp());


            result = stmt.executeInsert() > 0 ;

        } catch (Exception e) {

            Log.e(TAG, "insertLoginHistory "+ e.getMessage());

        }

        if (dbObject != null && dbObject.isOpen()) {
            dbObject.close();
        }
        return result;
    }


    public int logIn(User user, SQLiteDatabase dbObject) {

       int result = 0;
        try {

            String sql = "select 1 from user_login where user_login_id = ? and current_password = ? and enabled = ?";

            SQLiteStatement stmt = dbObject.compileStatement(sql);

            stmt.bindString(1, user.getUserLoginId());

            stmt.bindString(2, user.getPasswordUsed());

            stmt.bindString(3, "Y");


            result = Integer.parseInt(stmt.simpleQueryForString());

        } catch (Exception e) {

            Log.e(TAG, "logIn:" + e.getMessage());

        }
        if (dbObject != null && dbObject.isOpen()) {
            dbObject.close();
        }
        return result;

    }

    public Facility getUserFacility(User user, SQLiteDatabase dbObject) {

       String facility = "";

       Facility facilityDTO = new Facility();
        try {

            if (dbObject != null) {
                Log.d(TAG,"Get Facility");

                ArrayList<String> facilityNameList = new ArrayList<>();
                ArrayList<String> facilityIdList = new ArrayList<>();

                Log.d(TAG,"Get Facility1");

          /*    String sql = "select f.facility_id || ':' || f.facility_name from facility f, user_login ul" +
                              " where ul.user_login_id = ? and ul.current_password = ? and ul.enabled = ?";*/

                String sql = " select f.facility_id from facility f,app_device_unit adu,user_login ul" +
                        " WHERE f.facility_id = adu.unit_id ";

                // select facility_id from facility  where depot_type in ('TSS', 'SP', 'SSP', 'OHE') order by depot_type
               /* SQLiteStatement stmt = dbObject.compileStatement(sql);
                Log.d(TAG,"Get Facility2");

                stmt.bindString(1, user.getUserLoginId());

                stmt.bindString(2, user.getPasswordUsed());

                stmt.bindString(3, "Y");

                String args[] = {user.getUserLoginId(),user.getPasswordUsed(),"Y"};*/
                Log.d(TAG,"Get Facility3");

                    cursor = dbObject.rawQuery(sql,null);

                    Log.d(TAG,"id list sql--"+sql);

                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            Log.d(TAG,"in loop");
                            facilityIdList.add(cursor.getString(0));
                            cursor.moveToNext();
                        }
                        Log.d(TAG, "done" + facilityIdList);
                    }
                    cursor.close();

                    facilityDTO.setFacilityIdList(facilityIdList);
                    Log.d(TAG, "in userDAO facility");
             //   }



                String sqlf = " select facility_name from facility f,app_device_unit adu" +
                        " WHERE f.facility_id = adu.unit_id ";


                cursors = dbObject.rawQuery(sqlf, null);
                Log.d(TAG,"name list sql--"+sqlf);
                if (cursors.moveToFirst()) {
                    while (!cursors.isAfterLast()) {
                        Log.d(TAG,"in next loop");
                        facilityNameList.add(cursors.getString(0));
                        //userList.add(cursor.getString(1));
                        cursors.moveToNext();
                    }
                    Log.d(TAG, "done" + facilityNameList);
                }
                cursors.close();
                facilityDTO.setFacilityNameList(facilityNameList);





           /* facilityDTO.setFacilityNameList(facilityNameList);
            facilityDTO.setFacilityIdList(facilityIdList);
*/
                 /*  facilityDTO.setFacilityId(facility.substring(0, facility.indexOf(":")));
                   facilityDTO.setFacilityName(facility.substring(facility.indexOf(":") + 1));*/

          //  Log.d(TAG, "in userDAO facility");
         /*      }
           } else {

               Log.d(TAG, "getUserFacility: dbObject is null");
           }*/
        }
              else {
                Log.d(TAG, "getUserFacility: dbObject is null");
            }
        } catch (Exception e) {

            Log.e(TAG, "getUserFacility:" + e.getMessage());

        }
        if (dbObject != null && dbObject.isOpen()) {
            dbObject.close();
        }
        return facilityDTO;

    }
}