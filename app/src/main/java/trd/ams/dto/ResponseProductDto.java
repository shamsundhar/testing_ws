package trd.ams.dto;

import java.util.List;

public class ResponseProductDto {

	private List<ProductDto> productDtos;
	private Integer count;

	public List<ProductDto> getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(List<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
