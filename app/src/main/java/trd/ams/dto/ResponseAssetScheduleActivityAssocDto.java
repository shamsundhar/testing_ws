package trd.ams.dto;

import java.util.List;

public class ResponseAssetScheduleActivityAssocDto {

	private List<AssetScheduleActivityAssocDto> assetScheduleActivityAssocDtos;
	private Integer count;
	
	public List<AssetScheduleActivityAssocDto> getAssetScheduleActivityAssocDtos() {
		return assetScheduleActivityAssocDtos;
	}

	public void setAssetScheduleActivityAssocDtos(List<AssetScheduleActivityAssocDto> assetScheduleActivityAssocDtos) {
		this.assetScheduleActivityAssocDtos = assetScheduleActivityAssocDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
