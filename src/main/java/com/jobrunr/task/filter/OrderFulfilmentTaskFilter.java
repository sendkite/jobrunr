package com.jobrunr.task.filter;

import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.filters.JobServerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderFulfilmentTaskFilter implements JobServerFilter {

    private static final Logger logger = LoggerFactory.getLogger(OrderFulfilmentTaskFilter.class);

    @Override
    public void onFailedAfterRetries(Job job) {
        // TODO alert operational team of Job failure
        logger.info("All retries failed for Job {}", job.getJobName());
    }
}
