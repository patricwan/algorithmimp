package com.sap.micro.service.ui5.scenario;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.sap.micro.service.ui5.scenario.domain.Scenario;


@Service
public class ScenarioServiceImpl implements ScenarioService {
	
    private ScenarioRepository scenarioRepository;
    
   // private  final Logger Logger = LoggerFactory.getLogger(this.getClass());
    private static final Logger Logger = LogManager.getLogger(ScenarioServiceImpl.class);

    @Autowired
	public ScenarioServiceImpl(final ScenarioRepository scenarioRepository) {
		super();
		this.scenarioRepository = scenarioRepository;
	}

	@Override
	public void saveScenario(Scenario scenario) {
		//Create a new id if not exists
		if (scenario.getScenarioID() == null) {
			Long maxScenarioId;
			if (scenarioRepository.getMaxScenarioID()!=null) {
				maxScenarioId = scenarioRepository.getMaxScenarioID();
			} else {
				maxScenarioId = new Long(100);
			}
			scenario.setScenarioId(maxScenarioId + 1);
		}
		scenarioRepository.save(scenario);
	}
	
	/*@Override
	public void updateScenario(Long scenarioID, String gatlingName) {
	    scenarioRepository.updateScenario(scenarioID, gatlingName);
	} */

	@Override
	public void deleteScenario(Long scenarioID) {
		
		scenarioRepository.delete(scenarioID);
	}

	@Override
	public Scenario getScenario(Long scenarioID) {
		Logger.info("Get scenario " + scenarioID);
		return scenarioRepository.findOne(scenarioID);
	}

	@Override
	public List<Scenario> findAll() {
		 Logger.info("Find all Scenarios");
		return scenarioRepository.findAll();
	}

	@Override
	public Long getMaxScenarioID() {
		return scenarioRepository.getMaxScenarioID();	
	}
	
	@Async
    @Override
	public void startJenkinsJob() {
        try {
		Logger.info("This is the start of startJenkinsJob Service Impl");
        JenkinsServer jenkins = new JenkinsServer(new URI("http://10.169.77.97:8080/"), "patricwan", "Initial123456");
        if(jenkins.isRunning()){
                // get all jobs
                Map<String, Job> jobs = jenkins.getJobs();

                JobWithDetails job = jobs.get("TestMavenSimple").details();

                //build the job
                job.build();

                //get job output
                job.getLastBuild().details().getConsoleOutputHtml();
                job.getLastBuild().details().getConsoleOutputText();
                job.getLastBuild().details().getResult();
			}
        } catch (Exception e) {
        	Logger.error("Start Jenkins failed with " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	@Async
    @Override
    public String testAsync(){
        Logger.info("This is the start of testing Async in Service Impl");
            try {
            	//Logger.debug("This is the start of Test Async");
            	System.out.println("This is the start of testing Async in Service Impl");
                for (int i = 1;i <= 100;i++){
                    System.out.println(Thread.currentThread().getName()+"---------Async：>"+i);
                    Thread.sleep(100);
                }
                System.out.println("This is the end of testing Async in Service Impl");
               Logger.info("This is the end of Test Async");
                return "Executed Task";
            }catch (Exception ex){
                ex.printStackTrace();
            }
 
        return Thread.currentThread().getName()+"Executed";
    }
}
