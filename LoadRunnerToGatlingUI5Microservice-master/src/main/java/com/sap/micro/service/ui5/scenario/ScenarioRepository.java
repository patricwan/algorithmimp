package com.sap.micro.service.ui5.scenario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sap.micro.service.ui5.scenario.domain.PerfRun;
import com.sap.micro.service.ui5.scenario.domain.Scenario;


public interface ScenarioRepository  extends CrudRepository<Scenario, Long> {
    List<Scenario> findAll();
    
    @Query("select max(scenario.scenarioID)  from Scenario scenario") 
    Long getMaxScenarioID(); 
    
   /* @Query("update Scenario scenario set scenario.gatlingName = :gatlingName where scenario.scenarioID = :scenarioID")
   void updateScenario(@Param("scenarioID")  Long scenarioID, @Param("gatlingName") String gatlingName); */

}
