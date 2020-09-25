package com.sap.micro.service.ui5.scenario;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sap.micro.service.ui5.scenario.domain.PerfRun;



public interface PerfRunRepository  extends CrudRepository<PerfRun, Long> {
    
	List<PerfRun> findAll();
    
    @Query("select max(perfRun.perfRunId)  from PerfRun perfRun") 
    Long getMaxPerfRunID(); 
    
    @Query("select perfrun from PerfRun perfrun where perfrun.scenario.scenarioID = :scenarioID") 
    List<PerfRun> getPerfRunsByScenarioID(@Param("scenarioID")  Long scenarioID); 
}
