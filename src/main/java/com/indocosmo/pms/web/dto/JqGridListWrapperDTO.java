package com.indocosmo.pms.web.dto;

import java.util.List;

public class JqGridListWrapperDTO {
	private List<?> rows;
	private int total;
	private int page;
	private int records;
	private int endLimit;
	private int startLimit;
	private int randomNumber;
	private String pagingStart;

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getEndLimit() {
		return endLimit;
	}

	public void setEndLimit(int endLimit) {
		this.endLimit = endLimit;
	}

	public int getStartLimit() {
		return startLimit;
	}

	public void setStartLimit(int startLimit) {
		this.startLimit = startLimit;
	}

	public int getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(int randomString) {
		this.randomNumber = randomString;
	}

	public String getPagingStart() {
		return pagingStart;
	}

	public void setPagingStart(String pagingStart) {
		this.pagingStart = pagingStart;
	}
}