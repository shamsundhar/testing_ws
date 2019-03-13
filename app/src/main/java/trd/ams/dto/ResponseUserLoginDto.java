package trd.ams.dto;

import java.util.List;

public class ResponseUserLoginDto {

	private List<UserLoginDto> userLoginDtos;
	private Integer count;

	public List<UserLoginDto> getUserLoginDtos() {
		return userLoginDtos;
	}
	public Integer getCount() {
		return count;
	}
	public void setUserLoginDtos(List<UserLoginDto> userLoginDtos) {
		this.userLoginDtos = userLoginDtos;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}
