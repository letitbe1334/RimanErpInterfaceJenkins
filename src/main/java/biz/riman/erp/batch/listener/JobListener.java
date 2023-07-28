package biz.riman.erp.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobListener implements JobExecutionListener {
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("job name : " + jobExecution.getJobInstance().getJobName() + " start");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();
        long executionTime = endTime - startTime;
        log.info("job name : " + jobName  + " end "+ " execution time : "+executionTime);
    }

}
