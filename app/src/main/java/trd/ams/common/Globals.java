package trd.ams.common;

import android.support.multidex.MultiDexApplication;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import trd.ams.dto.ElementarySectionsDto;
import trd.ams.dto.LastRecord;

/**
 * Created by hanu on 04-12-2017.
 */

public class Globals extends MultiDexApplication {

    private ArrayList<String> measuresList;
    private ArrayList<String>  activitiesList;
    private String userName;
    private String facilityId;
    private String facilityName;
    private String depoName;
    private String measurementDate;
    private String powerBlockId;
    private String powerBlockName;
    private String elementarySectionCode;
    private String globalIMEI;
    private String assetTypeId;
    private String scheduleCode;
    private String assetId;
    private String scheduleType;
    private String doneBy;
    private String detailsOfMaint;
    private String remarks;
    private String incharge;
    private String entryStatus;
    private LastRecord lastRecord;
    private String dataSyncStatus;
    private String dataSyncStartTime;
    private String dataSyncFinishTime;

    private double veryShortDistance = 0.005;
    private double shortDistance = 0.01;
    private double mediumDistance = 0.02;
    private double longdistance = 0.03;
    private double vicinity = shortDistance;
    private double device_latitude;
    private double device_longitude;
    private double altitude;
    private double RANGE_1  =  0.0025;
    private double RANGE_2  =  0.005;
    private double RANGE_3  =  0.0075;
    private double RANGE_4  =  0.01;
    private double RANGE_5  =  0.0125;
    private double RANGE_6  =  0.015;
    private double RANGE_7  =  0.0175;
    private double RANGE_8  =  0.02;
    private double RANGE_9  =  0.0225;
    private double RANGE_10  =  0.025;

    private String kilolmeter;

    private String HOST_URL_BY_USER;
    private String MASTER_AUTHIMEI_URL_USER;
    private String MASTER_URL_USER;
    private String MASTER_AUTH_URL_USER;
    private String ELEMENTARY_SECTIONS_URL_USER;
    private String ASSET_ID_URL_USER;
    private String DATES_URL_USER;
    private String PROGRESS_TABLE_URL_USER;
    private String ASSET_SCHEDULE_TRACK_URL_USER;
    private String ASSET_SCHEDULE_RECORD_URL_USER;
    private String OVREDUE_URL_USER;

    private int ashCount;
    private int asarCount;
    private int ascCount;
    private String lastSyncTime;
    private String currentSyncTime;
    private String dsRequestTime;
    private String dsResponseTime;
    private String responseStatus;
    private String ashSeqUpdateTime;
    private String finalResponse;

    private ArrayList<String> elementaryList;
    private String assetType;
    private String elementarySection;
    private String date;
    private ArrayList<String> assetList;
    private ArrayList<String> dateList;
    private String thruDate;
    private String fromDate;
    private byte[] picArray;
    private String picName;
    private ByteBuffer buffer;




    private ArrayList<String> descriptionList;
    private ArrayList<String> populationList;
    private ArrayList<String> targetList;
    private ArrayList<String> targetTillMonthList;
    private ArrayList<String> progressList;
    private ArrayList<String> complianceList;
    private ArrayList<String> outstandingList;

    private String reloadTime;
    private String depoId;



    private ArrayList<String> facilityNameList;
    private ArrayList<String> facilityIdList;


    public ArrayList<String> getFacilityNameList() {
        return facilityNameList;
    }
    public void setFacilityNameList(ArrayList<String> facilityNameList) {
        this.facilityNameList = facilityNameList;
    }

    public ArrayList<String> getFacilityIdList() {
        return facilityIdList;
    }
    public void setFacilityIdList(ArrayList<String> facilityIdList) {
        this.facilityIdList = facilityIdList;
    }


    public String getHOST_URL_BY_USER() {
        return HOST_URL_BY_USER;
    }
    public void setHOST_URL_BY_USER(String HOST_URL_BY_USER) {
        this.HOST_URL_BY_USER = HOST_URL_BY_USER;
    }

