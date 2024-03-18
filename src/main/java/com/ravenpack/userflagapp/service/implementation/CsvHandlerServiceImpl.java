package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.AggregatedMessageScoreOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of the {@link CsvHandlerServiceImpl} to read and write the csv file
 */
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

    /**
     * Reads the csv from the location set in {@link #csvPathInput}
     *
     * @return a list of user message inputs
     */
    @Override
    public List<UserMessageInput> userMessageInputs() {
        LOG.info("Reading input file with path: [{}]", csvPathInput);

        final String[] headers = {"user_id", "message"};
        final List<UserMessageInput> userMessageInputs = new ArrayList<>();

        try {
            final Reader in = new FileReader(csvPathInput);

            final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(headers)
                    .setSkipHeaderRecord(true)
                    .build();

            final Iterable<CSVRecord> records = csvFormat.parse(in);

            for (final CSVRecord record : records) {
                final String userId = record.get("user_id");
                final String message = record.get("message");
                userMessageInputs.add(new UserMessageInput(userId, message));
            }

        } catch (Exception e) {
            LOG.error("Unable to read file with error [{}]", e.getMessage());
            throw new RuntimeException(e);
        }

        return userMessageInputs;
    }

    /**
     * Writes to the csv set in {@link #csvPathOutput}
     *
     * @param aggregatedUserMessages the values to be written to the csv
     */
    @Override
    public void writeAggregateUserMessageScores(final Map<String, AggregatedMessageScoreOutput> aggregatedUserMessages) {
        LOG.info("Writing csv file with output: [{}] to path [{}]", aggregatedUserMessages, csvPathOutput);
        final String[] headers = {"user_id", "total_messages", "avg_score"};

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvPathOutput), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord((Object[]) headers);

            final Set<String> userIds = aggregatedUserMessages.keySet();

            for (String userId : userIds) {
                final AggregatedMessageScoreOutput messageScore = aggregatedUserMessages.get(userId);

                csvPrinter.printRecord(
                        userId,
                        messageScore.totalMessages(),
                        messageScore.avgScore());
            }
        } catch (IOException e) {
            LOG.error("Unable to write to file, with error: [{}]", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
