package trd.ams.dto;


public class LastRecord {



    private String depoId;

    private String pbOperationSeqId;
    private String assetTypeId;
    private String elementarySectionCode;
    private String scheduleCode;
    private String detailsOfMaintenance;
    private String doneBy;
    private String incharge;
    private String remarks;
    private String status;

    public String getDepoId() {
        return depoId;
    }
    public void setDepoId(String depoId) {
        this.depoId = depoId;
    }

    public String getPbOperationSeqId() {
        return pbOperationSeqId;
    }

    public void setPbOperationSeqId(String pbOperationSeqId) {
        this.pbOperationSeqId= pbOperationSeqId;
    }

    public String getElementarySectionCode() {
        return elementarySectionCode;
    }

    public void setElementarySectionCode(String elementarySectionCode) {
        this.elementarySectionCode = elementarySectionCode;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }

    public void setScheduleCode(String scheduleCode) {
        this.scheduleCode = scheduleCode;
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId;
    }


    public String getDetailsOfMaintenance() {
        return detailsOfMaintenance;
    }

    public void setDetailsOfMaintenance(String detailsOfMaintenance) {
        this.detailsOfMaintenance = detailsOfMaintenance;

    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;

    }
    public String getIncharge() {
        return incharge;
    }

    public void setIncharge(String incharge) {
        this.incharge =incharge;

    }

}




