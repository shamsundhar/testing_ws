package trd.ams.dto;

import java.util.List;

public class ResponseAssetMonthlyTargetsDto {

	private List<AssetMonthlyTargetsDto> assetMonthlyTargetsDtos;
	private Integer count;
	
	public List<AssetMonthlyTargetsDto> getAssetMonthlyTargetsDtos() {
		return assetMonthlyTargetsDtos;
	}

	public void setAssetMonthlyTargetsDtos(List<AssetMonthlyTargetsDto> assetMonthlyTargetsDtos) {
		this.assetMonthlyTargetsDtos = assetMonthlyTargetsDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
