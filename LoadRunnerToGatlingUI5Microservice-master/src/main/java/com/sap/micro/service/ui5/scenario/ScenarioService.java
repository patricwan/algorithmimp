package com.sap.micro.service.ui5.scenario;

import java.util.List;

import com.sap.micro.service.ui5.scenario.domain.Scenario;


public interface ScenarioService {

	void saveScenario(Scenario scenario);

	void deleteScenario(Long scenarioID);

	Scenario getScenario(Long scenarioID);
    
	List<Scenario> findAll();
	
    Long getMaxScenarioID();

	String testAsync(); 
    
    //void updateScenario(Long scenarioID, String gatlingName);
    void startJenkinsJob();
    
}
