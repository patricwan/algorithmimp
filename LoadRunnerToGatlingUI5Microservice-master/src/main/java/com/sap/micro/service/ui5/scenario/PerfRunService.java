package com.sap.micro.service.ui5.scenario;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sap.micro.service.ui5.scenario.domain.PerfRun;

public interface PerfRunService {

	void deletePerfRun(Long perfRunID);

	PerfRun getPerfRun(Long perfRunID);
    
	List<PerfRun> findAll();

	void savePerfRun(PerfRun perfRun);
	
    List<PerfRun> getPerfRunsByScenarioID(Long scenarioID);

	Long getMaxPerfRunID(); 
}
