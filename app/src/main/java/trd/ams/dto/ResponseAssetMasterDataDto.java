package trd.ams.dto;

import java.util.List;

public class ResponseAssetMasterDataDto {

	private List<AssetMasterDataDto> assetMasterDataDtos;
	private Integer count;
	
	public List<AssetMasterDataDto> getAssetMasterDataDtos() {
		return assetMasterDataDtos;
	}

	public void setAssetMasterDataDtos(List<AssetMasterDataDto> assetMasterDataDtos) {
		this.assetMasterDataDtos = assetMasterDataDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
