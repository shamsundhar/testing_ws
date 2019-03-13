package trd.ams.dto;

public class AssetScheduleAssocDto {

	public AssetScheduleAssocDto() {
		// TODO Auto-generated constructor stub
	}

	private String asaSeqId;
	private String assetType;
	private String createdBy;
	private String createdOn;
	private String createdStamp;
	private String createdTxStamp;
	private String description;
	private String duration;
	private String isDpr;
	private String lastUpdatedStamp;
	private String lastUpdatedTxStamp;
	private String scheduleCode;
	private String sequenceCode;
	private String uomOfDuration;
	private String targetPlanMonths;
	
	public String getAsaSeqId() {
		return asaSeqId;
	}
	public void setAsaSeqId(String asaSeqId) {
		this.asaSeqId = asaSeqId;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getIsDpr() {
		return isDpr;
	}
	public void setIsDpr(String isDpr) {
		this.isDpr = isDpr;
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
	public String getScheduleCode() {
		return scheduleCode;
	}
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	public String getSequenceCode() {
		return sequenceCode;
	}
	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}
	public String getUomOfDuration() {
		return uomOfDuration;
	}
	public void setUomOfDuration(String uomOfDuration) {
		this.uomOfDuration = uomOfDuration;
	}

	public String getTargetPlanMonths() {
		return targetPlanMonths;
	}
	public void setTargetPlanMonths(String targetPlanMonths) { this.targetPlanMonths = targetPlanMonths; }
}
