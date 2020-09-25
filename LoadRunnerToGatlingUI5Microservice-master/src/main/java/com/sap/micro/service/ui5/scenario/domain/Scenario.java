package com.sap.micro.service.ui5.scenario.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sc_scenario")
public class Scenario {
	
	@Id
	@Column(name = "scenarioID")
	private Long scenarioID;

	@Column(name = "scenarioName")
	private String scenarioName;
	
	@Column(name = "gatlingName")
	private String gatlingName;
	
	@Column(name = "loadrunnerName")
	private String loadrunnerName;

	public Long getScenarioID() {
		return scenarioID;
	}

	public void setScenarioId(Long scenarioID) {
		this.scenarioID = scenarioID;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getGatlingName() {
		return gatlingName;
	}

	public void setGatlingName(String gatlingName) {
		this.gatlingName = gatlingName;
	}

	public String getLoadrunnerName() {
		return loadrunnerName;
	}

	
	public void setLoadrunnerName(String loadrunnerName) {
		this.loadrunnerName = loadrunnerName;
	}
}
