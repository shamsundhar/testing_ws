package trd.ams.dto;

import java.util.List;

public class ResponseElementarySectionsDto {

	private List<ElementarySectionsDto> elementarySectionsDtos;
	private Integer count;
	
	public List<ElementarySectionsDto> getElementarySectionsDtos() {
		return elementarySectionsDtos;
	}

	public void setElementarySectionsDtos(List<ElementarySectionsDto> elementarySectionsDtos) {
		this.elementarySectionsDtos = elementarySectionsDtos;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
