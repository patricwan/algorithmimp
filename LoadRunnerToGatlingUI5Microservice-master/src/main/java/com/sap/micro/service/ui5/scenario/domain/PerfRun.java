package com.sap.micro.service.ui5.scenario.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sc_perfrun")
public class PerfRun {
	
	@Id
	private Long perfRunId;

	@Column(name = "buildVersion")
	private String buildVersion;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "maxUsers")
	private int maxUsers;
	
	//With minutes
	@Column(name = "duration")
	private float duration;

	@Column(name = "startTime")
	private Date startTime;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "scenarioID", referencedColumnName = "scenarioID")
	private Scenario scenario;

	public Long getPerfRunId() {
		return perfRunId;
	}

	public void setPerfRunId(Long perfRunId) {
		this.perfRunId = perfRunId;
	}

	public String getBuildVersion() {
		return buildVersion;
	}

	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
}
