package biz.riman.erp.batch.job.delivery;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import biz.riman.erp.batch.listener.JobListener;
import biz.riman.erp.batch.listener.StepListener;
import biz.riman.erp.batch.parameter.DeliveryParameter;
import biz.riman.erp.batch.service.delivery.UniverseDeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UniverseDeliveryJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UniverseDeliveryService service;
    private final DeliveryParameter parameter;

    public static final String JOB_NAME = "universe.deliveryBatch";
    public static final String STEP_NAME = ".syncStep";

    @Bean(name = JOB_NAME)
    public Job job() throws Exception {
        log.info("## {} Job 실행 ##", JOB_NAME);
        return jobBuilderFactory.get(JOB_NAME)
                .start(syncStep())
                .listener(new JobListener())
                .build();
    }

    @Bean(name = JOB_NAME + STEP_NAME)
    @JobScope
    public Step syncStep() throws Exception {
        log.info("## {} - {} Step 실행 ##", JOB_NAME, STEP_NAME);
        return stepBuilderFactory.get(JOB_NAME + STEP_NAME)
                .listener(new StepListener())
                .tasklet((contribution, chunkContext) -> {
                    String response = service.callDelivery(parameter);
                    log.info("## response : {}", response);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
