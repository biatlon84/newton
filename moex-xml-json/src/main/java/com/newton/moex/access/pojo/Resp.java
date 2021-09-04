package com.newton.moex.access.pojo;

public class Resp {
	private data resp;
	private long responseTime;
	private boolean validData;

	public Resp(String ti, String last, boolean valid) {
		super();
		this.resp = new data(ti, last);
		this.responseTime = System.currentTimeMillis() / 1000;
		this.validData = valid;
	}

	public data getResp() {
		return resp;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public boolean isValidData() {
		return validData;
	}

}