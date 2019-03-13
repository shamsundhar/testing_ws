package trd.ams.dto;

import java.util.List;

public class ResponseAssetStatusUpdateDto {

	private List<AssetStatusUpdateDto> assetStatusUpdateDtos;
	private Integer count;
	
	public List<AssetStatusUpdateDto> getAssetStatusUpdateDtos() {
		return assetStatusUpdateDtos;
	}

	public void setAssetStatusUpdateDtos(List<AssetStatusUpdateDto> assetStatusUpdateDtos) {
		this.assetStatusUpdateDtos = assetStatusUpdateDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
