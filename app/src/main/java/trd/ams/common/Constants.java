package trd.ams.common;

import org.apache.http.conn.scheme.HostNameResolver;

/**
 * Created by hanu on 20-01-2018.
 */

public class Constants {

  public static String HOST_URL = "http://122.175.41.15:8919";       // Madras
   // public static String HOST_URL = "http://203.153.46.190:8081";        // Sec- div

//  public static String HOST_URL = "http://112.133.211.116:8081"  // Chennai PUBLIC IP

    public static String MASTER_AUTHIMEI_URL= HOST_URL + "/warehouse/rest/device/auth";

    public static String MASTER_URL = HOST_URL + "/warehouse/rest/get-data";

    public static String MASTER_AUTH_URL = HOST_URL + "/warehouse/rest/user/auth";

    public static String FACILITY_URL = HOST_URL + "/warehouse/rest/get-facility-data";

    public static String POWER_BLOCKS_URL = HOST_URL + "/warehouse/rest/get-powerBlocks-data";

    public static String ELEMENTARY_SECTIONS_URL = HOST_URL + "/warehouse/rest/get-elementarySections-data";

    public static String SCHEDULE_URL = HOST_URL + "/warehouse/rest/get-schedule-data";

    public static String ASSET_MASTER_DATA_URL = HOST_URL + "/warehouse/rest/get-assetMasterData-data";

    public static String ASSET_SCHEDULE_ACTIVITY_ASSOC_URL = HOST_URL + "/warehouse/rest/get-assetScheduleActivityAssoc-data";

    public static String ASSET_SCHEDULE_ASSOC_URL = HOST_URL + "/warehouse/rest/get-assetScheduleAssoc-data";

    public static String USER_LOGIN_URL = HOST_URL + "/warehouse/rest/get-userLogin-data";

    public static String DEVICE_URL = HOST_URL + "/warehouse/rest/get-device-data";

    public static String PRODUCT_URL = HOST_URL + "/warehouse/rest/get-product-data";

    public static String PRODUCT_CATEGORY_MEMBER_URL = HOST_URL + "/warehouse/rest/get-productCategoryMember-data";

    public static String OHE_LOCATION_URL = HOST_URL + "/warehouse/rest/get-oheLocation-data";

    public static String MEASURE_OR_ACTIVITY_URL = HOST_URL + "/warehouse/rest/get-measureOrActivityList-data";

    public static String ASSETS_SCHEDULE_HISTORY_URL = HOST_URL + "/warehouse/rest/get-assetsScheduleHistory-data";

    public static String ASSET_SCHEDULE_ACTIVITY_RECORD_URL = HOST_URL + "/warehouse/rest/get-assetScheduleActivityRecord-data";

    public static String ASSET_ID_URL = HOST_URL + "/warehouse/rest/get-assetId-data";

    public static String DATES_URL = HOST_URL + "/warehouse/rest/get-dates-data";

    public static String PROGRESS_TABLE_URL = HOST_URL + "/warehouse/rest/get-report-progress";

    public static String ASSET_SCHEDULE_TRACK_URL = HOST_URL + "/warehouse/rest/get-report-assetScheduleTrack";

    public static String ASSET_SCHEDULE_RECORD_URL = HOST_URL + "/warehouse/rest/get-report-assetScheduleRecord";

    public static String OVERDUE_URL = HOST_URL + "/warehouse/rest/get-report-overdue";

    public static String GRAPH_URL = HOST_URL + "/warehouse/rest/get-graph-data";





