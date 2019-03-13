package trd.ams.dto;

import java.util.List;

public class  ResponseFacilityDto {

	private List<FacilityDto> facilityDtos;
	private Integer count;
	
	public List<FacilityDto> getFacilityDtos() {
		return facilityDtos;
	}

	public void setFacilityDtos(List<FacilityDto> facilityDtos) {
		this.facilityDtos = facilityDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
