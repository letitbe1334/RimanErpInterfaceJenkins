package biz.riman.erp.batch.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import biz.riman.erp.batch.job.salesOrder.UniverseSalesOrderJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class UniverseSalesOrderTest {
    @Autowired 
    private ApplicationContext applicationContext;
    @Autowired
    private JobLauncher jobLauncher;

    @Test
    public void test() {
        try {
            Job job = applicationContext.getBean(UniverseSalesOrderJob.JOB_NAME, Job.class);
            
            Map<String, JobParameter> jobParametersMap = new HashMap<>();
            jobParametersMap.put("date", new JobParameter(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"))));
//            jobParametersMap.put("date", new JobParameter("2023-07-11 18:20:33"));
            jobParametersMap.put("job", new JobParameter("SyncUniverseSalesOrder"));
            JobParameters parameters = new JobParameters(jobParametersMap);
    
            JobExecution jobExecution = jobLauncher.run(job, parameters);
    
            while (jobExecution.isRunning()) {
                log.info("...");
            }
            
            log.info("## Job Execution: " + jobExecution.getStatus());
            log.info("## Job getJobConfigurationName: " + jobExecution.getJobConfigurationName());
            log.info("## Job getJobId: " + jobExecution.getJobId());
            log.info("## Job getExitStatus: " + jobExecution.getExitStatus());
            log.info("## Job getJobInstance: " + jobExecution.getJobInstance());
            log.info("## Job getStepExecutions: " + jobExecution.getStepExecutions());
            log.info("## Job getLastUpdated: " + jobExecution.getLastUpdated());
            log.info("## Job getFailureExceptions: " + jobExecution.getFailureExceptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