    public static String FACILITY_INSERT_SQL = "insert into  facility (facility_id,facility_type_id,parent_facility_id,owner_party_id,default_inventory_item_type_id,facility_name, " +
            "last_updated_stamp,last_updated_tx_stamp,created_stamp,created_tx_stamp,closed_date,default_days_to_ship,default_dimension_uom_id,default_weight_uom_id,  " +
           " depot_type,description,facility_size, facility_size_uom_id,geo_point_id,is_disable,manuf_alloc_enable,opened_date,organized,primary_facility_group_id,product_store_id,remarks,reserve_order_enum_id,skip_pack_inv_check,square_footage) "+
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String FACILITY_UPDATE_SQL = "update facility  set facility_type_id = ?, parent_facility_id = ?, owner_party_id = ?, " +
            "default_inventory_item_type_id = ?, facility_name = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ?, closed_date = ?, default_days_to_ship = ?, default_dimension_uom_id = ?, default_weight_uom_id = ?,  " +
            " depot_type = ?, description = ?, facility_size = ?, facility_size_uom_id = ?, geo_point_id = ?, is_disable = ?, manuf_alloc_enable = ?, opened_date = ?, organized = ?, primary_facility_group_id = ?, product_store_id = ?, remarks = ?, reserve_order_enum_id = ?, skip_pack_inv_check = ?, square_footage = ? where facility_id = ?";

    public static String FACILITY_DELETE_SQL = "delete from facility  where facility_id = ?";


    public static String POWER_BLOCKS_INSERT_SQL = "insert into power_blocks (pb_operation_seq_id, created_date,  facility_id, type_of_operation, shadow_block, " +
            "elementary_section_code, section, line, line2, req_department, req_period, reqn_by, ptw_availed_from_date_time, tpc_no_ptw_issue, field_no_ptw_issue, " +
            "ptw_availed_thru_date_time, tpc_no_ptw_return, field_no_ptw_return, purpose, pb_requested_from_date_time,  pb_requested_thru_date_time, pb_granted_from_date_time, " +
            "pb_granted_thru_date_time, staff_to_work, post, switching_station, switching_equipment, equipment_to_work, special_remarks, remarks, tpc_board, schedule, " +
            "supervisor_incharge, current_status, created_on, created_by,   last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp ) " +
            "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String POWER_BLOCKS_UPDATE_SQL = "update power_blocks set created_date = ?, facility_id = ?, type_of_operation = ?, " +
            "shadow_block = ?,elementary_section_code = ?,section = ?,line = ?, line2 = ?, req_department = ?, req_period = ?, reqn_by = ?, ptw_availed_from_date_time = ?, " +
            "tpc_no_ptw_issue = ?,field_no_ptw_issue = ?, ptw_availed_thru_date_time = ?, tpc_no_ptw_return = ?, field_no_ptw_return = ?, purpose = ?, pb_requested_from_date_time = ?, " +
            "pb_requested_thru_date_time = ?, pb_granted_from_date_time = ?,  pb_granted_thru_date_time = ?, staff_to_work = ?, post = ?, switching_station = ?, switching_equipment = ?, " +
            "equipment_to_work = ?, special_remarks = ?, remarks = ?, tpc_board = ?,  schedule = ?, supervisor_incharge = ?, current_status = ?, created_on = ?, created_by = ?, " +
            "last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? where pb_operation_seq_id = ?";


    public static String POWER_BLOCKS_DELETE_SQL = "delete from power_blocks where pb_operation_seq_id = ?";


    public static String ELEMENTARY_SECTIONS_INSERT_SQL = "insert into elementary_sections (seq_id, elementary_section_code, facility_id, tpc_board, devision_id, " +
            "section_code, station_code, sector_code, sub_sector_code, siding_main, track_code, from_km, from_seq, to_km, to_seq,   multi_es_remark, longitudinal_dn, " +
            "description, protection_crossover, protection_turnout, is_auto_dead, remarks_shunting, remarks_no, longitudinal, alternate_supply, last_updated_stamp, " +
            "last_updated_tx_stamp, created_stamp, created_tx_stamp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String ELEMENTARY_SECTIONS_UPDATE_SQL = "update elementary_sections set elementary_section_code = ?, facility_id = ?, tpc_board = ?, devision_id = ?, " +
            "section_code = ?, station_code = ?, sector_code = ?, sub_sector_code = ?, siding_main = ?, track_code = ?, from_km = ?, from_seq = ?, to_km = ?, to_seq = ?,   multi_es_remark = ?, longitudinal_dn = ?, " +
            "description = ?, protection_crossover = ?, protection_turnout = ?, is_auto_dead = ?, remarks_shunting = ?, remarks_no = ?, longitudinal = ?, alternate_supply = ?, last_updated_stamp = ?, " +
            "last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? where seq_id = ?";

