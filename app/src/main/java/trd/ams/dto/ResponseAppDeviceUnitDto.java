package trd.ams.dto;

import java.util.List;

public class ResponseAppDeviceUnitDto{

    private List<AppDeviceUnitDto> appDeviceUnitDtos;
    private Integer count;

    public List<AppDeviceUnitDto> getAppDeviceUnitDtos() {
        return appDeviceUnitDtos;
    }
    public void setAppDeviceUnitDtos(List<AppDeviceUnitDto> appDeviceUnitDtos) {
        this.appDeviceUnitDtos = appDeviceUnitDtos;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }


}
