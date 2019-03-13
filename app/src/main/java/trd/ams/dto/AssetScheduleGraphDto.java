package trd.ams.dto;

public class AssetScheduleGraphDto {

    public AssetScheduleGraphDto(){
        // TODO Auto-generated constructor stub
    }


    private String description;
    private String assetType;
    private String scheduleCode;
    private String population;
    private String target;
    private String targetTillMonth;
    private String progress;
    private String compliance;
    private String outstanding;




    public String getAssetType() {
        return assetType;
    }
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getScheduleCode() {
        return scheduleCode;
    }
    public void setScheduleCode(String scheduleCode) {
        this.scheduleCode = scheduleCode;
    }

    public String getPopulation() {
        return population;
    }
    public void setPopulation(String population) {
        this.population = population;
    }

    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetTillMonth() {
        return targetTillMonth;
    }
    public void setTargetTillMonth(String targetTillMonth) {
        this.targetTillMonth = targetTillMonth;
    }

    public String getProgress() {
        return progress;
    }
    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCompliance() {
        return compliance;
    }
    public void setCompliance(String compliance) {
        this.compliance = compliance;
    }

    public String getOutstanding() {
        return outstanding;
    }
    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
