package biz.riman.erp.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        if(stepExecution.getStatus() == BatchStatus.STARTED) {
            log.info("step name : " + stepExecution.getStepName() + " start");
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("step name : " + stepExecution.getStepName()  + " end "+ " execution time : "+ stepExecution.getEndTime());
        }
        return new ExitStatus("stepListener exit");

    }

}
