package trd.ams.dto;

import java.util.List;

public class ResponseDataSyncHistoryDto {

    private List<DataSyncHistoryDto> dataSyncHistoryDtos;
    private Integer count;

    public List<DataSyncHistoryDto> getDataSyncHistoryDtos() {
        return dataSyncHistoryDtos;
    }
    public void setDataSyncHistoryDtos(List<DataSyncHistoryDto> dataSyncHistoryDtos) {
        this.dataSyncHistoryDtos = dataSyncHistoryDtos;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
