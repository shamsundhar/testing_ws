package trd.ams.dao;

import android.util.Log;


import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

import java.util.ArrayList;


import trd.ams.common.Constants;
import trd.ams.dto.AppDeviceUnitDto;
import trd.ams.dto.AssetMasterDataDto;
import trd.ams.dto.AssetMonthlyTargetsDto;
import trd.ams.dto.AssetScheduleActivityAssocDto;
import trd.ams.dto.AssetScheduleActivityRecordDto;
import trd.ams.dto.AssetScheduleAssocDto;
import trd.ams.dto.AssetStatusUpdateDto;
import trd.ams.dto.AssetsScheduleHistoryDto;
import trd.ams.dto.DeviceDto;
import trd.ams.dto.ElementarySectionsDto;
import trd.ams.dto.FacilityDto;
import trd.ams.dto.MeasureOrActivityListDto;
import trd.ams.dto.OheLocationDto;
import trd.ams.dto.PowerBlocksDto;
import trd.ams.dto.ProductCategoryMemberDto;
import trd.ams.dto.ProductDto;
import trd.ams.dto.ResponseAssetStatusUpdateDto;
import trd.ams.dto.ScheduleDto;
import trd.ams.dto.UserLoginDto;

/**
 * Created by hanu on 20-01-2018.
 */

public class DataUpdateDAO {


    private static String TAG = "DataUpdateDAO";

    private static DataUpdateDAO dataUpdateDAO;

    public static DataUpdateDAO getInstance() {

        if (dataUpdateDAO == null)
            dataUpdateDAO = new DataUpdateDAO();

        return  dataUpdateDAO;
    }

    /**
     * Class Constructor
     */
    private DataUpdateDAO() {

    }


