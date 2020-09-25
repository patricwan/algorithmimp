package com.sap.perf.loadrunner.gatling;

import java.util.ArrayList;
import java.util.List;

public class LRAction {
	
	private List<LRTransaction> lrTransctions = null;

	public LRAction() {
		super();
		this.lrTransctions = new ArrayList<LRTransaction>();
	}
	
	public LRAction(List<LRTransaction> lrTransctions) {
		super();
		this.lrTransctions = lrTransctions;
	}

	public List<LRTransaction> getLrTransctions() {
		return lrTransctions;
	}

	public void setLrTransctions(List<LRTransaction> lrTransctions) {
		this.lrTransctions = lrTransctions;
	}
	
	public  void combine(LRAction lrAction) {
		this.lrTransctions .addAll(lrAction.getLrTransctions());
	}
}