    public static String ELEMENTARY_SECTIONS_DELETE_SQL = "delete from elementary_sections where seq_id = ?";


    public static String SCHEDULE_INSERT_SQL = "insert into  schedule (seq_id,schedule_code,schedule_name,schedule_type,description,created_on, " +
            "created_by, last_updated_stamp,last_updated_tx_stamp,created_stamp, created_tx_stamp ) values (?,?,?,?,?,?,?,?,?,?,?)";

    public static String SCHEDULE_UPDATE_SQL = "update schedule set seq_id = ?, schedule_code = ?, schedule_name = ?, schedule_type = ?, " +
            "description = ?, created_on = ?,  created_by = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, " +
            "created_stamp = ?, created_tx_stamp = ? where seq_id = ?";

    public static String SCHEDULE_DELETE_SQL = "delete from schedule where seq_id = ?";


    public static String ASSET_SCHEDULE_ASSOC_INSERT_SQL = "insert into asset_schedule_assoc (asa_seq_id, asset_type, schedule_code, sequence_code, " +
            "duration, uom_of_duration, description, created_on, created_by,last_updated_stamp, last_updated_tx_stamp, " +
            "created_stamp, created_tx_stamp) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String ASSET_SCHEDULE_ASSOC_UPDATE_SQL = "update asset_schedule_assoc set asset_type = ?, schedule_code = ?, " +
            "sequence_code = ?, duration = ?, uom_of_duration = ?, description = ?, created_on = ?, created_by = ?, " +
            "last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? where asa_seq_id = ?";

    public static String ASSET_SCHEDULE_ASSOC_DELETE_SQL = "delete from asset_schedule_assoc where asa_seq_id = ?";

    public static String MEASURE_OR_ACTIVITY_INSERT_SQL = "insert into measure_or_activity_list (seq_id, activity_id, activity_name, activity_type, " +
            "unit_of_measure, description, created_on, created_by, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp )" +
            "values (?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String MEASURE_OR_ACTIVITY_UPDATE_SQL = "update measure_or_activity_list set activity_id = ?, activity_name = ?, " +
            "activity_type = ?, unit_of_measure = ?, description = ?, created_on = ?, created_by = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, " +
            "created_stamp = ?, created_tx_stamp = ? where seq_id = ?";

    public static String MEASURE_OR_ACTIVITY_DELETE_SQL = "delete from measure_or_activity_list where seq_id = ?";

    public static String ASSET_SCHEDULE_ACTIVITY_ASSOC_INSERT_SQL = "insert into asset_schedule_activity_assoc(seq_id, asa_seq_id, sub_asset_type, " +
            "activity_id, activity_position_id, make_code, model_code, lower_limit, upper_limit, description, created_on, created_by," +
            "last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String ASSET_SCHEDULE_ACTIVITY_ASSOC_UPDATE_SQL = "update asset_schedule_activity_assoc set asa_seq_id = ?, sub_asset_type = ?, " +
            "activity_id = ?, activity_position_id = ?, make_code = ?, model_code = ?, lower_limit = ?, upper_limit = ?, description = ?, " +
            "created_on = ?, created_by = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? " +
            "where seq_id = ?";

    public static String ASSET_SCHEDULE_ACTIVITY_ASSOC_DELETE_SQL = "delete from asset_schedule_activity_assoc where seq_id = ?";

    public static String ASSET_MASTER_DATA_INSERT_SQL = "insert into asset_master_data (seq_id, asset_type, asset_id, facility_id, " +
            "elementary_section, rly_assigned_serial, warranty_amc, created_on, created_by, location_position,last_updated_stamp, " +
            "last_updated_tx_stamp, created_stamp, created_tx_stamp, type , major_section ,adee_section , name_plate_details , end1_side1, " +
            "end2_side2 , station , lug_date , remark1 , remark2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?)";