    public boolean insertFacilityData(FacilityDto facilityDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(facilityDto.getFacilityId());
        values.add(facilityDto.getFacilityTypeId());
        values.add(facilityDto.getParentFacilityId());
        values.add(facilityDto.getOwnerPartyId());
        values.add(facilityDto.getDefaultInventoryItemTypeId());
        values.add(facilityDto.getFacilityName());
        values.add(facilityDto.getLastUpdatedStamp());
        values.add(facilityDto.getLastUpdatedTxStamp());
        values.add(facilityDto.getCreatedStamp());
        values.add(facilityDto.getCreatedTxStamp());
        values.add(facilityDto.getClosedDate());
        values.add(facilityDto.getDefaultDaysToShip());
        values.add(facilityDto.getDefaultDimensionUomId());
        values.add(facilityDto.getDefaultWeightUomId());
        values.add(facilityDto.getDepotType());
        values.add(facilityDto.getDescription());
        values.add(facilityDto.getFacilitySize());
        values.add(facilityDto.getFacilitySizeUomId());
        values.add(facilityDto.getGeoPointId());
        values.add(facilityDto.getIsDisable());
        values.add(facilityDto.getManufAllocEnable());
        values.add(facilityDto.getOpenedDate());
        values.add(facilityDto.getOrganized());
        values.add(facilityDto.getPrimaryFacilityGroupId());
        values.add(facilityDto.getProductStoreId());
        values.add(facilityDto.getRemarks());
        values.add(facilityDto.getReserveOrderEnumId());
        values.add(facilityDto.getSkipPackInvCheck());
        values.add(facilityDto.getSquareFootage());


        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.FACILITY_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertFacilityData - : " + e.getMessage());
        }
        return result;

    }

    public boolean updateFacilityData(FacilityDto facilityDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(facilityDto.getFacilityTypeId());
        values.add(facilityDto.getParentFacilityId());
        values.add(facilityDto.getOwnerPartyId());
        values.add(facilityDto.getDefaultInventoryItemTypeId());
        values.add(facilityDto.getFacilityName());
        values.add(facilityDto.getLastUpdatedStamp());
        values.add(facilityDto.getLastUpdatedTxStamp());
        values.add(facilityDto.getCreatedStamp());
        values.add(facilityDto.getCreatedTxStamp());
        values.add(facilityDto.getFacilityId());
        values.add(facilityDto.getClosedDate());
        values.add(facilityDto.getDefaultDaysToShip());
        values.add(facilityDto.getDefaultDimensionUomId());
        values.add(facilityDto.getDefaultWeightUomId());
        values.add(facilityDto.getDepotType());
        values.add(facilityDto.getDescription());
        values.add(facilityDto.getFacilitySize());
        values.add(facilityDto.getFacilitySizeUomId());
        values.add(facilityDto.getGeoPointId());
        values.add(facilityDto.getIsDisable());
        values.add(facilityDto.getManufAllocEnable());
        values.add(facilityDto.getOpenedDate());
        values.add(facilityDto.getOrganized());
        values.add(facilityDto.getPrimaryFacilityGroupId());
        values.add(facilityDto.getProductStoreId());
        values.add(facilityDto.getRemarks());
        values.add(facilityDto.getReserveOrderEnumId());
        values.add(facilityDto.getSkipPackInvCheck());
        values.add(facilityDto.getSquareFootage());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.FACILITY_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateFacilityData - : " + e.getMessage());
        }
        return result;

    }

    public boolean insertAppDeviceUnitData(AppDeviceUnitDto appDeviceUnitDto, SQLiteDatabase db) {

        boolean result = false;

        ArrayList<String> values = new ArrayList<>();

            values.add(appDeviceUnitDto.getSeqId());
            values.add(appDeviceUnitDto.getAppDeviceSeqId());
            values.add(appDeviceUnitDto.getCreatedStamp());
            values.add(appDeviceUnitDto.getCreatedTxStamp());
            values.add(appDeviceUnitDto.getLastUpdatedStamp());
            values.add(appDeviceUnitDto.getLastUpdatedTxStamp());
            values.add(appDeviceUnitDto.getUnitId());
            values.add(appDeviceUnitDto.getUnitType());

            try{
                result = DataUpdateDAO.getInstance().insertTableData(Constants.APP_DEVICE_UNIT_INSERT_SQL, values.toArray(new String[values.size()]),db);
            }catch (Exception e){
                e.printStackTrace();
            }
               return result;
    }


    public boolean updateAppDeviceUnitData(AppDeviceUnitDto appDeviceUnitDto, SQLiteDatabase db) {

        boolean result = false;

        ArrayList<String> values = new ArrayList<>();

        values.add(appDeviceUnitDto.getSeqId());
        values.add(appDeviceUnitDto.getAppDeviceSeqId());
        values.add(appDeviceUnitDto.getCreatedStamp());
        values.add(appDeviceUnitDto.getCreatedTxStamp());
        values.add(appDeviceUnitDto.getLastUpdatedStamp());
        values.add(appDeviceUnitDto.getLastUpdatedTxStamp());
        values.add(appDeviceUnitDto.getUnitId());
        values.add(appDeviceUnitDto.getUnitType());

        try{
            result = DataUpdateDAO.getInstance().updateTableData(Constants.APP_DEVICE_UNIT_UPDATE_SQL, values.toArray(new String[values.size()]),db);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }




    public boolean insertPowerBlocksData(PowerBlocksDto powerblockDto, SQLiteDatabase db) {

        boolean result = false;

        ArrayList<String> values = new ArrayList<>();
        values.add(powerblockDto.getPbOperationSeqId());
        values.add(powerblockDto.getCreatedDate());
        values.add(powerblockDto.getFacilityId());
        values.add(powerblockDto.getTypeOfOperation());
        values.add(powerblockDto.getShadowBlock());
        values.add(powerblockDto.getElementarySectionCode());
        values.add(powerblockDto.getSection());
        values.add(powerblockDto.getLine());
        values.add(powerblockDto.getLine2());
        values.add(powerblockDto.getReqDepartment());
        values.add(powerblockDto.getReqPeriod());
        values.add(powerblockDto.getReqnBy());
        values.add(powerblockDto.getPtwAvailedFromDateTime());
        values.add(powerblockDto.getTpcNoPtwIssue());
        values.add(powerblockDto.getFieldNoPtwIssue());
        values.add(powerblockDto.getPtwAvailedThruDateTime());
        values.add(powerblockDto.getTpcNoPtwReturn());
        values.add(powerblockDto.getFieldNoPtwReturn());
        values.add(powerblockDto.getPurpose());
        values.add(powerblockDto.getPbRequestedFromDateTime());
        values.add(powerblockDto.getPbRequestedThruDateTime());
        values.add(powerblockDto.getPbGrantedFromDateTime());
        values.add(powerblockDto.getPbGrantedThruDateTime());
        values.add(powerblockDto.getStaffToWork());
        values.add(powerblockDto.getPost());
        values.add(powerblockDto.getSwitchingStation());
        values.add(powerblockDto.getSwitchingEquipment());
        values.add(powerblockDto.getEquipmentToWork());
        values.add(powerblockDto.getSpecialRemarks());
        values.add(powerblockDto.getTpcBoard());
        values.add(powerblockDto.getSchedule());
        values.add(powerblockDto.getSupervisorIncharge());
        values.add(powerblockDto.getCurrentStatus());
        values.add(powerblockDto.getCreatedOn());
        values.add(powerblockDto.getCreatedBy());
        values.add(powerblockDto.getLastUpdatedStamp());
        values.add(powerblockDto.getLastUpdatedTxStamp());
        values.add(powerblockDto.getCreatedStamp());
        values.add(powerblockDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.POWER_BLOCKS_INSERT_SQL, values.toArray(new String[values.size()]), db);


        } catch (Exception e){

            Log.e(TAG, "insertPowerBlocksData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updatePowerBlocksData(PowerBlocksDto powerblockDto, SQLiteDatabase db) {

        boolean result = false;

        ArrayList<String> values = new ArrayList<>();

        values.add(powerblockDto.getCreatedDate());
        values.add(powerblockDto.getFacilityId());
        values.add(powerblockDto.getTypeOfOperation());
        values.add(powerblockDto.getShadowBlock());
        values.add(powerblockDto.getElementarySectionCode());
        values.add(powerblockDto.getSection());
        values.add(powerblockDto.getLine());
        values.add(powerblockDto.getLine2());
        values.add(powerblockDto.getReqDepartment());
        values.add(powerblockDto.getReqPeriod());
        values.add(powerblockDto.getReqnBy());
        values.add(powerblockDto.getPtwAvailedFromDateTime());
        values.add(powerblockDto.getTpcNoPtwIssue());
        values.add(powerblockDto.getFieldNoPtwIssue());
        values.add(powerblockDto.getPtwAvailedThruDateTime());
        values.add(powerblockDto.getTpcNoPtwReturn());
        values.add(powerblockDto.getFieldNoPtwReturn());
        values.add(powerblockDto.getPurpose());
        values.add(powerblockDto.getPbRequestedFromDateTime());
        values.add(powerblockDto.getPbRequestedThruDateTime());
        values.add(powerblockDto.getPbGrantedFromDateTime());
        values.add(powerblockDto.getPbGrantedThruDateTime());
        values.add(powerblockDto.getStaffToWork());
        values.add(powerblockDto.getPost());
        values.add(powerblockDto.getSwitchingStation());
        values.add(powerblockDto.getSwitchingEquipment());
        values.add(powerblockDto.getEquipmentToWork());
        values.add(powerblockDto.getSpecialRemarks());
        values.add(powerblockDto.getTpcBoard());
        values.add(powerblockDto.getSchedule());
        values.add(powerblockDto.getSupervisorIncharge());
        values.add(powerblockDto.getCurrentStatus());
        values.add(powerblockDto.getCreatedOn());
        values.add(powerblockDto.getCreatedBy());
        values.add(powerblockDto.getLastUpdatedStamp());
        values.add(powerblockDto.getLastUpdatedTxStamp());
        values.add(powerblockDto.getCreatedStamp());
        values.add(powerblockDto.getCreatedTxStamp());
        values.add(powerblockDto.getPbOperationSeqId());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.POWER_BLOCKS_UPDATE_SQL, values.toArray(new String[values.size()]), db);


        } catch (Exception e){

            Log.e(TAG, "updatePowerBlocksData - : " + e.getMessage());
        }

        return result;
    }

    public boolean insertElementarySectionsData(ElementarySectionsDto elementarySectionDto, SQLiteDatabase db) {

        boolean result = false;


        ArrayList<String> values = new ArrayList<>();
        values.add(elementarySectionDto.getSeqId());
        values.add(elementarySectionDto.getElementarySectionCode());
        values.add(elementarySectionDto.getFacilityId());
        values.add(elementarySectionDto.getTpcBoard());
        values.add(elementarySectionDto.getDevisionId());
        values.add(elementarySectionDto.getSectionCode());
        values.add(elementarySectionDto.getStationCode());
        values.add(elementarySectionDto.getSectorCode());
        values.add(elementarySectionDto.getSubSectorCode());
        values.add(elementarySectionDto.getSidingMain());
        values.add(elementarySectionDto.getTrackCode());
        values.add(elementarySectionDto.getFromKm());
        values.add(elementarySectionDto.getFromSeq());
        values.add(elementarySectionDto.getToKm());
        values.add(elementarySectionDto.getToSeq());
        values.add(elementarySectionDto.getMultiEsRemark());
        values.add(elementarySectionDto.getLongitudinalDn());
        values.add(elementarySectionDto.getDescription());
        values.add(elementarySectionDto.getProtectionCrossover());
        values.add(elementarySectionDto.getProtectionTurnout());
        values.add(elementarySectionDto.getIsAutoDead());
        values.add(elementarySectionDto.getRemarksShunting());
        values.add(elementarySectionDto.getRemarksNo());
        values.add(elementarySectionDto.getLongitudinal());
        values.add(elementarySectionDto.getAlternateSupply());
        values.add(elementarySectionDto.getLastUpdatedStamp());
        values.add(elementarySectionDto.getLastUpdatedTxStamp());
        values.add(elementarySectionDto.getCreatedStamp());
        values.add(elementarySectionDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ELEMENTARY_SECTIONS_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertElementarySectionsData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateElementarySectionsData(ElementarySectionsDto elementarySectionDto, SQLiteDatabase db) {

        boolean result = false;


        ArrayList<String> values = new ArrayList<>();

        values.add(elementarySectionDto.getElementarySectionCode());
        values.add(elementarySectionDto.getFacilityId());
        values.add(elementarySectionDto.getTpcBoard());
        values.add(elementarySectionDto.getDevisionId());
        values.add(elementarySectionDto.getSectionCode());
        values.add(elementarySectionDto.getStationCode());
        values.add(elementarySectionDto.getSectorCode());
        values.add(elementarySectionDto.getSubSectorCode());
        values.add(elementarySectionDto.getSidingMain());
        values.add(elementarySectionDto.getTrackCode());
        values.add(elementarySectionDto.getFromKm());
        values.add(elementarySectionDto.getFromSeq());
        values.add(elementarySectionDto.getToKm());
        values.add(elementarySectionDto.getToSeq());
        values.add(elementarySectionDto.getMultiEsRemark());
        values.add(elementarySectionDto.getLongitudinalDn());
        values.add(elementarySectionDto.getDescription());
        values.add(elementarySectionDto.getProtectionCrossover());
        values.add(elementarySectionDto.getProtectionTurnout());
        values.add(elementarySectionDto.getIsAutoDead());
        values.add(elementarySectionDto.getRemarksShunting());
        values.add(elementarySectionDto.getRemarksNo());
        values.add(elementarySectionDto.getLongitudinal());
        values.add(elementarySectionDto.getAlternateSupply());
        values.add(elementarySectionDto.getLastUpdatedStamp());
        values.add(elementarySectionDto.getLastUpdatedTxStamp());
        values.add(elementarySectionDto.getCreatedStamp());
        values.add(elementarySectionDto.getCreatedTxStamp());
        values.add(elementarySectionDto.getSeqId());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ELEMENTARY_SECTIONS_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateElementarySectionsData - : " + e.getMessage());
        }

        return result;
    }
    public boolean insertScheduleData(ScheduleDto scheduleDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(scheduleDto.getSeqId());
        values.add(scheduleDto.getScheduleCode());
        values.add(scheduleDto.getScheduleName());
        values.add(scheduleDto.getScheduleType());
        values.add(scheduleDto.getDescription());
        values.add(scheduleDto.getCreatedOn());
        values.add(scheduleDto.getCreatedBy());
        values.add(scheduleDto.getLastUpdatedStamp());
        values.add(scheduleDto.getLastUpdatedTxStamp());
        values.add(scheduleDto.getCreatedStamp());
        values.add(scheduleDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.SCHEDULE_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertScheduleData - : " + e.getMessage());
        }
        return result;

    }

    public boolean updateScheduleData(ScheduleDto scheduleDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(scheduleDto.getSeqId());
        values.add(scheduleDto.getScheduleCode());
        values.add(scheduleDto.getScheduleName());
        values.add(scheduleDto.getScheduleType());
        values.add(scheduleDto.getDescription());
        values.add(scheduleDto.getCreatedOn());
        values.add(scheduleDto.getCreatedBy());
        values.add(scheduleDto.getLastUpdatedStamp());
        values.add(scheduleDto.getLastUpdatedTxStamp());
        values.add(scheduleDto.getCreatedStamp());
        values.add(scheduleDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.SCHEDULE_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateScheduleData - : " + e.getMessage());
        }
        return result;

    }


    public boolean insertAssetScheduleAssocData(AssetScheduleAssocDto assetScheduleAssocDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(assetScheduleAssocDto.getAsaSeqId());
        values.add(assetScheduleAssocDto.getAssetType());
        values.add(assetScheduleAssocDto.getScheduleCode());
        values.add(assetScheduleAssocDto.getSequenceCode());
        values.add(assetScheduleAssocDto.getDuration());
        values.add(assetScheduleAssocDto.getUomOfDuration());
        values.add(assetScheduleAssocDto.getDescription());
        values.add(assetScheduleAssocDto.getCreatedOn());
        values.add(assetScheduleAssocDto.getCreatedBy());
        values.add(assetScheduleAssocDto.getLastUpdatedStamp());
        values.add(assetScheduleAssocDto.getLastUpdatedTxStamp());
        values.add(assetScheduleAssocDto.getCreatedStamp());
        values.add(assetScheduleAssocDto.getCreatedTxStamp());
       // values.add(assetScheduleAssocDto.getTargetPlanMonths());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_SCHEDULE_ASSOC_INSERT_SQL, values.toArray(new String[values.size()]), db);


        } catch (Exception e){

            Log.e(TAG, "syncAssetScheduleAssocData - : " + e.getMessage());
        }
        return result;
    }

    public boolean updateAssetScheduleAssocData(AssetScheduleAssocDto assetScheduleAssocDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(assetScheduleAssocDto.getAssetType());
        values.add(assetScheduleAssocDto.getScheduleCode());
        values.add(assetScheduleAssocDto.getSequenceCode());
        values.add(assetScheduleAssocDto.getDuration());
        values.add(assetScheduleAssocDto.getUomOfDuration());
        values.add(assetScheduleAssocDto.getDescription());
        values.add(assetScheduleAssocDto.getCreatedOn());
        values.add(assetScheduleAssocDto.getCreatedBy());
        values.add(assetScheduleAssocDto.getLastUpdatedStamp());
        values.add(assetScheduleAssocDto.getLastUpdatedTxStamp());
        values.add(assetScheduleAssocDto.getCreatedStamp());
        values.add(assetScheduleAssocDto.getCreatedTxStamp());
        values.add(assetScheduleAssocDto.getAsaSeqId());
      //  values.add(assetScheduleAssocDto.getTargetPlanMonths());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_SCHEDULE_ASSOC_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateAssetScheduleAssocData - : " + e.getMessage());
        }
        return result;
    }
    public boolean insertAssetScheduleActivityAssocData(AssetScheduleActivityAssocDto assetScheduleActivityAssocDto, SQLiteDatabase db) {


        boolean result = false;

        ArrayList<String> values = new ArrayList<>();
        values.add(assetScheduleActivityAssocDto.getSeqId());
        values.add(assetScheduleActivityAssocDto.getAsaSeqId());
        values.add(assetScheduleActivityAssocDto.getSubAssetType());
        values.add(assetScheduleActivityAssocDto.getActivityId());
        values.add(assetScheduleActivityAssocDto.getActivityPositionId());
        values.add(assetScheduleActivityAssocDto.getMakeCode());
        values.add(assetScheduleActivityAssocDto.getModelCode());
        values.add(assetScheduleActivityAssocDto.getLowerLimit());
        values.add(assetScheduleActivityAssocDto.getUpperLimit());
        values.add(assetScheduleActivityAssocDto.getDescription());
        values.add(assetScheduleActivityAssocDto.getCreatedOn());
        values.add(assetScheduleActivityAssocDto.getCreatedBy());
        values.add(assetScheduleActivityAssocDto.getLastUpdatedStamp());
        values.add(assetScheduleActivityAssocDto.getLastUpdatedTxStamp());
        values.add(assetScheduleActivityAssocDto.getCreatedStamp());
        values.add(assetScheduleActivityAssocDto.getCreatedTxStamp());


        try {

            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_SCHEDULE_ACTIVITY_ASSOC_INSERT_SQL, values.toArray(new String[values.size()]), db);


        } catch (Exception e){

            Log.e(TAG, "insertAssetScheduleActivityData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateAssetScheduleActivityAssocData(AssetScheduleActivityAssocDto assetScheduleActivityAssocDto,SQLiteDatabase db) {


        boolean result = false;

        ArrayList<String> values = new ArrayList<>();
        values.add(assetScheduleActivityAssocDto.getAsaSeqId());
        values.add(assetScheduleActivityAssocDto.getSubAssetType());
        values.add(assetScheduleActivityAssocDto.getActivityId());
        values.add(assetScheduleActivityAssocDto.getActivityPositionId());
        values.add(assetScheduleActivityAssocDto.getMakeCode());
        values.add(assetScheduleActivityAssocDto.getModelCode());
        values.add(assetScheduleActivityAssocDto.getLowerLimit());
        values.add(assetScheduleActivityAssocDto.getUpperLimit());
        values.add(assetScheduleActivityAssocDto.getDescription());
        values.add(assetScheduleActivityAssocDto.getCreatedOn());
        values.add(assetScheduleActivityAssocDto.getCreatedBy());
        values.add(assetScheduleActivityAssocDto.getLastUpdatedStamp());
        values.add(assetScheduleActivityAssocDto.getLastUpdatedTxStamp());
        values.add(assetScheduleActivityAssocDto.getCreatedStamp());
        values.add(assetScheduleActivityAssocDto.getCreatedTxStamp());
        values.add(assetScheduleActivityAssocDto.getSeqId());

        try {

            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_SCHEDULE_ACTIVITY_ASSOC_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateAssetScheduleActivityData - : " + e.getMessage());
        }

        return result;
    }
    public boolean insertAssetMasterData(AssetMasterDataDto assetMasterDataDto, SQLiteDatabase db) {


        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(assetMasterDataDto.getSeqId());
        values.add(assetMasterDataDto.getAssetType());
        values.add(assetMasterDataDto.getAssetId());
        values.add(assetMasterDataDto.getFacilityId());
        values.add(assetMasterDataDto.getElementarySection());
        values.add(assetMasterDataDto.getRlyAssignedSerial());
        values.add(assetMasterDataDto.getWarrantyAmc());
        values.add(assetMasterDataDto.getCreatedOn());
        values.add(assetMasterDataDto.getCreatedBy());
        values.add(assetMasterDataDto.getLocationPosition());
        values.add(assetMasterDataDto.getLastUpdatedStamp());
        values.add(assetMasterDataDto.getLastUpdatedTxStamp());
        values.add(assetMasterDataDto.getCreatedStamp());
        values.add(assetMasterDataDto.getCreatedTxStamp());
        values.add(assetMasterDataDto.getType());
        values.add(assetMasterDataDto.getMajorSection());
        values.add(assetMasterDataDto.getAdeeSection());
        values.add(assetMasterDataDto.getNamePlateDetails());
        values.add(assetMasterDataDto.getEnd1Side1());
        values.add(assetMasterDataDto.getEnd2Side2());
        values.add(assetMasterDataDto.getStation());
        values.add(assetMasterDataDto.getLugDate());
        values.add(assetMasterDataDto.getRemark1());
        values.add(assetMasterDataDto.getRemark2());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_MASTER_DATA_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertAssetMasterData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateAssetMasterData(AssetMasterDataDto assetMasterDataDto, SQLiteDatabase db) {


        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetMasterDataDto.getAssetType());
        values.add(assetMasterDataDto.getAssetId());
        values.add(assetMasterDataDto.getFacilityId());
        values.add(assetMasterDataDto.getElementarySection());
        values.add(assetMasterDataDto.getRlyAssignedSerial());
        values.add(assetMasterDataDto.getWarrantyAmc());
        values.add(assetMasterDataDto.getCreatedOn());
        values.add(assetMasterDataDto.getCreatedBy());
        values.add(assetMasterDataDto.getLocationPosition());
        values.add(assetMasterDataDto.getLastUpdatedStamp());
        values.add(assetMasterDataDto.getLastUpdatedTxStamp());
        values.add(assetMasterDataDto.getCreatedStamp());
        values.add(assetMasterDataDto.getCreatedTxStamp());
        values.add(assetMasterDataDto.getSeqId());
        values.add(assetMasterDataDto.getType());
        values.add(assetMasterDataDto.getMajorSection());
        values.add(assetMasterDataDto.getAdeeSection());
        values.add(assetMasterDataDto.getNamePlateDetails());
        values.add(assetMasterDataDto.getEnd1Side1());
        values.add(assetMasterDataDto.getEnd2Side2());
        values.add(assetMasterDataDto.getStation());
        values.add(assetMasterDataDto.getLugDate());
        values.add(assetMasterDataDto.getRemark1());
        values.add(assetMasterDataDto.getRemark2());

       // Log.d(TAG,"fac id in AMD ::*::" + assetMasterDataDto.getFacilityId());
        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_MASTER_DATA_UPDATE_SQL, values.toArray(new String[values.size()]), db);
        } catch (Exception e){

            Log.e(TAG, "updateAssetMasterData - : " + e.getMessage());
        }

        return result;
    }



    public boolean insertAssetMonthlyTargetsData(AssetMonthlyTargetsDto assetMonthlyTargetsDto,SQLiteDatabase db)
    {
        boolean result=false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetMonthlyTargetsDto.getSeqId());
        values.add(assetMonthlyTargetsDto.getFacilityId());
        values.add(assetMonthlyTargetsDto.getElementarySection());
        values.add(assetMonthlyTargetsDto.getScheduleType());
        values.add(assetMonthlyTargetsDto.getAssetType());
        values.add(assetMonthlyTargetsDto.getTargetJan());
        values.add(assetMonthlyTargetsDto.getTargetFeb());
        values.add(assetMonthlyTargetsDto.getTargetMar());
        values.add(assetMonthlyTargetsDto.getTargetApr());
        values.add(assetMonthlyTargetsDto.getTargetMay());
        values.add(assetMonthlyTargetsDto.getTargetJune());
        values.add(assetMonthlyTargetsDto.getTargetJuly());
        values.add(assetMonthlyTargetsDto.getTargetAug());
        values.add(assetMonthlyTargetsDto.getTargetSep());
        values.add(assetMonthlyTargetsDto.getTargetOct());
        values.add(assetMonthlyTargetsDto.getTargetNov());
        values.add(assetMonthlyTargetsDto.getTargetDec());
        values.add(assetMonthlyTargetsDto.getYear());
        values.add(assetMonthlyTargetsDto.getCreatedOn());
        values.add(assetMonthlyTargetsDto.getCreatedBy());
        values.add(assetMonthlyTargetsDto.getLastUpdatedStamp());
        values.add(assetMonthlyTargetsDto.getLastUpdatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_MONTHLY_TARGETS_INSERT_SQL, values.toArray(new String[values.size()]), db);
        }catch (Exception e)
        {
            Log.e(TAG, "insertAssetMonthlyTargetsData :-  " +e.getMessage());
        }

        return result;
    }


    public boolean updateAssetMonthlyTargetsData(AssetMonthlyTargetsDto assetMonthlyTargetsDto,SQLiteDatabase db)
    {
        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetMonthlyTargetsDto.getSeqId());
        values.add(assetMonthlyTargetsDto.getFacilityId());
        values.add(assetMonthlyTargetsDto.getElementarySection());
        values.add(assetMonthlyTargetsDto.getScheduleType());
        values.add(assetMonthlyTargetsDto.getAssetType());
        values.add(assetMonthlyTargetsDto.getTargetJan());
        values.add(assetMonthlyTargetsDto.getTargetFeb());
        values.add(assetMonthlyTargetsDto.getTargetMar());
        values.add(assetMonthlyTargetsDto.getTargetApr());
        values.add(assetMonthlyTargetsDto.getTargetMay());
        values.add(assetMonthlyTargetsDto.getTargetJune());
        values.add(assetMonthlyTargetsDto.getTargetJuly());
        values.add(assetMonthlyTargetsDto.getTargetAug());
        values.add(assetMonthlyTargetsDto.getTargetSep());
        values.add(assetMonthlyTargetsDto.getTargetOct());
        values.add(assetMonthlyTargetsDto.getTargetNov());
        values.add(assetMonthlyTargetsDto.getTargetDec());
        values.add(assetMonthlyTargetsDto.getYear());
        values.add(assetMonthlyTargetsDto.getCreatedOn());
        values.add(assetMonthlyTargetsDto.getCreatedBy());
        values.add(assetMonthlyTargetsDto.getLastUpdatedStamp());
        values.add(assetMonthlyTargetsDto.getLastUpdatedTxStamp());


        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_MONTHLY_TARGETS_UPDATE_SQL, values.toArray(new String[values.size()]), db);
        }catch (Exception e)
        {
            Log.e(TAG, "insertAssetMonthlyTargetsData :-  " +e.getMessage());
        }


        return result;
    }


    public boolean insertAssetStatusUpdateData(AssetStatusUpdateDto assetStatusUpdateDto , SQLiteDatabase db)
    {
        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetStatusUpdateDto.getSeqId());
        values.add(assetStatusUpdateDto.getCreatedDate());
        values.add(assetStatusUpdateDto.getAssetId());
        values.add(assetStatusUpdateDto.getAssetType());
        values.add(assetStatusUpdateDto.getFacilityId());
        values.add(assetStatusUpdateDto.getDateOfStatus());
        values.add(assetStatusUpdateDto.getStatus());
        values.add(assetStatusUpdateDto.getDefectObserved());
        values.add(assetStatusUpdateDto.getReasonOfStatusChange());
        values.add(assetStatusUpdateDto.getSchedule());
        values.add(assetStatusUpdateDto.getRemarks());
        values.add(assetStatusUpdateDto.getCurrentStatus());
        values.add(assetStatusUpdateDto.getCreatedBy());
        values.add(assetStatusUpdateDto.getCreatedOn());
        values.add(assetStatusUpdateDto.getLastUpdatedStamp());
        values.add(assetStatusUpdateDto.getLastUpdatedTxStamp());


        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_STATUS_UPDATE_INSERT_SQL, values.toArray(new String[values.size()]), db);
        }catch (Exception e)
        {
            Log.e(TAG, "insertAssetStatusUpdateData :-  " +e.getMessage());
        }

        return result;
    }


    public boolean updateAssetStatusUpdateData(AssetStatusUpdateDto assetStatusUpdateDto , SQLiteDatabase db)
    {
        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetStatusUpdateDto.getSeqId());
        values.add(assetStatusUpdateDto.getCreatedDate());
        values.add(assetStatusUpdateDto.getAssetId());
        values.add(assetStatusUpdateDto.getAssetType());
        values.add(assetStatusUpdateDto.getFacilityId());
        values.add(assetStatusUpdateDto.getDateOfStatus());
        values.add(assetStatusUpdateDto.getStatus());
        values.add(assetStatusUpdateDto.getDefectObserved());
        values.add(assetStatusUpdateDto.getReasonOfStatusChange());
        values.add(assetStatusUpdateDto.getSchedule());
        values.add(assetStatusUpdateDto.getRemarks());
        values.add(assetStatusUpdateDto.getCurrentStatus());
        values.add(assetStatusUpdateDto.getCreatedBy());
        values.add(assetStatusUpdateDto.getCreatedOn());
        values.add(assetStatusUpdateDto.getLastUpdatedStamp());
        values.add(assetStatusUpdateDto.getLastUpdatedTxStamp());



        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_STATUS_UPDATE_UPDATE_SQL, values.toArray(new String[values.size()]), db);
        }catch (Exception e)
        {
            Log.e(TAG, "updateAssetStatusUpdateData :-  " +e.getMessage());
        }

        return result;
    }

        public boolean insertProductData(ProductDto productDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(productDto.getProductId());
        values.add(productDto.getProductTypeId());
        values.add(productDto.getInternalName());
        values.add(productDto.getProductName());
        values.add(productDto.getBillOfMaterialLevel());
        values.add(productDto.getIsActive());
        values.add(productDto.getProductCodeTypeId());
        values.add(productDto.getPlNo());
        values.add(productDto.getRlyId());
        values.add(productDto.getTrdDivId());
        values.add(productDto.getLastUpdatedStamp());
        values.add(productDto.getLastUpdatedTxStamp());
        values.add(productDto.getCreatedStamp());
        values.add(productDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.PRODUCT_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "syncProductData - : " + e.getMessage());
        }
        return result;
    }

    public boolean updateProductData(ProductDto productDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(productDto.getProductTypeId());
        values.add(productDto.getInternalName());
        values.add(productDto.getProductName());
        values.add(productDto.getBillOfMaterialLevel());
        values.add(productDto.getIsActive());
        values.add(productDto.getProductCodeTypeId());
        values.add(productDto.getPlNo());
        values.add(productDto.getRlyId());
        values.add(productDto.getTrdDivId());
        values.add(productDto.getLastUpdatedStamp());
        values.add(productDto.getLastUpdatedTxStamp());
        values.add(productDto.getCreatedStamp());
        values.add(productDto.getCreatedTxStamp());
        values.add(productDto.getProductId());
        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.PRODUCT_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateProductData - : " + e.getMessage());
        }
        return result;
    }

    public boolean insertMeasureOrActivityData(MeasureOrActivityListDto measureOrActivityListDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(measureOrActivityListDto.getSeqId());
        values.add(measureOrActivityListDto.getActivityId());
        values.add(measureOrActivityListDto.getActivityName());
        values.add(measureOrActivityListDto.getActivityType());
        values.add(measureOrActivityListDto.getUnitOfMeasure());
        values.add(measureOrActivityListDto.getDescription());
        values.add(measureOrActivityListDto.getCreatedOn());
        values.add(measureOrActivityListDto.getCreatedBy());
        values.add(measureOrActivityListDto.getLastUpdatedStamp());
        values.add(measureOrActivityListDto.getLastUpdatedTxStamp());
        values.add(measureOrActivityListDto.getCreatedStamp());
        values.add(measureOrActivityListDto.getCreatedTxStamp());
        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.MEASURE_OR_ACTIVITY_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertMeasureOrActivityData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateMeasureOrActivityData(MeasureOrActivityListDto measureOrActivityListDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(measureOrActivityListDto.getActivityId());
        values.add(measureOrActivityListDto.getActivityName());
        values.add(measureOrActivityListDto.getActivityType());
        values.add(measureOrActivityListDto.getUnitOfMeasure());
        values.add(measureOrActivityListDto.getDescription());
        values.add(measureOrActivityListDto.getCreatedOn());
        values.add(measureOrActivityListDto.getCreatedBy());
        values.add(measureOrActivityListDto.getLastUpdatedStamp());
        values.add(measureOrActivityListDto.getLastUpdatedTxStamp());
        values.add(measureOrActivityListDto.getCreatedStamp());
        values.add(measureOrActivityListDto.getCreatedTxStamp());
        values.add(measureOrActivityListDto.getSeqId());
        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.MEASURE_OR_ACTIVITY_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateMeasureOrActivityData - : " + e.getMessage());
        }

        return result;
    }

    public boolean insertProductCategoryMemberData(ProductCategoryMemberDto productCategoryMemberDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(productCategoryMemberDto.getComments());
        values.add(productCategoryMemberDto.getFromDate());
        values.add(productCategoryMemberDto.getProductCategoryId());
        values.add(productCategoryMemberDto.getProductId());
        values.add(productCategoryMemberDto.getQuantity());
        values.add(productCategoryMemberDto.getSequenceNum());
        values.add(productCategoryMemberDto.getThruDate());
        values.add(productCategoryMemberDto.getCreatedStamp());
        values.add(productCategoryMemberDto.getCreatedTxStamp());
        values.add(productCategoryMemberDto.getLastUpdatedStamp());
        values.add(productCategoryMemberDto.getLastUpdatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.PRODUCT_CATEGORY_MEMBER_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertProductCategoryMemberData - : " + e.getMessage());
        }
        return result;
    }

    public boolean updateProductCategoryMemberData(ProductCategoryMemberDto productCategoryMemberDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(productCategoryMemberDto.getComments());
        values.add(productCategoryMemberDto.getFromDate());
        values.add(productCategoryMemberDto.getProductCategoryId());
        values.add(productCategoryMemberDto.getProductId());
        values.add(productCategoryMemberDto.getQuantity());
        values.add(productCategoryMemberDto.getSequenceNum());
        values.add(productCategoryMemberDto.getThruDate());
        values.add(productCategoryMemberDto.getCreatedStamp());
        values.add(productCategoryMemberDto.getCreatedTxStamp());
        values.add(productCategoryMemberDto.getLastUpdatedStamp());
        values.add(productCategoryMemberDto.getLastUpdatedTxStamp());


        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.PRODUCT_CATEGORY_MEMBER_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updatedProductCategoryMemberData - : " + e.getMessage());
        }
        return result;
    }



    public boolean insertAssetsScheduleHistoryData(AssetsScheduleHistoryDto assetsScheduleHistoryDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        values.add(assetsScheduleHistoryDto.getSeqId());
        values.add(assetsScheduleHistoryDto.getFacilityId());
        values.add(assetsScheduleHistoryDto.getPbOperationSeqId());
        values.add(assetsScheduleHistoryDto.getDeviceId());
        values.add(assetsScheduleHistoryDto.getDeviceSeqId());
        values.add(assetsScheduleHistoryDto.getAssetType());
        values.add(assetsScheduleHistoryDto.getAssetId());
        values.add(assetsScheduleHistoryDto.getScheduleCode());
        values.add(assetsScheduleHistoryDto.getScheduleDate());
        values.add(assetsScheduleHistoryDto.getStatus());
        values.add(assetsScheduleHistoryDto.getDetailsOfMaint());
        values.add(assetsScheduleHistoryDto.getDoneBy());
        values.add(assetsScheduleHistoryDto.getInitialOfIncharge());
        values.add(assetsScheduleHistoryDto.getRemarks());
        values.add(assetsScheduleHistoryDto.getCreatedOn());
        values.add(assetsScheduleHistoryDto.getCreatedBy());
        values.add(assetsScheduleHistoryDto.getLastUpdatedStamp());
        values.add(assetsScheduleHistoryDto.getLastUpdatedTxStamp());
        values.add(assetsScheduleHistoryDto.getCreatedStamp());
        values.add(assetsScheduleHistoryDto.getCreatedTxStamp());
        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSETS_SCHEDULE_HISTORY_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertAssetsScheduleHistoryData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateAssetsScheduleHistoryData(AssetsScheduleHistoryDto assetsScheduleHistoryDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetsScheduleHistoryDto.getSeqId());
        values.add(assetsScheduleHistoryDto.getFacilityId());
        values.add(assetsScheduleHistoryDto.getPbOperationSeqId());
        values.add(assetsScheduleHistoryDto.getDeviceId());
        values.add(assetsScheduleHistoryDto.getDeviceSeqId());
        values.add(assetsScheduleHistoryDto.getAssetType());
        values.add(assetsScheduleHistoryDto.getAssetId());
        values.add(assetsScheduleHistoryDto.getScheduleCode());
        values.add(assetsScheduleHistoryDto.getScheduleDate());
        values.add(assetsScheduleHistoryDto.getStatus());
        values.add(assetsScheduleHistoryDto.getDetailsOfMaint());
        values.add(assetsScheduleHistoryDto.getDoneBy());
        values.add(assetsScheduleHistoryDto.getInitialOfIncharge());
        values.add(assetsScheduleHistoryDto.getRemarks());
        values.add(assetsScheduleHistoryDto.getCreatedOn());
        values.add(assetsScheduleHistoryDto.getCreatedBy());
        values.add(assetsScheduleHistoryDto.getLastUpdatedStamp());
        values.add(assetsScheduleHistoryDto.getLastUpdatedTxStamp());
        values.add(assetsScheduleHistoryDto.getCreatedStamp());
        values.add(assetsScheduleHistoryDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSETS_SCHEDULE_HISTORY_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateAssetsScheduleHistoryData - : " + e.getMessage());
        }

        return result;
    }


    public boolean insertAssetScheduleActivityRecordData(AssetScheduleActivityRecordDto assetScheduleActivityRecordDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetScheduleActivityRecordDto.getAssetMeasureObserSeqId());
        values.add(assetScheduleActivityRecordDto.getFacilityId());
        values.add(assetScheduleActivityRecordDto.getAssetType());
        values.add(assetScheduleActivityRecordDto.getAssetId());
        values.add(assetScheduleActivityRecordDto.getScheduleCode());
        values.add(assetScheduleActivityRecordDto.getScheduleDate());
        values.add(assetScheduleActivityRecordDto.getScheduleActualDate());
        values.add(assetScheduleActivityRecordDto.getAssetScheduleHistoryId());
        values.add(assetScheduleActivityRecordDto.getStatus());
        values.add(assetScheduleActivityRecordDto.getDeviceId());
        values.add(assetScheduleActivityRecordDto.getDeviceSeqId());
        values.add(assetScheduleActivityRecordDto.getDeviceCreatedStamp());
        values.add(assetScheduleActivityRecordDto.getDeviceLastUpdatedStamp());
        values.add(assetScheduleActivityRecordDto.getM1());
        values.add(assetScheduleActivityRecordDto.getM2());
        values.add(assetScheduleActivityRecordDto.getM3());
        values.add(assetScheduleActivityRecordDto.getM4());
        values.add(assetScheduleActivityRecordDto.getM5());
        values.add(assetScheduleActivityRecordDto.getM6());
        values.add(assetScheduleActivityRecordDto.getM7());
        values.add(assetScheduleActivityRecordDto.getM8());
        values.add(assetScheduleActivityRecordDto.getM9());
        values.add(assetScheduleActivityRecordDto.getM10());
        values.add(assetScheduleActivityRecordDto.getM11());
        values.add(assetScheduleActivityRecordDto.getM12());
        values.add(assetScheduleActivityRecordDto.getM13());
        values.add(assetScheduleActivityRecordDto.getM14());
        values.add(assetScheduleActivityRecordDto.getM15());
        values.add(assetScheduleActivityRecordDto.getM16());
        values.add(assetScheduleActivityRecordDto.getM17());
        values.add(assetScheduleActivityRecordDto.getM18());
        values.add(assetScheduleActivityRecordDto.getM19());
        values.add(assetScheduleActivityRecordDto.getM20());
        values.add(assetScheduleActivityRecordDto.getM21());
        values.add(assetScheduleActivityRecordDto.getM22());
        values.add(assetScheduleActivityRecordDto.getM23());
        values.add(assetScheduleActivityRecordDto.getM24());
        values.add(assetScheduleActivityRecordDto.getM25());
        values.add(assetScheduleActivityRecordDto.getM26());
        values.add(assetScheduleActivityRecordDto.getM27());
        values.add(assetScheduleActivityRecordDto.getM28());
        values.add(assetScheduleActivityRecordDto.getM29());
        values.add(assetScheduleActivityRecordDto.getM30());
        values.add(assetScheduleActivityRecordDto.getM31());
        values.add(assetScheduleActivityRecordDto.getM32());
        values.add(assetScheduleActivityRecordDto.getM33());
        values.add(assetScheduleActivityRecordDto.getM34());
        values.add(assetScheduleActivityRecordDto.getM35());
        values.add(assetScheduleActivityRecordDto.getM36());
        values.add(assetScheduleActivityRecordDto.getM37());
        values.add(assetScheduleActivityRecordDto.getM38());
        values.add(assetScheduleActivityRecordDto.getM39());
        values.add(assetScheduleActivityRecordDto.getM40());
        values.add(assetScheduleActivityRecordDto.getM41());
        values.add(assetScheduleActivityRecordDto.getM42());
        values.add(assetScheduleActivityRecordDto.getM43());
        values.add(assetScheduleActivityRecordDto.getM44());
        values.add(assetScheduleActivityRecordDto.getM45());
        values.add(assetScheduleActivityRecordDto.getM46());
        values.add(assetScheduleActivityRecordDto.getM47());
        values.add(assetScheduleActivityRecordDto.getM48());
        values.add(assetScheduleActivityRecordDto.getM49());
        values.add(assetScheduleActivityRecordDto.getM50());
        values.add(assetScheduleActivityRecordDto.getA1());
        values.add(assetScheduleActivityRecordDto.getA2());
        values.add(assetScheduleActivityRecordDto.getA3());
        values.add(assetScheduleActivityRecordDto.getA4());
        values.add(assetScheduleActivityRecordDto.getA5());
        values.add(assetScheduleActivityRecordDto.getA6());
        values.add(assetScheduleActivityRecordDto.getA7());
        values.add(assetScheduleActivityRecordDto.getA8());
        values.add(assetScheduleActivityRecordDto.getA9());
        values.add(assetScheduleActivityRecordDto.getA10());
        values.add(assetScheduleActivityRecordDto.getA11());
        values.add(assetScheduleActivityRecordDto.getA12());
        values.add(assetScheduleActivityRecordDto.getA13());
        values.add(assetScheduleActivityRecordDto.getA14());
        values.add(assetScheduleActivityRecordDto.getA15());
        values.add(assetScheduleActivityRecordDto.getA16());
        values.add(assetScheduleActivityRecordDto.getA17());
        values.add(assetScheduleActivityRecordDto.getA18());
        values.add(assetScheduleActivityRecordDto.getA19());
        values.add(assetScheduleActivityRecordDto.getA20());
        values.add(assetScheduleActivityRecordDto.getA21());
        values.add(assetScheduleActivityRecordDto.getA22());
        values.add(assetScheduleActivityRecordDto.getA23());
        values.add(assetScheduleActivityRecordDto.getA24());
        values.add(assetScheduleActivityRecordDto.getA25());
        values.add(assetScheduleActivityRecordDto.getA26());
        values.add(assetScheduleActivityRecordDto.getA27());
        values.add(assetScheduleActivityRecordDto.getA28());
        values.add(assetScheduleActivityRecordDto.getA29());
        values.add(assetScheduleActivityRecordDto.getA30());
        values.add(assetScheduleActivityRecordDto.getA31());
        values.add(assetScheduleActivityRecordDto.getA32());
        values.add(assetScheduleActivityRecordDto.getA33());
        values.add(assetScheduleActivityRecordDto.getA34());
        values.add(assetScheduleActivityRecordDto.getA35());
        values.add(assetScheduleActivityRecordDto.getA36());
        values.add(assetScheduleActivityRecordDto.getA37());
        values.add(assetScheduleActivityRecordDto.getA38());
        values.add(assetScheduleActivityRecordDto.getA39());
        values.add(assetScheduleActivityRecordDto.getA40());
        values.add(assetScheduleActivityRecordDto.getA41());
        values.add(assetScheduleActivityRecordDto.getA42());
        values.add(assetScheduleActivityRecordDto.getA43());
        values.add(assetScheduleActivityRecordDto.getA44());
        values.add(assetScheduleActivityRecordDto.getA45());
        values.add(assetScheduleActivityRecordDto.getA46());
        values.add(assetScheduleActivityRecordDto.getA47());
        values.add(assetScheduleActivityRecordDto.getA48());
        values.add(assetScheduleActivityRecordDto.getA49());
        values.add(assetScheduleActivityRecordDto.getA50());
        values.add(assetScheduleActivityRecordDto.getMma1());
        values.add(assetScheduleActivityRecordDto.getMma2());
        values.add(assetScheduleActivityRecordDto.getMma3());
        values.add(assetScheduleActivityRecordDto.getMma4());
        values.add(assetScheduleActivityRecordDto.getMma5());
        values.add(assetScheduleActivityRecordDto.getMma6());
        values.add(assetScheduleActivityRecordDto.getMma7());
        values.add(assetScheduleActivityRecordDto.getMma8());
        values.add(assetScheduleActivityRecordDto.getMma9());
        values.add(assetScheduleActivityRecordDto.getMma10());
        values.add(assetScheduleActivityRecordDto.getMake());
        values.add(assetScheduleActivityRecordDto.getModel());
        values.add(assetScheduleActivityRecordDto.getDetailsOfMaint());
        values.add(assetScheduleActivityRecordDto.getDoneBy());
        values.add(assetScheduleActivityRecordDto.getInitialOfIncharge());
        values.add(assetScheduleActivityRecordDto.getRemarks());
        values.add(assetScheduleActivityRecordDto.getCreatedOn());
        values.add(assetScheduleActivityRecordDto.getCreatedBy());
        values.add(assetScheduleActivityRecordDto.getLastUpdatedStamp());
        values.add(assetScheduleActivityRecordDto.getLastUpdatedTxStamp());
        values.add(assetScheduleActivityRecordDto.getCreatedStamp());
        values.add(assetScheduleActivityRecordDto.getCreatedTxStamp());


        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.ASSET_SCHEDULE_ACTIVITY_RECORD_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertAssetScheduleActivityRecordData - : " + e.getMessage());
        }

        return result;
    }

    public boolean updateAssetScheduleActivityRecordData(AssetScheduleActivityRecordDto assetScheduleActivityRecordDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(assetScheduleActivityRecordDto.getAssetMeasureObserSeqId());
        values.add(assetScheduleActivityRecordDto.getFacilityId());
        values.add(assetScheduleActivityRecordDto.getAssetType());
        values.add(assetScheduleActivityRecordDto.getAssetId());
        values.add(assetScheduleActivityRecordDto.getScheduleCode());
        values.add(assetScheduleActivityRecordDto.getScheduleDate());
        values.add(assetScheduleActivityRecordDto.getScheduleActualDate());
        values.add(assetScheduleActivityRecordDto.getAssetScheduleHistoryId());
        values.add(assetScheduleActivityRecordDto.getStatus());
        values.add(assetScheduleActivityRecordDto.getDeviceId());
        values.add(assetScheduleActivityRecordDto.getDeviceSeqId());
        values.add(assetScheduleActivityRecordDto.getDeviceCreatedStamp());
        values.add(assetScheduleActivityRecordDto.getDeviceLastUpdatedStamp());
        values.add(assetScheduleActivityRecordDto.getM1());
        values.add(assetScheduleActivityRecordDto.getM2());
        values.add(assetScheduleActivityRecordDto.getM3());
        values.add(assetScheduleActivityRecordDto.getM4());
        values.add(assetScheduleActivityRecordDto.getM5());
        values.add(assetScheduleActivityRecordDto.getM6());
        values.add(assetScheduleActivityRecordDto.getM7());
        values.add(assetScheduleActivityRecordDto.getM8());
        values.add(assetScheduleActivityRecordDto.getM9());
        values.add(assetScheduleActivityRecordDto.getM10());
        values.add(assetScheduleActivityRecordDto.getM11());
        values.add(assetScheduleActivityRecordDto.getM12());
        values.add(assetScheduleActivityRecordDto.getM13());
        values.add(assetScheduleActivityRecordDto.getM14());
        values.add(assetScheduleActivityRecordDto.getM15());
        values.add(assetScheduleActivityRecordDto.getM16());
        values.add(assetScheduleActivityRecordDto.getM17());
        values.add(assetScheduleActivityRecordDto.getM18());
        values.add(assetScheduleActivityRecordDto.getM19());
        values.add(assetScheduleActivityRecordDto.getM20());
        values.add(assetScheduleActivityRecordDto.getM21());
        values.add(assetScheduleActivityRecordDto.getM22());
        values.add(assetScheduleActivityRecordDto.getM23());
        values.add(assetScheduleActivityRecordDto.getM24());
        values.add(assetScheduleActivityRecordDto.getM25());
        values.add(assetScheduleActivityRecordDto.getM26());
        values.add(assetScheduleActivityRecordDto.getM27());
        values.add(assetScheduleActivityRecordDto.getM28());
        values.add(assetScheduleActivityRecordDto.getM29());
        values.add(assetScheduleActivityRecordDto.getM30());
        values.add(assetScheduleActivityRecordDto.getM31());
        values.add(assetScheduleActivityRecordDto.getM32());
        values.add(assetScheduleActivityRecordDto.getM33());
        values.add(assetScheduleActivityRecordDto.getM34());
        values.add(assetScheduleActivityRecordDto.getM35());
        values.add(assetScheduleActivityRecordDto.getM36());
        values.add(assetScheduleActivityRecordDto.getM37());
        values.add(assetScheduleActivityRecordDto.getM38());
        values.add(assetScheduleActivityRecordDto.getM39());
        values.add(assetScheduleActivityRecordDto.getM40());
        values.add(assetScheduleActivityRecordDto.getM41());
        values.add(assetScheduleActivityRecordDto.getM42());
        values.add(assetScheduleActivityRecordDto.getM43());
        values.add(assetScheduleActivityRecordDto.getM44());
        values.add(assetScheduleActivityRecordDto.getM45());
        values.add(assetScheduleActivityRecordDto.getM46());
        values.add(assetScheduleActivityRecordDto.getM47());
        values.add(assetScheduleActivityRecordDto.getM48());
        values.add(assetScheduleActivityRecordDto.getM49());
        values.add(assetScheduleActivityRecordDto.getM50());
        values.add(assetScheduleActivityRecordDto.getA1());
        values.add(assetScheduleActivityRecordDto.getA2());
        values.add(assetScheduleActivityRecordDto.getA3());
        values.add(assetScheduleActivityRecordDto.getA4());
        values.add(assetScheduleActivityRecordDto.getA5());
        values.add(assetScheduleActivityRecordDto.getA6());
        values.add(assetScheduleActivityRecordDto.getA7());
        values.add(assetScheduleActivityRecordDto.getA8());
        values.add(assetScheduleActivityRecordDto.getA9());
        values.add(assetScheduleActivityRecordDto.getA10());
        values.add(assetScheduleActivityRecordDto.getA11());
        values.add(assetScheduleActivityRecordDto.getA12());
        values.add(assetScheduleActivityRecordDto.getA13());
        values.add(assetScheduleActivityRecordDto.getA14());
        values.add(assetScheduleActivityRecordDto.getA15());
        values.add(assetScheduleActivityRecordDto.getA16());
        values.add(assetScheduleActivityRecordDto.getA17());
        values.add(assetScheduleActivityRecordDto.getA18());
        values.add(assetScheduleActivityRecordDto.getA19());
        values.add(assetScheduleActivityRecordDto.getA20());
        values.add(assetScheduleActivityRecordDto.getA21());
        values.add(assetScheduleActivityRecordDto.getA22());
        values.add(assetScheduleActivityRecordDto.getA23());
        values.add(assetScheduleActivityRecordDto.getA24());
        values.add(assetScheduleActivityRecordDto.getA25());
        values.add(assetScheduleActivityRecordDto.getA26());
        values.add(assetScheduleActivityRecordDto.getA27());
        values.add(assetScheduleActivityRecordDto.getA28());
        values.add(assetScheduleActivityRecordDto.getA29());
        values.add(assetScheduleActivityRecordDto.getA30());
        values.add(assetScheduleActivityRecordDto.getA31());
        values.add(assetScheduleActivityRecordDto.getA32());
        values.add(assetScheduleActivityRecordDto.getA33());
        values.add(assetScheduleActivityRecordDto.getA34());
        values.add(assetScheduleActivityRecordDto.getA35());
        values.add(assetScheduleActivityRecordDto.getA36());
        values.add(assetScheduleActivityRecordDto.getA37());
        values.add(assetScheduleActivityRecordDto.getA38());
        values.add(assetScheduleActivityRecordDto.getA39());
        values.add(assetScheduleActivityRecordDto.getA40());
        values.add(assetScheduleActivityRecordDto.getA41());
        values.add(assetScheduleActivityRecordDto.getA42());
        values.add(assetScheduleActivityRecordDto.getA43());
        values.add(assetScheduleActivityRecordDto.getA44());
        values.add(assetScheduleActivityRecordDto.getA45());
        values.add(assetScheduleActivityRecordDto.getA46());
        values.add(assetScheduleActivityRecordDto.getA47());
        values.add(assetScheduleActivityRecordDto.getA48());
        values.add(assetScheduleActivityRecordDto.getA49());
        values.add(assetScheduleActivityRecordDto.getA50());
        values.add(assetScheduleActivityRecordDto.getMma1());
        values.add(assetScheduleActivityRecordDto.getMma2());
        values.add(assetScheduleActivityRecordDto.getMma3());
        values.add(assetScheduleActivityRecordDto.getMma4());
        values.add(assetScheduleActivityRecordDto.getMma5());
        values.add(assetScheduleActivityRecordDto.getMma6());
        values.add(assetScheduleActivityRecordDto.getMma7());
        values.add(assetScheduleActivityRecordDto.getMma8());
        values.add(assetScheduleActivityRecordDto.getMma9());
        values.add(assetScheduleActivityRecordDto.getMma10());
        values.add(assetScheduleActivityRecordDto.getMake());
        values.add(assetScheduleActivityRecordDto.getModel());
        values.add(assetScheduleActivityRecordDto.getDetailsOfMaint());
        values.add(assetScheduleActivityRecordDto.getDoneBy());
        values.add(assetScheduleActivityRecordDto.getInitialOfIncharge());
        values.add(assetScheduleActivityRecordDto.getRemarks());
        values.add(assetScheduleActivityRecordDto.getCreatedOn());
        values.add(assetScheduleActivityRecordDto.getCreatedBy());
        values.add(assetScheduleActivityRecordDto.getLastUpdatedStamp());
        values.add(assetScheduleActivityRecordDto.getLastUpdatedTxStamp());
        values.add(assetScheduleActivityRecordDto.getCreatedStamp());
        values.add(assetScheduleActivityRecordDto.getCreatedTxStamp());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.ASSET_SCHEDULE_ACTIVITY_RECORD_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateAssetScheduleActivityRecordData - : " + e.getMessage());
        }

        return result;
    }


    public boolean insertOheLocationData(OheLocationDto oheLocationDto, SQLiteDatabase db)
    {
        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(oheLocationDto.getSeqId());
        values.add(oheLocationDto.getDivision());
        values.add(oheLocationDto.getSection());
        values.add(oheLocationDto.getPwi());
        values.add(oheLocationDto.getOheMast());
        values.add(oheLocationDto.getEngFeature());
        values.add(oheLocationDto.getOheFeature());
        values.add(oheLocationDto.getRemarkOne());
        values.add(oheLocationDto.getRemarkTwo());
        values.add(oheLocationDto.getLatitude());
        values.add(oheLocationDto.getLongitude());
        values.add(oheLocationDto.getAltitude());
        values.add(oheLocationDto.getValidity());
        values.add(oheLocationDto.getSatellites());
        values.add(oheLocationDto.getSpeed());
        values.add(oheLocationDto.getHeading());
        values.add(oheLocationDto.getOheSequence());
        values.add(oheLocationDto.getCurvature());
        values.add(oheLocationDto.getCurvatureRemark());
        values.add(oheLocationDto.getDate());
        values.add(oheLocationDto.getLastUpdatedStamp());
        values.add(oheLocationDto.getLastUpdatedTxStamp());
        values.add(oheLocationDto.getCreatedStamp());
        values.add(oheLocationDto.getCreatedTxStamp());
        values.add(oheLocationDto.getChainage());
        values.add(oheLocationDto.getChainageRemark());
        values.add(oheLocationDto.getStructureType());
        values.add(oheLocationDto.getTrackLine());
        values.add(oheLocationDto.getKilometer());
        values.add(oheLocationDto.getSequenceNo());
        values.add(oheLocationDto.getFacilityId());
        values.add(oheLocationDto.getMajorSection());
        values.add(oheLocationDto.getAdeeSection());


        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.OHE_LOCATION_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insert OheLocation Data - : " + e.getMessage());
        }

        return result;
    }


    public boolean updateOheLocationData(OheLocationDto oheLocationDto, SQLiteDatabase db)
    {
        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(oheLocationDto.getSeqId());
        values.add(oheLocationDto.getDivision());
        values.add(oheLocationDto.getSection());
        values.add(oheLocationDto.getPwi());
        values.add(oheLocationDto.getOheMast());
        values.add(oheLocationDto.getEngFeature());
        values.add(oheLocationDto.getOheFeature());
        values.add(oheLocationDto.getRemarkOne());
        values.add(oheLocationDto.getRemarkTwo());
        values.add(oheLocationDto.getLatitude());
        values.add(oheLocationDto.getLongitude());
        values.add(oheLocationDto.getAltitude());
        values.add(oheLocationDto.getValidity());
        values.add(oheLocationDto.getSatellites());
        values.add(oheLocationDto.getSpeed());
        values.add(oheLocationDto.getHeading());
        values.add(oheLocationDto.getOheSequence());
        values.add(oheLocationDto.getCurvature());
        values.add(oheLocationDto.getCurvatureRemark());
        values.add(oheLocationDto.getDate());
        values.add(oheLocationDto.getLastUpdatedStamp());
        values.add(oheLocationDto.getLastUpdatedTxStamp());
        values.add(oheLocationDto.getCreatedStamp());
        values.add(oheLocationDto.getCreatedTxStamp());
        values.add(oheLocationDto.getChainage());
        values.add(oheLocationDto.getChainageRemark());
        values.add(oheLocationDto.getStructureType());
        values.add(oheLocationDto.getTrackLine());
        values.add(oheLocationDto.getKilometer());
        values.add(oheLocationDto.getSequenceNo());
         values.add(oheLocationDto.getFacilityId());
        values.add(oheLocationDto.getMajorSection());
        values.add(oheLocationDto.getAdeeSection());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.OHE_LOCATION_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "update OheLocation Data - : " + e.getMessage());
        }



        return result;
    }

    public boolean insertUserLoginData(UserLoginDto userLoginDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();
        Log.d(TAG,"userlogin debug**"+userLoginDto.getUserLoginId());
        values.add(userLoginDto.getUserLoginId());
        values.add(userLoginDto.getCurrentPassword());
        values.add(userLoginDto.getPasswordHint());
        values.add(userLoginDto.getIsSystem());
        values.add(userLoginDto.getEnabled());

        values.add(userLoginDto.getLastUpdatedStamp());
        values.add(userLoginDto.getLastUpdatedTxStamp());
        values.add(userLoginDto.getCreatedStamp());
        values.add(userLoginDto.getCreatedTxStamp());


        //values.add(userLoginDto.getDeviceIMEI());

        try {
            result = DataUpdateDAO.getInstance().insertTableData(Constants.USER_LOGIN_INSERT_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "insertUserLoginData - : " + e.getMessage());
        }

        return result;

    }


    public boolean updateUserLoginData(UserLoginDto userLoginDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(userLoginDto.getUserLoginId());
        values.add(userLoginDto.getCurrentPassword());
        values.add(userLoginDto.getPasswordHint());
        values.add(userLoginDto.getIsSystem());
        values.add(userLoginDto.getEnabled());

        values.add(userLoginDto.getLastUpdatedStamp());
        values.add(userLoginDto.getLastUpdatedTxStamp());
        values.add(userLoginDto.getCreatedStamp());
        values.add(userLoginDto.getCreatedTxStamp());

        //values.add(userLoginDto.getDeviceIMEI());

        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.USER_LOGIN_UPDATE_SQL, values.toArray(new String[values.size()]), db);

        } catch (Exception e){

            Log.e(TAG, "updateUserLoginData - : " + e.getMessage());
        }

        return result;

    }

    public boolean insertDeviceData(DeviceDto deviceDto, SQLiteDatabase db) {

        boolean result = false;
                    ArrayList<String> values = new ArrayList<>();
                    values.add(deviceDto.getDeviceIMEI());
                    values.add(deviceDto.getFacilityId());


                    try {
                    result = DataUpdateDAO.getInstance().insertTableData(Constants.DEVICE_INSERT_SQL, values.toArray(new String[values.size()]), db);


                } catch (Exception e){

                    Log.e(TAG, "insertDeviceData - : " + e.getMessage());
                }
          return result;

    }

    public boolean updateDeviceData(DeviceDto deviceDto, SQLiteDatabase db) {

        boolean result = false;
        ArrayList<String> values = new ArrayList<>();

        values.add(deviceDto.getFacilityId());
        values.add(deviceDto.getDeviceIMEI());


        try {
            result = DataUpdateDAO.getInstance().updateTableData(Constants.DEVICE_UPDATE_SQL, values.toArray(new String[values.size()]), db);


        } catch (Exception e){

            Log.e(TAG, "updateDeviceData - : " + e.getMessage());
        }
        return result;

    }

    private boolean insertTableData(String SQL, String[] values, SQLiteDatabase db) {

        boolean result = false;
        SQLiteStatement stmt = null;

        try {

            if (db != null) {

                stmt = db.compileStatement(SQL);

                for (int i = 0; i < values.length; i++) {

                    String value = values[i];

                    if (value == null) {

                        stmt.bindNull(i + 1);
                    } else {

                        stmt.bindString(i + 1, value);
                    }


                }


                result = stmt.executeInsert() < 0;
                stmt.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "inserTableData - " + SQL + e.getMessage());

            result = false;

            if (stmt != null ) {
                stmt.close();
            }



        }


        return result;

    }

    private boolean updateTableData(String SQL, String[] values, SQLiteDatabase db) {

        boolean result = false;
        SQLiteStatement stmt = null;

        try {

            if (db != null) {

                stmt = db.compileStatement(SQL);

                    for (int i = 0; i < values.length; i++) {

                        String value =  values[i];

                        if (value == null) {

                            stmt.bindNull(i+1);

                        } else {

                            stmt.bindString(i+1, value);
                        }
                    }

                    result = stmt.executeUpdateDelete() > 0;

                    stmt.close();

                }



        } catch (Exception e) {

            Log.e(TAG, "updateTableData - " + SQL + e.getMessage());

            result = false;

            if (stmt != null ) {
                stmt.close();
            }


        }

        return result;

    }


    public boolean deleteTableData(String SQL, String[] values, SQLiteDatabase db) {

        boolean result = false;
        SQLiteStatement stmt = null;

        try {

            if (db != null) {

                stmt = db.compileStatement(SQL);

                for (int i = 0; i < values.length; i++) {



                    String value =  values[i];

                    if (value == null) {

                        stmt.bindNull(i+1);

                    } else {

                        stmt.bindString(i+1, value);
                    }
                }

                result = stmt.executeUpdateDelete() > 0;

                stmt.close();

            }



        } catch (Exception e) {

            Log.e(TAG, "deleteTableData - " + SQL + e.getMessage());

            result = false;

            if (stmt != null ) {
                stmt.close();
            }


        }

        return result;

    }












}
