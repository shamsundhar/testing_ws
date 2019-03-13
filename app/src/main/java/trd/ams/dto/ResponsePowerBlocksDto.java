package trd.ams.dto;

import java.util.List;

public class ResponsePowerBlocksDto {

	private List<PowerBlocksDto> powerBlocksDtos;
	private Integer count;
	
	public List<PowerBlocksDto> getPowerBlocksDtos() {
		return powerBlocksDtos;
	}

	public void setPowerBlocksDtos(List<PowerBlocksDto> powerBlocksDtos) {
		this.powerBlocksDtos = powerBlocksDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
