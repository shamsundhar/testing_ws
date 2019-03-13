package trd.ams.dto;

import java.util.List;

public class ResponseAssetScheduleActivityRecordDto {

	private List<AssetScheduleActivityRecordDto> assetScheduleActivityRecordDtos;
	private Integer count;
	
	public List<AssetScheduleActivityRecordDto> getAssetScheduleActivityRecordDtos() {
		return assetScheduleActivityRecordDtos;
	}
	public void setAssetScheduleActivityRecordDtos(List<AssetScheduleActivityRecordDto> assetScheduleActivityRecordDtos) {
		this.assetScheduleActivityRecordDtos = assetScheduleActivityRecordDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
