package com.ravenpack.userflagapp.service.implementation;

import com.opencsv.bean.CsvToBeanBuilder;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public void writeAggregateUserMessageScores(final Map<String, MessageScore> aggregatedUserMessages) {
        LOG.info("Writing csv file with output: [{}]", aggregatedUserMessages);

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvPathOutput), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("user_id", "total_messages", "avg_score");

            final Set<String> userIds = aggregatedUserMessages.keySet();

            for (String userId : userIds) {
                csvPrinter.printRecord(userId, aggregatedUserMessages.get(userId).totalMessages(), aggregatedUserMessages.get(userId).averageScore());
            }
        } catch (IOException e) {
            LOG.error("Unable to write to file: [{}]", e);
            throw new RuntimeException(e);
        }
    }
}
