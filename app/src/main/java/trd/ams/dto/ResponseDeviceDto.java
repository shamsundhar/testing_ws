package trd.ams.dto;

import java.util.List;

public class ResponseDeviceDto {

	private List<DeviceDto> deviceDtos;
	private Integer count;
	
	public List<DeviceDto> getDeviceDtos() {
		return deviceDtos;
	}

	public void setDeviceDtos(List<DeviceDto> deviceDtos) {
		this.deviceDtos = deviceDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
