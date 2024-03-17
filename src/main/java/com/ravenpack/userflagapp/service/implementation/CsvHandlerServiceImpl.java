package com.ravenpack.userflagapp.service.implementation;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

@Service
public class CsvHandlerServiceImpl implements CsvHandlerService {
    public static final Logger LOG = LoggerFactory.getLogger(CsvHandlerServiceImpl.class);

    private final String csvPathInput;
    private final String csvPathOutput;

    public CsvHandlerServiceImpl(
            @Value("${csv.file.input}") String csvPathInput,
            @Value("${csv.file.output}") String csvPathOutput
    ) {
        this.csvPathInput = csvPathInput;
        this.csvPathOutput = csvPathOutput;
    }

    @Override
    public List<UserMessageInput> userMessageInputs() {

        LOG.info("Reading input file with path: [{}]", csvPathInput);

        final File file = new File(csvPathInput);

        try {
            return new CsvToBeanBuilder(new FileReader(file))
                    .withType(UserMessageInput.class).build()
                    .parse();
        } catch (Exception e) {
            LOG.error("Unable to find file or map to class");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeAggregateUserMessageScores(List<AggregatedUserMessageOutput> aggregatedUserMessageOutput) {
        LOG.info("Writing csv file with output: [{}]", aggregatedUserMessageOutput);

        final CustomMappingStrategy<AggregatedUserMessageOutput> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(AggregatedUserMessageOutput.class);

        try (Writer writer = new FileWriter(csvPathOutput)) {
            final StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(aggregatedUserMessageOutput);
        } catch (Exception e) {
            LOG.error("Unable to write to file");
            throw new RuntimeException(e);
        }
    }
}
