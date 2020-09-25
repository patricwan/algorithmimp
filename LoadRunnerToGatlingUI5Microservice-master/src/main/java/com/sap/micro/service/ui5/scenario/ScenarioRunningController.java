package com.sap.micro.service.ui5.scenario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.micro.service.ui5.scenario.domain.PerfRun;
import com.sap.micro.service.ui5.scenario.domain.Scenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/scenariorunning")
@EnableAsync
public class ScenarioRunningController {
	
	private final LRToGatlingService lrToGatlingService;
	private final ScenarioService scenarioService;
	private final PerfRunService perfRunService;
    private static final Logger Logger = LogManager.getLogger(ScenarioRunningController.class);
    
	@Autowired
	public ScenarioRunningController(final ScenarioService scenarioService, final PerfRunService perfRunService, final LRToGatlingService lrToGatlingService) {
		this.scenarioService = scenarioService;
		this.perfRunService = perfRunService;
		this.lrToGatlingService = lrToGatlingService;
	}

	@RequestMapping(value = "/convertToGatling",method = RequestMethod.GET)
    public String lrConvertToGalting(@RequestParam("zipFileName") String zipFileName){
		Logger.info("Start to Convert To Galting "+Thread.currentThread().getName());
		
		lrToGatlingService.lrConvertToGatling(zipFileName);
		
		Logger.info("LR Convert To Gatling submitted");
		
		return "Async Mode, currently processing";
    }
	
	@RequestMapping(value = "/testAsync",method = RequestMethod.GET)
    public String testAsync(){
		Logger.info(" TestAsync ============>"+Thread.currentThread().getName());
		scenarioService.testAsync();
		Logger.info(" TestAsync submitted");
		return "Async Mode, currently processing";
    }
	
	@RequestMapping(value = "/startJenkinsJob",method = RequestMethod.GET)
    public void startJenkinsJob(){
		Logger.info("Will start Jenkins Job "+Thread.currentThread().getName());
		scenarioService.startJenkinsJob();
		Logger.info("Start Jenkins Job submitted");
    }

	@GetMapping("/scenario")
	Scenario getScenario(@RequestParam("scenarioId") Long scenarioId) {
		return scenarioService.getScenario(scenarioId);
	}

	@GetMapping("/scenariosAll")
	ResponseEntity<List<Scenario>> getAllScenarios() {
		return ResponseEntity.ok(scenarioService.findAll());
	}

	@PostMapping("/addScenario")
	ResponseEntity<Scenario> postScenario(@RequestBody Scenario scenario) {
		scenarioService.saveScenario(scenario);
		return ResponseEntity.ok(scenario);
	}
	
	@PutMapping("/scenario/{scenarioId}")
	ResponseEntity<Scenario> updateScenarioGatling(@RequestBody Scenario scenario, @PathVariable Long scenarioId) {
		Scenario scenarioGot = scenarioService.getScenario(scenarioId);
		scenarioGot.setGatlingName(scenario.getGatlingName());
	
		scenarioService.saveScenario(scenarioGot);
		
		return ResponseEntity.ok(scenario);
	}
	
	@GetMapping("/perfRun")
	PerfRun getPerfRun(@RequestParam("perfRunId") Long perfRunId) {
		return perfRunService.getPerfRun(perfRunId);
	}
	
	@GetMapping("/perfRunAll")
	ResponseEntity<List<PerfRun>> getAllPerfRun() {
		return ResponseEntity.ok(perfRunService.findAll());
	}
	
	@PostMapping("/addPerfRun")
	ResponseEntity<PerfRun> postPerf(@RequestBody PerfRun perfRun) {
		Logger.info("Entry postPerf " + perfRun.getScenario());
		Logger.info("Will add Perf Run " + perfRun.getBuildVersion() + " ");
		perfRunService.savePerfRun(perfRun);
		return ResponseEntity.ok(perfRun);
	}
	
	@GetMapping("/perfRunsByScenarioID")
	ResponseEntity<List<PerfRun>> getPerfRunsByScenarioID(@RequestParam("scenarioID") Long scenarioID) {
		return ResponseEntity.ok(perfRunService.getPerfRunsByScenarioID(scenarioID));
	}
}
