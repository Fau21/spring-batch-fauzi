package id.co.fauzi.spring.batch.job;

import id.co.fauzi.spring.batch.service.EksperimenBatchService;
import id.co.fauzi.spring.batch.tasklet.EksperimenBatchTasklet;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "eksperimen-batch")
public class EksperimenBatchJob extends SimpleJob {

    @Autowired
    private EksperimenBatchService eksperimenBatchService;

    public EksperimenBatchJob(JobRepository jobRepository) {
        setJobRepository(jobRepository);
    }

    @Override
    protected void doExecute(JobExecution execution)
            throws JobInterruptedException, JobRestartException {

        setSteps(List.of(
                new EksperimenBatchTasklet(
                        eksperimenBatchService
                )));

        super.doExecute(execution);
    }
}
