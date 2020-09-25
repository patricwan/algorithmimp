package com.sap.micro.service.ui5.scenario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.micro.service.ui5.scenario.domain.PerfRun;


@Service
public class PerfRunServiceImpl implements PerfRunService {
	
    private PerfRunRepository perfRunRepository;
    
    @Autowired
	public PerfRunServiceImpl(final PerfRunRepository perfRunRepository) {
		super();
		this.perfRunRepository = perfRunRepository;
	}

	@Override
	public void savePerfRun(PerfRun perfRun) {
		//Create a new id if not exists
		if (perfRun.getPerfRunId() == null) {
			Long initialMaxID;
			if (perfRunRepository.getMaxPerfRunID() !=null) {
				initialMaxID = perfRunRepository.getMaxPerfRunID();
			} else {
				initialMaxID = new Long(100);
			}
			
			perfRun.setPerfRunId(initialMaxID + 1);
		}
		
		perfRunRepository.save(perfRun);
	}
	
	@Override
	public Long getMaxPerfRunID() {
		return perfRunRepository.getMaxPerfRunID();	
	}

	@Override
	public void deletePerfRun(Long perfRunID) {
		
		perfRunRepository.delete(perfRunID);
	}

	@Override
	public PerfRun getPerfRun(Long perfRunID) {
		return perfRunRepository.findOne(perfRunID);
	}

	@Override
	public List<PerfRun> findAll() {
		 
		return perfRunRepository.findAll();
	}

	@Override
	public List<PerfRun> getPerfRunsByScenarioID(Long scenarioID) {
		return perfRunRepository.getPerfRunsByScenarioID(scenarioID);
	}
}
