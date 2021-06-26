package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;

public class GetMyUsersWithPag {
	
	private List<GetMyUsersDto> list;
	private Long count;
	
	public GetMyUsersWithPag(List<GetMyUsersDto> list, Long count) {
		super();
		this.list = list;
		this.count = count;
	}
	public List<GetMyUsersDto> getList() {
		return list;
	}
	public void setList(List<GetMyUsersDto> list) {
		this.list = list;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
