package trd.ams.dto;

import java.util.List;

public class ResponseMeasureOrActivityListDto {

	private List<MeasureOrActivityListDto> measureOrActivityListDtos;
	private Integer count;
	
	public List<MeasureOrActivityListDto> getMeasureOrActivityListDtos() {
		return measureOrActivityListDtos;
	}

	public void setMeasureOrActivityListDtos(List<MeasureOrActivityListDto> measureOrActivityListDtos) {
		this.measureOrActivityListDtos = measureOrActivityListDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
