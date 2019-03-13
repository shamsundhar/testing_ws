package trd.ams.dto;

public class AppDeviceUnitDto {

    public AppDeviceUnitDto() {
        // TODO Auto-generated constructor stub
    }




    private String seqId;
    private String appDeviceSeqId;
    private String createdStamp;
    private String createdTxStamp;
    private String lastUpdatedStamp;
    private String lastUpdatedTxStamp;
    private String unitId;
    private String unitType;


    public String getSeqId() {
        return seqId;
    }
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getAppDeviceSeqId() {
        return appDeviceSeqId;
    }
    public void setAppDeviceSeqId(String appDeviceSeqId) {
        this.appDeviceSeqId = appDeviceSeqId;
    }

    public String getCreatedStamp() {
        return createdStamp;
    }
    public void setCreatedStamp(String createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getCreatedTxStamp() {
        return createdTxStamp;
    }
    public void setCreatedTxStamp(String createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    public String getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }
    public void setLastUpdatedStamp(String lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }

    public String getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }
    public void setLastUpdatedTxStamp(String lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitType() {
        return unitType;
    }
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }


}
