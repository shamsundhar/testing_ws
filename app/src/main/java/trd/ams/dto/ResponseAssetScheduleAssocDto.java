package trd.ams.dto;

import java.util.List;

public class ResponseAssetScheduleAssocDto {

	private List<AssetScheduleAssocDto> assetScheduleAssocDtos;
	private Integer count;
	
	public List<AssetScheduleAssocDto> getAssetScheduleAssocDtos() {
		return assetScheduleAssocDtos;
	}

	public void setAssetScheduleAssocDtos(List<AssetScheduleAssocDto> assetScheduleAssocDtos) {
		this.assetScheduleAssocDtos = assetScheduleAssocDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