    public String getMASTER_AUTHIMEI_URL_USER() {
        return MASTER_AUTHIMEI_URL_USER;
    }
    public void setMASTER_AUTHIMEI_URL_USER(String MASTER_AUTHIMEI_URL_USER) {
        this.MASTER_AUTHIMEI_URL_USER = MASTER_AUTHIMEI_URL_USER;
    }

    public String getMASTER_URL_USER() {
        return MASTER_URL_USER;
    }
    public void setMASTER_URL_USER(String MASTER_URL_USER) {
        this.MASTER_URL_USER = MASTER_URL_USER;
    }

    public String getMASTER_AUTH_URL_USER() {
        return MASTER_AUTH_URL_USER;
    }
    public void setMASTER_AUTH_URL_USER(String MASTER_AUTH_URL_USER) {
        this.MASTER_AUTH_URL_USER = MASTER_AUTH_URL_USER;
    }

    public String getPROGRESS_TABLE_URL_USER() {
        return PROGRESS_TABLE_URL_USER;
    }
    public void setPROGRESS_TABLE_URL_USER(String PROGRESS_TABLE_URL_USER) {
        this.PROGRESS_TABLE_URL_USER = PROGRESS_TABLE_URL_USER;
    }

    public String getELEMENTARY_SECTIONS_URL_USER() {
        return ELEMENTARY_SECTIONS_URL_USER;
    }
    public void setELEMENTARY_SECTIONS_URL_USER(String ELEMENTARY_SECTIONS_URL_USER) {
        this.ELEMENTARY_SECTIONS_URL_USER = ELEMENTARY_SECTIONS_URL_USER;
    }

    public String getASSET_ID_URL_USER() {
        return ASSET_ID_URL_USER;
    }
    public void setASSET_ID_URL_USER(String ASSET_ID_URL_USER) {
        this.ASSET_ID_URL_USER = ASSET_ID_URL_USER;
    }

    public String getDATES_URL_USER() {
        return DATES_URL_USER;
    }
    public void setDATES_URL_USER(String DATES_URL_USER) {
        this.DATES_URL_USER = DATES_URL_USER;
    }

    public String getASSET_SCHEDULE_TRACK_URL_USER() {
        return ASSET_SCHEDULE_TRACK_URL_USER;
    }
    public void setASSET_SCHEDULE_TRACK_URL_USER(String ASSET_SCHEDULE_TRACK_URL_USER) {
        this.ASSET_SCHEDULE_TRACK_URL_USER = ASSET_SCHEDULE_TRACK_URL_USER;
    }

    public String getASSET_SCHEDULE_RECORD_URL_USER() {
        return ASSET_SCHEDULE_RECORD_URL_USER;
    }
    public void setASSET_SCHEDULE_RECORD_URL_USER(String ASSET_SCHEDULE_RECORD_URL_USER) {
        this.ASSET_SCHEDULE_RECORD_URL_USER = ASSET_SCHEDULE_RECORD_URL_USER;
    }

    public String getOVREDUE_URL_USER() {
        return OVREDUE_URL_USER;
    }
    public void setOVREDUE_URL_USER(String OVREDUE_URL_USER) {
        this.OVREDUE_URL_USER = OVREDUE_URL_USER;
    }

    public String getKilolmeter() {
        return kilolmeter;
    }
    public void setKilolmeter(String kilolmeter) {
        this.kilolmeter = kilolmeter;
    }

    public double getVeryShortDistance(){return veryShortDistance;}
    public void setVeryShortDistance(double veryShortDistance) {
        this.veryShortDistance = veryShortDistance;
    }

    public double getShortDistance() {
        return shortDistance;
    }
    public void setShortDistance(double shortDistance) {
        this.shortDistance = shortDistance;
    }

    public double getMediumDistance() {
        return mediumDistance;
    }
    public void setMediumDistance(double mediumDistance) {
        this.mediumDistance = mediumDistance;
    }

    public double getLongdistance() {
        return longdistance;
    }
    public void setLongdistance(double longdistance) {
        this.longdistance = longdistance;
    }

    public double getVicinity() {
        return vicinity;
    }
    public void setVicinity(double vicinity) {
        this.vicinity = vicinity;
    }

