package biz.riman.erp.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class RimanErpInterfaceJenkinsApplications {
//    private static final Logger logger = LoggerFactory.getLogger(RimanErpInterfaceJenkinsApplications.class);

    public static void main(String[] args) {
//	public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//	    SpringApplication.run(RimanErpInterfaceJenkinsApplications.class, args);
		final var context = SpringApplication.run(RimanErpInterfaceJenkinsApplications.class, args);
		System.exit(SpringApplication.exit(context));
        

//        SpringApplication app = new SpringApplication(RimanErpInterfaceJenkinsApplications.class);
//        app.setWebApplicationType(WebApplicationType.NONE);
//        ConfigurableApplicationContext ctx = app.run(args);
//        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
//
//        if (args.length > 0) {
//            for (String arg : args) {
//                logger.error("## JOB NAME ARGUMENTS {}", arg);
//                Job job = ctx.getBean(arg, Job.class);
//                jobLauncher.run(job, new JobParameters());
//            }
//        } else {
//            logger.error("배치 잡 이름을 입력해 주세요. EX) {}", "java -jar app.jar updateTop100CoinPriceJob");
//        }
//
//        System.exit(0);
	}

}
