package trd.ams.dto;

import java.util.List;

public class ResponseAssetScheduleContentDto {

    private List<AssetScheduleContentDto> assetScheduleContentDtos;
    private Integer count;

    public List<AssetScheduleContentDto> getAssetScheduleContentDtos() {
        return assetScheduleContentDtos;
    }
    public void setAssetScheduleContentDtos(List<AssetScheduleContentDto> assetScheduleContentDtos) {
        this.assetScheduleContentDtos = assetScheduleContentDtos;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