    public double getDevice_latitude() {
        return device_latitude;
    }
    public void setDevice_latitude(double device_latitude) {
        this.device_latitude = device_latitude;
    }

    public double getDevice_longitude() {
        return device_longitude;
    }
    public void setDevice_longitude(double device_longitude) {
        this.device_longitude = device_longitude;
    }

    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getRANGE_1() {
        return RANGE_1;
    }
    public void setRANGE_1(double RANGE_1) {
        this.RANGE_1 = RANGE_1;
    }

    public double getRANGE_2() {
        return RANGE_2;
    }
    public void setRANGE_2(double RANGE_2) {
        this.RANGE_2 = RANGE_2;
    }

    public double getRANGE_3() {
        return RANGE_3;
    }
    public void setRANGE_3(double RANGE_3) {
        this.RANGE_3 = RANGE_3;
    }

    public double getRANGE_4() {
        return RANGE_4;
    }
    public void setRANGE_4(double RANGE_4) {
        this.RANGE_4 = RANGE_4;
    }

    public double getRANGE_5() {
        return RANGE_5;
    }
    public void setRANGE_5(double RANGE_5) {
        this.RANGE_5 = RANGE_5;
    }

    public double getRANGE_6() {
        return RANGE_6;
    }
    public void setRANGE_6(double RANGE_6) {
        this.RANGE_6 = RANGE_6;
    }

    public double getRANGE_7() {
        return RANGE_7;
    }
    public void setRANGE_7(double RANGE_7) {
        this.RANGE_7 = RANGE_7;
    }

    public double getRANGE_8() {
        return RANGE_8;
    }
    public void setRANGE_8(double RANGE_8) {
        this.RANGE_8 = RANGE_8;
    }

    public double getRANGE_9() {
        return RANGE_9;
    }
    public void setRANGE_9(double RANGE_9) {
        this.RANGE_9 = RANGE_9;
    }

    public double getRANGE_10() {
        return RANGE_10;
    }
    public void setRANGE_10(double RANGE_10) {
        this.RANGE_10 = RANGE_10;
    }

    public ArrayList<String> getMeasuresList() {
        return measuresList;
    }

    public void setMeasuresList(ArrayList<String> measuresList) {
        this.measuresList = measuresList;

    }

    public String getGlobalIMEI() {
        return globalIMEI;
    }

    public void setGlobalIMEI(String GlobalIMEI) {
        this.globalIMEI = globalIMEI;
    }

    public String getDataSyncStatus() {
        return dataSyncStatus;
    }

    public void setDataSyncStatus(String dataSyncStatus) {
        this.dataSyncStatus = dataSyncStatus;
    }


    public String getDataSyncStartTime() {
        return dataSyncStartTime;
    }

    public void setDataSyncStartTime(String dataSyncStartTime) {
        this.dataSyncStartTime = dataSyncStartTime;
    }

    public String getDataSyncFinishTime() {
        return dataSyncFinishTime;
    }

    public void setDataSyncFinishTime(String dataSyncFinishTime) {
        this.dataSyncFinishTime = dataSyncFinishTime;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String>  getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(ArrayList<String>  activitiesList) {
        this.activitiesList = activitiesList;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }


    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getElementarySectionCode() {
        return elementarySectionCode;
    }

    public void setElementarySectionCode(String elementarySectionCode) {
        this.elementarySectionCode = elementarySectionCode;
    }

    public String getPowerBlockId() {
        return powerBlockId;
    }

    public void setPowerBlockId(String powerBlockId) {
        this.powerBlockId = powerBlockId;
    }


    public String getPowerBlockName() {
        return powerBlockName;
    }

    public void setPowerBlockName(String powerBlockName) {
        this.powerBlockName = powerBlockName;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        this.scheduleCode = scheduleCode;
    }
    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public String getDetailsOfMaint() {
        return detailsOfMaint;
    }

    public void setDetailsOfMaint(String detailsOfMaint) {
        this.detailsOfMaint = detailsOfMaint;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }


    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge = incharge;
    }

    public LastRecord getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(LastRecord lastRecord) {
        this.lastRecord = lastRecord;
    }

