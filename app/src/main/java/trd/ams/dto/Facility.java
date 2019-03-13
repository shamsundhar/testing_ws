package trd.ams.dto;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by hanu on 09-11-2017.
 */

public class Facility {


    private String facilityId ;
    private String facilityName ;

    ArrayList<String> facilityNameList = new ArrayList<>();
    ArrayList<String> facilityIdList = new ArrayList<>();


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

}
