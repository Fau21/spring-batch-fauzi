package id.co.fauzi.spring.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@Slf4j
@EnableBatchProcessing
//@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class
//})
@SpringBootApplication
public class FauziApplication implements CommandLineRunner {

    @Autowired
    ApplicationContext context;

    @Autowired
    JobLauncher jobLauncher;


    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FauziApplication.class);
        System.exit(SpringApplication.exit(springApplication.run(args)));
    }

    @Override
    public void run(String... args) throws Exception {
//        log.info("arguments " + args[0]);
//        String[] argument = args[0].split("=");
        Job job = (Job) context.getBean("eksperimen-batch");

        JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder()
                .addDate("now", new Date())
                .addLong("jobId", System.currentTimeMillis())
                .toJobParameters());

        BatchStatus batchStatus = jobExecution.getStatus();
        if (batchStatus == BatchStatus.COMPLETED) {
            log.info("Return Code : 0");
            System.exit(0);
        } else {
            log.info("Return Code : 2");
            System.exit(2);
        }
    }
}