    public static String ASSET_MASTER_DATA_UPDATE_SQL = "update asset_master_data set asset_type = ?, asset_id = ?, facility_id = ?, " +
            "elementary_section = ?, rly_assigned_serial = ?, warranty_amc = ?, created_on = ?, created_by = ?, location_position = ?, " +
            "last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ?,  type = ?, major_section = ?, " +
            "adee_section = ?, name_plate_details = ?, end1_side1 = ?, end2_side2 = ?, station  = ?, lug_date = ?, remark1 = ?, remark2 = ? where seq_id = ?";

    public static String ASSET_MASTER_DATA_DELETE_SQL = "delete from asset_master_data where seq_id = ?";


    public static String ASSET_MONTHLY_TARGETS_INSERT_SQL = "insert into asset_monthly_targets ( seq_id, facility_id, elementary_section, schedule_type, asset_type,  " +
            " target_jan, target_feb, target_mar, target_apr, target_may, target_june, target_july, target_aug, target_sep, target_oct, target_nov, target_dec, year, created_on, created_by, " +
            "last_updated_stamp, last_updated_tx_stamp) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String ASSET_MONTHLY_TARGETS_UPDATE_SQL = "update asset_monthly_targets set facility_id= ?, elementary_section = ?, schedule_type = ?, asset_type = ?, " +
            " target_jan = ?, target_feb = ?, target_mar = ?, target_apr = ?, target_may = ?, target_june = ?, target_july = ?, target_aug = ?, target_sep = ?, target_oct = ?, target_nov = ?, target_dec = ?," +
            " year = ?, created_on = ?, created_by = ?,last_updated_stamp = ?, last_updated_tx_stamp = ? where seq_id = ?" ;

    public static String ASSET_MONTHLY_TARGETS_DELETE_SQL = "delete from asset_monthly_targets where seq_id = ?";


    public static String  ASSET_STATUS_UPDATE_INSERT_SQL = " insert into asset_status_update ( seq_id, created_date, asset_id, asset_type, facility_id, date_of_status, status, defect_observed, reason_of_status_change, schedule," +
            "remarks, current_status, created_by, created_on, last_updated_stamp, last_updated_tx_stamp) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


    public static String ASSET_STATUS_UPDATE_UPDATE_SQL = " update asset_status_update set created_date = ?, asset_id = ?, asset_type = ?, facility_id = ?, date_of_status = ?, status = ?, defect_observed = ?, " +
            " reason_of_status_change = ?, schedule = ?, remarks = ?, current_status = ?, created_by = ?, created_on = ?, last_updated_stamp = ?, last_updated_tx_stamp = ? where seq_id = ? " ;

    public static String ASSET_STATUS_UPDATE_DELETE_SQL = "delete from asset_status_update where seq_id = ?";

    public static String PRODUCT_INSERT_SQL = "insert into product (product_id, product_type_id, internal_name, product_name, bill_of_material_level, " +
            "is_active, product_code_type_id, pl_no, rly_id, trd_div_id, last_updated_stamp, last_updated_tx_stamp, " +
            "created_stamp, created_tx_stamp ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String PRODUCT_UPDATE_SQL = "update product set product_type_id = ?, internal_name = ?, product_name = ?, " +
            "bill_of_material_level = ?, is_active = ?, product_code_type_id = ?, pl_no = ?, rly_id = ?, trd_div_id = ?, last_updated_stamp = ?, " +
            "last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? where product_id = ?";

    public static String PRODUCT_DELETE_SQL = "delete from product where product_id = ?";

    public static String USER_LOGIN_INSERT_SQL = "insert into user_login( user_login_id, current_password, password_hint, is_system, enabled, last_updated_stamp, last_updated_tx_stamp, " +
            "created_stamp, created_tx_stamp) " +
            "values(?,?,?,?,?,?,?,?,?)";

    public static String USER_LOGIN_UPDATE_SQL = "update user_login set current_password = ?, password_hint = ?," +
            "is_system = ?, enabled = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? where user_login_id = ?";

    public static String USER_LOGIN_DELETE_SQL = "delete from user_login where user_login_id = ?";


    public static String DEVICE_INSERT_SQL = "insert into device( Device_IMEI, facility_id) values (?, ?)";


