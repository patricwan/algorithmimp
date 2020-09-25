package com.sap.micro.service.ui5;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sap.micro.service.ui5.scenario.PerfRunRepository;
import com.sap.micro.service.ui5.scenario.ScenarioRepository;
import com.sap.micro.service.ui5.scenario.domain.PerfRun;
import com.sap.micro.service.ui5.scenario.domain.Scenario;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InitializeScenarioTest {

	@Autowired
	private ScenarioRepository scenarioRepository;

	@Autowired
	private PerfRunRepository perfRunRepository;

	@Test
	public void testAddScenario() throws Exception {
		Scenario scenario = new Scenario();
		scenario.setScenarioId(new Long(100));
		scenario.setScenarioName("WMLoad Full Scenario");
		scenario.setGatlingName("WMLoadAllGatling");
		scenario.setLoadrunnerName("WMLoadAllLR");
		// scenarioRepository.save(scenario);
	}

	@Test
	public void testAddPerfRun() throws Exception {
		PerfRun perfRun = new PerfRun();
		perfRun.setPerfRunId(new Long(200));
		perfRun.setBuildVersion("b1808.20181012033952");
		perfRun.setDuration(120);
		perfRun.setMaxUsers(1000);
		perfRun.setStartTime(new Date());

		Long scenarioId = new Long(100);
		Scenario foundScenario = scenarioRepository.findOne(scenarioId);
		perfRun.setScenario(foundScenario);
		//perfRunRepository.save(perfRun);
	}
}