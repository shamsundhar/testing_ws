package trd.ams.dto;

import trd.ams.activity.DataSyncActivity;

public class DataSyncHistoryDto {

    public DataSyncHistoryDto(){
       //  TODO auto-generated constructor stub
    }

    private String seqId;
    private String deviceId;
    private String lastSyncDateTime;
    private String syncStartDateTime;
    private int ashSentCount;
    private int asarSentCount;
    private int ascSentCount;
    private String dsRequestDateTime;
    private String dsResponseDateTime;
    private String responseStatus;
    private String devSerSeqUpdateDateTime;
    private String finalStatusUpdate;


    public String getSeqId() {
        return seqId;
    }
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLastSyncDateTime() {
        return lastSyncDateTime;
    }
    public void setLastSyncDateTime(String lastSyncDateTime) {
        this.lastSyncDateTime = lastSyncDateTime;
    }

    public String getSyncStartDateTime() {
        return syncStartDateTime;
    }
    public void setSyncStartDateTime(String syncStartDateTime) {
        this.syncStartDateTime = syncStartDateTime;
    }

    public int getAshSentCount() {
        return ashSentCount;
    }
    public void setAshSentCount(int ashSentCount) {
        this.ashSentCount = ashSentCount;
    }

    public int getAsarSentCount() {
        return asarSentCount;
    }
    public void setAsarSentCount(int asarSentCount) {
        this.asarSentCount = asarSentCount;
    }

    public int getAscSentCount() {
        return ascSentCount;
    }
    public void setAscSentCount(int ascSentCount) {
        this.ascSentCount = ascSentCount;
    }

    public String getDsRequestDateTime() {
        return dsRequestDateTime;
    }
    public void setDsRequestDateTime(String dsRequestDateTime) {
        this.dsRequestDateTime = dsRequestDateTime;
    }

    public String getDsResponseDateTime(String dsResponseTime) {
        return dsResponseDateTime;
    }
    public void setDsResponseDateTime(String dsResponseDateTime) {
        this.dsResponseDateTime = dsResponseDateTime;
    }

    public String getResponseStatus(String responseStatus) {
        return this.responseStatus;
    }
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getDevSerSeqUpdateDateTime(String ashSeqUpdateTime) {
        return devSerSeqUpdateDateTime;
    }
    public void setDevSerSeqUpdateDateTime(String devSerSeqUpdateDateTime) {
        this.devSerSeqUpdateDateTime = devSerSeqUpdateDateTime;
    }

    public String getFinalStatusUpdate() {
        return finalStatusUpdate;
    }
    public void setFinalStatusUpdate(String finalStatusUpdate) {
        this.finalStatusUpdate = finalStatusUpdate;
    }



}
