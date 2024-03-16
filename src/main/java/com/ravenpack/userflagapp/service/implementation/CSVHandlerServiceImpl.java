package com.ravenpack.userflagapp.service.implementation;

import com.opencsv.bean.CsvToBeanBuilder;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CSVHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.util.List;

@Service
public class CSVHandlerServiceImpl implements CSVHandlerService {
    public static final Logger LOG = LoggerFactory.getLogger(CSVHandlerServiceImpl.class);

    private final String csvPathInput;

    public CSVHandlerServiceImpl(@Value("${csv.file.input}") String csvPathInput) {
        this.csvPathInput = csvPathInput;
    }

    @Override
    public List<UserMessageInput> userMessageInputs() {

        List<UserMessageInput> userMessages;

        System.out.println(csvPathInput);

        final File file = new File(csvPathInput);

        try {
            userMessages = new CsvToBeanBuilder(new FileReader(file))
                    .withType(UserMessageInput.class).build()
                    .parse();
        } catch (Exception e) {
            LOG.error("Unable to find file or map to class");
            throw new RuntimeException(e);
        }

        return userMessages;
    }

    @Override
    public void writeAggregateUserMessageScores(List<AggregatedUserMessageOutput> aggregatedUserMessageOutput) {

    }
}