    public int getAshCount() {
        return ashCount;
    }
    public void setAshCount(int ashCount) {
        this.ashCount = ashCount;
    }

    public int getAsarCount() {
        return asarCount;
    }
    public void setAsarCount(int asarCount) {
        this.asarCount = asarCount;
    }

    public int getAscCount() {
        return ascCount;
    }
    public void setAscCount(int ascCount) {
        this.ascCount = ascCount;
    }

    public String getLastSyncTime() {
        return lastSyncTime;
    }
    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getCurrentSyncTime() {
        return currentSyncTime;
    }
    public void setCurrentSyncTime(String currentSyncTime) {
        this.currentSyncTime = currentSyncTime;
    }

    public String getDsRequestTime() {
        return dsRequestTime;
    }
    public void setDsRequestTime(String dsRequestTime) {
        this.dsRequestTime = dsRequestTime;
    }

    public String getDsResponseTime() {
        return dsResponseTime;
    }
    public void setDsResponseTime(String dsResponseTime) {
        this.dsResponseTime = dsResponseTime;
    }

    public String getResponseStatus() {
        return responseStatus;
    }
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getAshSeqUpdateTime() {
        return ashSeqUpdateTime;
    }
    public void setAshSeqUpdateTime(String ashSeqUpdateTime) {
        this.ashSeqUpdateTime = ashSeqUpdateTime;
    }

    public String getFinalResponse() {
        return finalResponse;
    }
    public void setFinalResponse(String finalResponse) {
        this.finalResponse = finalResponse;
    }

    public ArrayList<String> getElementaryList() {
        return elementaryList;
    }
    public void setElementaryList(ArrayList<String> elementaryList) {
        this.elementaryList = elementaryList;
    }

    public String getAssetType() {
        return assetType;
    }
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getElementarySection() {
        return elementarySection;
    }
    public void setElementarySection(String elementarySection) {
        this.elementarySection = elementarySection;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getAssetList() {
        return assetList;
    }
    public void setAssetList(ArrayList<String> assetList) {
        this.assetList = assetList;
    }

    public ArrayList<String> getDateList() {
        return dateList;
    }
    public void setDateList(ArrayList<String> dateList) {
        this.dateList = dateList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getThruDate() {
        return thruDate;
    }

    public void setThruDate(String thruDate) {
        this.thruDate = thruDate;
    }

    public byte[] getPicArray() {
        return picArray;
    }

    public void setPicArray(byte[] picArray) {
        this.picArray = picArray;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }


    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }
    public void setDescriptionList(ArrayList<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

    public ArrayList<String> getPopulationList() {
        return populationList;
    }
    public void setPopulationList(ArrayList<String> populationList) {
        this.populationList = populationList;
    }

    public ArrayList<String> getTargetList() {
        return targetList;
    }
    public void setTargetList(ArrayList<String> targetList) {
        this.targetList = targetList;
    }


    public ArrayList<String> getTargetTillMonthList() {
        return targetTillMonthList;
    }
    public void setTargetTillMonthList(ArrayList<String> targetTillMonthList) {
        this.targetTillMonthList = targetTillMonthList;
    }

    public ArrayList<String> getProgressList() {
        return progressList;
    }
    public void setProgressList(ArrayList<String> progressList) {
        this.progressList = progressList;
    }

    public ArrayList<String> getComplianceList() {
        return complianceList;
    }
    public void setComplianceList(ArrayList<String> complianceList) {
        this.complianceList = complianceList;
    }

    public ArrayList<String> getOutstandingList() {
        return outstandingList;
    }
    public void setOutstandingList(ArrayList<String> outstandingList) {
        this.outstandingList = outstandingList;
    }

    public String getReloadTime() {
        return reloadTime;
    }
    public void setReloadTime(String reloadTime) {
        this.reloadTime = reloadTime;
    }


    public String getDepoName() {
        return depoName;
    }
    public void setDepoName(String depoName) {
        this.depoName = depoName;
    }

    public String getDepoId() {
        return depoId;
    }
    public void setDepoId(String depoId) {
        this.depoId = depoId;
    }

}