    public static String DEVICE_UPDATE_SQL = "update device set facility_id = ? where Device_IMEI = ?";

    public static String DEVICE_DELETE_SQL = "delete from device where Device_IMEI = ?";

    public static String ASSETS_SCHEDULE_HISTORY_INSERT_SQL = "insert into assets_schedule_history (seq_id,facility_id, pb_operation_seq_id, device_id, device_seq_id, device_created_stamp, device_last_updated_stamp,  asset_type, asset_id, schedule_code, schedule_date, status," +
            "details_of_maint, done_by,initial_of_incharge, remarks,  created_on, created_by, last_updated_stamp," +
            "last_updated_tx_stamp, created_stamp, created_tx_stamp)" +
            " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;

    public static String ASSETS_SCHEDULE_HISTORY_UPDATE_SQL = "UPDATE assets_schedule_history SET seq_id = ?, pb_operation_seq_id = ?,  device_id = ?, device_seq_id = ?, device_created_stamp = ?, device_last_updated_stamp  = ?, asset_type = ?, asset_id = ?, schedule_code = ?, schedule_date = ?, status = ?, details_of_maint = ?,  done_by = ?, initial_of_incharge = ?,  " +
            "remarks = ?,  created_on = ?, created_by = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ? WHERE facility_id = ?" ;

    public static String ASSETS_SCHEDULE_HISTORY_DELETE_SQL = "DELETE FROM assets_schedule_history WHERE seq_id = ?";


    public static String ASSET_SCHEDULE_ACTIVITY_RECORD_INSERT_SQL = "insert into asset_schedule_activity_record ( asset_measure_obser_seq_id, " +
            "facility_id,asset_type, asset_id, schedule_code, schedule_date, schedule_actual_date, asset_schedule_history_id,status, device_id, device_seq_id, device_created_stamp, device_last_updated_stamp, " +
            " m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20, m21, m22, m23, m24, m25, m26, m27, m28, m29, m30, m31, m32, m33, m34, m35, m36, m37, m38, m39, m40, m41, m42, m43, m44, m45, m46, m47,m48, m49, m50," +
            " a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22, a23, a24, a25, a26, a27, a28, a29, a30, a31, a32, a33, a34, a35, a36, a37, a38, a39, a40, a41, a42, a43, a44, a45, a46, a47, a48, a49, a50, " +
            " mma1, mma2, mma3, mma4, mma5, mma6, mma7, mma8, mma9, mma10, " +
            "make, model, details_of_maint, done_by, initial_of_incharge, remarks, created_on, created_by, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp )" +
            " values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,    ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
            "   ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,    ?,?,?,?,?,?,?,?,?,?,     ?,?,?,?,?,?,?,?,?,?,?,?)";

    public static String ASSET_SCHEDULE_ACTIVITY_RECORD_UPDATE_SQL = "UPDATE asset_schedule_activity_record SET asset_measure_obser_seq_id = ?, asset_type = ?, " +
            " asset_id = ?, schedule_code = ?, schedule_date = ?,  schedule_actual_date = ?, asset_schedule_history_id = ?, status = ?, device_id = ?, device_seq_id = ?, device_created_stamp = ?, device_last_updated_stamp = ?, " +
            " m1 = ?, m2 = ?, m3 = ?, m4 = ?, m5 = ?, m6 = ?, m7 = ?, m8 = ?, m9 = ?, m10 = ?, m11 = ?, m12 = ?, m13 = ?, m14 = ?, m15 = ?, m16 = ?, m17 = ?, m18 = ?, m19 = ?, m20 = ?, m21 = ?, m22 = ?,m23 = ?, m24 =?, m25 = ?," +
            "m26 = ?, m27 = ?, m28 = ?, m29 = ?, m30 = ?, m31 = ?, m32 = ?, m33 = ?, m34 = ?, m35 = ?, m36 = ?, m37 = ?, m38 = ?, m39 = ?, m40 = ?, m41 = ?, m42 = ?, m43 = ?, m44 = ?, m45 = ?, m46 = ?, m47 = ?,m48 = ?, m49 = ?, m50 = ?, " +
            " a1 = ?, a2 = ?, a3 = ?, a4 = ?, a5 = ?, a6 = ?, a7 = ?, a8 = ?, a9 = ?, a10 = ?, a11 = ?, a12 = ?, a13 = ?, a14 = ?, a15 = ?, a16 = ?, a17 = ?, a18 = ?, a19 = ?, a20 = ?, a21 = ?, a22 = ?, a23 = ?, a24 = ?, a25 = ?, " +
            "a26 = ?, a27 = ?, a28 = ?, a29 = ?, a30 = ?, a31 = ?, a32 = ?, a33 = ?, a34 = ?, a35 = ?, a36 = ?, a37 = ?, a38 = ?, a39 = ?, a40 = ?, a41 = ?, a42 = ?, a43 = ?, a44 = ?, a45 = ?, a46 = ?, a47 = ?, a48 = ?, a49 = ?, a50 = ?," +
            "mma1 = ?, mma2 = ?, mma3 = ?, mma4 = ?, mma5 = ?, mma6 = ?, mma7 = ?, mma8 = ?, mma9 = ?, mma10 = ?, "+
            " make = ?, model = ?,  details_of_maint = ?, done_by = ?, initial_of_incharge = ?, remarks = ?, created_on = ?, created_by = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ?  WHERE facility_id = ? " ;


