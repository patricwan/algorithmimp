package com.sap.perf.loadrunner.gatling;

import java.util.ArrayList;
import java.util.List;

public class LRTransaction {
	
	private String transactionName = null;
	
	private String thinkTime = null;
	
	private List<LRRequest> lrRequests = new ArrayList<LRRequest>();

	public String getThinkTime() {
		return thinkTime;
	}

	public void setThinkTime(String thinkTime) {
		this.thinkTime = thinkTime;
	}
	
	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public void addLrRequest(LRRequest lrRequest) {
		 this.lrRequests.add(lrRequest);
	}
	
	public List<LRRequest> getLrRequests() {
		return lrRequests;
	}

	public void setLrRequests(List<LRRequest> lrRequests) {
		this.lrRequests = lrRequests;
	}
}
