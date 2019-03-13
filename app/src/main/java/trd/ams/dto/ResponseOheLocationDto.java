package trd.ams.dto;

import java.util.List;

public class ResponseOheLocationDto {

    private List<OheLocationDto> oheLocationDtos;
    private Integer count;

    public List<OheLocationDto> getOheLocationDtos() {
        return oheLocationDtos;
    }

    public void setOheLocationDtos(List<OheLocationDto> oheLocationDtos) {
        this.oheLocationDtos = oheLocationDtos;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