    public static String ASSET_SCHEDULE_ACTIVITY_RECORD_DELETE_SQL = "DELETE FROM asset_schedule_activity_record" +
            " WHERE asset_measure_obser_seq_id = ?" ;



    public static String PRODUCT_CATEGORY_MEMBER_INSERT_SQL = "insert into product_category_member( comments, from_date, product_category_id, product_id, quantity, " +
              " sequence_num, thru_date, created_stamp, created_tx_stamp, last_updated_stamp, last_updated_tx_stamp ) values (?,?,?,?,?,?,?,?,?,?,?)";


    public static String PRODUCT_CATEGORY_MEMBER_UPDATE_SQL = "update product_category_member set comments = ?, from_date = ?, product_category_id = ?, quantity = ?, " +
                " sequence_num = ?, thru_date = ?,created_stamp = ?, created_tx_stamp = ?, last_updated_stamp = ?, last_updated_tx_stamp = ? where product_id = ? ";

    public static String PRODUCT_CATEGORY_MEMBER_DELETE_SQL = "delete from product_category_member where product_id = ? " ;


    public static String OHE_LOCATION_INSERT_SQL = " insert into ohe_location ( seq_id, division, section, pwi, ohe_mast, eng_feature, ohe_feature, remark_one, remark_two, latitude, longitude, altitude, validity, satellites, speed, heading, " +
                 " ohe_sequence, curvature, curvature_remark, date, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp, chainage, chainage_remark, structure_type, track_line, kilometer, sequence_no, facility_id, major_section, adee_section )" +
                 "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?)" ;

    public static String OHE_LOCATION_UPDATE_SQL =  " update ohe_location set division = ?, section = ?, pwi = ?, ohe_mast = ?, eng_feature = ?, ohe_feature = ?, remark_one = ?, remark_two = ?, latitude = ?, longitude = ?, altitude = ?, validity = ?, satellites = ?, speed = ?, heading = ?, " +
                 " ohe_sequence = ?, curvature = ?, curvature_remark = ?, date = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ?, chainage = ?, chainage_remark = ?, structure_type = ?, track_line = ?, kilometer = ?, sequence_no = ?, facility_id = ?, major_section = ?, adee_section = ? where seq_id = ?" ;


    public static String APP_DEVICE_UNIT_INSERT_SQL = "insert into app_device_unit ( seq_id, app_device_seq_id,  last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp, unit_id,  unit_type )" +
                                 " values ( ?,?,?,?,?,?,?,?) " ;

    public static String APP_DEVICE_UNIT_UPDATE_SQL = " update app_device_unit set app_device_seq_id = ?, last_updated_stamp = ?, last_updated_tx_stamp = ?, created_stamp = ?, created_tx_stamp = ?, unit_id =?,  unit_type = ? where seq_id = ? ";


}
