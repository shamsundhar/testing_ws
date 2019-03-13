package trd.ams.dto;


public class PowerBlock {

    private String facilityId;

    private String pbOperationSeqId;
    private String powerBlockName;

    private String typeOfOperation;
    private String elementarySectionCode;
    private String section;

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getPbOperationSeqId() {
        return pbOperationSeqId;
    }

    public void setPbOperationSeqId(String pbOperationSeqId) {
        this.pbOperationSeqId= pbOperationSeqId;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public String getElementarySectionCode() {
        return elementarySectionCode;
    }

    public void setElementarySectionCode(String elementarySectionCode) {
        this.elementarySectionCode = elementarySectionCode;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPowerBlockName() {
        return powerBlockName;
    }

    public void setPowerBlockName(String powerBlockName) {
        this.powerBlockName = powerBlockName;
    }



}




