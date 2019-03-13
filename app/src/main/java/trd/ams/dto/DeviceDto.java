package trd.ams.dto;

public class DeviceDto {

	public DeviceDto() {
		// TODO Auto-generated constructor stub
	}


	private String facilityId, deviceIMEI;


    public String getDeviceIMEI() {
        return deviceIMEI;
    }
    public void setDeviceIMEI(String v) {
        this.deviceIMEI = deviceIMEI;
    }

	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}
