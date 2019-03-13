package trd.ams.dto;

import java.sql.Blob;

public class AssetScheduleContentDto {

    public AssetScheduleContentDto(){
       //  TODO Auto-generated constructor stub
    }

    private String seqId;
    private String contentId;
    private String fromDate;
    private String thruDate;
    private String deviceId;
    private String deviceSeqId;
    private String deviceAshSeqId;
    private String deviceContentId;
    private String fileName;
    private String content;


    public String getSeqId() {
        return seqId;
    }
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getContentId() {
        return contentId;
    }
    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSeqId() {
        return deviceSeqId;
    }
    public void setDeviceSeqId(String deviceSeqId) {
        this.deviceSeqId = deviceSeqId;
    }

    public String getDeviceAshSeqId() {
        return deviceAshSeqId;
    }
    public void setDeviceAshSeqId(String deviceAshSeqId) {
        this.deviceAshSeqId = deviceAshSeqId;
    }

    public String getDeviceContentId() {
        return deviceContentId;
    }
    public void setDeviceContentId(String deviceContentId) {
        this.deviceContentId = deviceContentId;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}

