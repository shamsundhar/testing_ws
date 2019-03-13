package trd.ams.dto;

import java.util.List;

public class ResponseProductCategoryMemberDto {

	private List<ProductCategoryMemberDto> productCategoryMemberDtos;
	private Integer count;
	
	public List<ProductCategoryMemberDto> getProductCategoryMemberDtos() {
		return productCategoryMemberDtos;
	}

	public void setProductCategoryMemberDtos(List<ProductCategoryMemberDto> productCategoryMemberDtos) {
		this.productCategoryMemberDtos = productCategoryMemberDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
