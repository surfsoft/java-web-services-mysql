package com.surfsoftconsulting.template.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoScheduledTask {

    private final Logger LOGGER = LoggerFactory.getLogger(DemoScheduledTask.class);

    @Scheduled(fixedRateString = "${com.surfsoftconsulting.template.scheduled.poll.interval}")
    public void scheduledTask() {
        LOGGER.debug("Scheduled task running...");
    }

}
