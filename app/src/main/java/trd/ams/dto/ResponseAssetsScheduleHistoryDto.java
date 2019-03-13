package trd.ams.dto;

import java.util.List;

public class ResponseAssetsScheduleHistoryDto {

	private List<AssetsScheduleHistoryDto> assetsScheduleHistoryDtos;
	private Integer count;
	
	public List<AssetsScheduleHistoryDto> getAssetsScheduleHistoryDtos() {
		return assetsScheduleHistoryDtos;
	}
	public void setAssetsScheduleHistoryDtos(List<AssetsScheduleHistoryDto> assetsScheduleHistoryDtos) {
		this.assetsScheduleHistoryDtos = assetsScheduleHistoryDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
