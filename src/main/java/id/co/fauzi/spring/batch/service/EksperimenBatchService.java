package id.co.fauzi.spring.batch.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import id.co.fauzi.spring.batch.entity.EksperimenBatch;
import id.co.fauzi.spring.batch.repository.EksperimenBatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EksperimenBatchService {

    private final EksperimenBatchRepository eksperimenBatchRepository;

    public EksperimenBatchService(EksperimenBatchRepository eksperimenBatchRepository){
        this.eksperimenBatchRepository = eksperimenBatchRepository;
    }

    public FlatFileItemReader<EksperimenBatch> csvProductReader() {
        FlatFileItemReader<EksperimenBatch> reader = new FlatFileItemReader<EksperimenBatch>();
        reader.setResource(new ClassPathResource("products.csv"));
        reader.setLineMapper(new DefaultLineMapper<EksperimenBatch>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "name", "price", "description" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<EksperimenBatch>() {
                    {
                        setTargetType(EksperimenBatch.class);
                    }
                });
            }
        });
        return reader;
    }

    public List<EksperimenBatch> csvProductReaderUsingOpenCSV(){
        // Hashmap to map CSV data to
        // Bean attributes.
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("id", "id");
        mapping.put("nama", "name");
        mapping.put("harga", "price");
        mapping.put("deskripsi", "description");

        // HeaderColumnNameTranslateMappingStrategy
        // for EksperimenBatch class
        HeaderColumnNameTranslateMappingStrategy<EksperimenBatch> strategy =
                new HeaderColumnNameTranslateMappingStrategy<EksperimenBatch>();
        strategy.setType(EksperimenBatch.class);
        strategy.setColumnMapping(mapping);

        // Create castobaen and csvreader object
        CSVReader csvReader = null;
        try {
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current absolute path is: " + s);

            csvReader = new CSVReader(new FileReader
                    (s+"\\src\\main\\resources\\products.csv"));
        }
        catch (FileNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CsvToBean csvToBean = new CsvToBean();

        // call the parse method of CsvToBean
        // pass strategy, csvReader to parse method
        List<EksperimenBatch> list = csvToBean.parse(strategy, csvReader);

        return list;
    }

    public void saveToDatabase(EksperimenBatch eksperimenBatch){
//        List<EksperimenBatch> test = eksperimenBatchRepository.findAll();
        eksperimenBatchRepository.save(eksperimenBatch);
//        log.info("seharusnya lewat sini");
    }

    public void saveListToDatabase(List<EksperimenBatch> eksperimenBatchList){
        eksperimenBatchRepository.saveAll(eksperimenBatchList);
    }

}
