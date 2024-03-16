package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.service.CSVHandlerService;
import com.ravenpack.userflagapp.service.UserMessageService;

public class UserMessageServiceImpl implements UserMessageService {

    private final CSVHandlerService csvHandlerService;

    public UserMessageServiceImpl(final CSVHandlerService csvHandlerService) {
        this.csvHandlerService = csvHandlerService;
    }

    @Override
    public void getOffensiveMessageScoresCSV() {}
}
