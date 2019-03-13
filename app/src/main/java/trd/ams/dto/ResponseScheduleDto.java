package trd.ams.dto;

import java.util.List;

public class ResponseScheduleDto {

	private List<ScheduleDto> scheduleDtos;
	private Integer count;
	
	public List<ScheduleDto> getScheduleDtos() {
		return scheduleDtos;
	}

	public void setScheduleDtos(List<ScheduleDto> scheduleDtos) {
		this.scheduleDtos = scheduleDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
