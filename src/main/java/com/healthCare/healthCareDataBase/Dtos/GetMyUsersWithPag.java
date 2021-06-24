package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;

public class GetMyUsersWithPag {
	
	private List<GetMyUsersDto> list;
	private long count;
	
	public GetMyUsersWithPag(List<GetMyUsersDto> list, long count) {
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
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
}
