package id.co.fauzi.spring.batch.tasklet;

import id.co.fauzi.spring.batch.entity.EksperimenBatch;
import id.co.fauzi.spring.batch.service.EksperimenBatchService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EksperimenBatchTasklet implements Step {

    private final EksperimenBatchService eksperimenBatchService;

    public EksperimenBatchTasklet(EksperimenBatchService eksperimenBatchService){
        this.eksperimenBatchService = eksperimenBatchService;
    }

    @Override
    public String getName() {
        return "Eksperimen Batch Fauzi";
    }

    @Override
    public boolean isAllowStartIfComplete() {
        return false;
    }

    @Override
    public int getStartLimit() {
        return 1;
    }

    @Override
    public void execute(StepExecution stepExecution) throws JobInterruptedException {
//        referensi: edijun.id/spring-batch-tutorial-migrasi-csv-ke-database/
//        FlatFileItemReader<EksperimenBatch> toBeInsert = eksperimenBatchService.csvProductReader();
//        EksperimenBatch eksperimenBatch = new EksperimenBatch();
//        eksperimenBatch.setId("2019042801");
//        eksperimenBatch.setName("sugar");
//        eksperimenBatch.setPrice(7000);
//        eksperimenBatch.setDescription("its a sugar");
//        eksperimenBatchService.saveToDatabase(eksperimenBatch);

        //referensi: https://www.geeksforgeeks.org/mapping-csv-to-javabeans-using-opencsv/
        List<EksperimenBatch> toBeInsertList =  eksperimenBatchService.csvProductReaderUsingOpenCSV();
        eksperimenBatchService.saveListToDatabase(toBeInsertList);
        stepExecution.getJobExecution().setStatus(BatchStatus.COMPLETED);
    }
}
