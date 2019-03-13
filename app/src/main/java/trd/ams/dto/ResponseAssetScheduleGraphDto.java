package trd.ams.dto;

import java.util.List;

public class ResponseAssetScheduleGraphDto {

    private List<AssetScheduleGraphDto> assetScheduleGraphDtos;
    private Integer count;

    public List<AssetScheduleGraphDto> getAssetScheduleGraphDtos() {
        return assetScheduleGraphDtos;
    }
    public void setAssetScheduleGraphDtos(List<AssetScheduleGraphDto> assetScheduleGraphDtos) {
        this.assetScheduleGraphDtos = assetScheduleGraphDtos;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
