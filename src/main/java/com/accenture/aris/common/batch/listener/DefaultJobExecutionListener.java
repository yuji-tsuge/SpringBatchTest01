package com.accenture.aris.common.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class DefaultJobExecutionListener implements JobExecutionListener{

    private static final Logger LOGGER = 
        LoggerFactory.getLogger(DefaultJobExecutionListener.class);
    
    @Override
    public void beforeJob(JobExecution execution) {
    }

    @Override
    public void afterJob(JobExecution execution) {
        long execTime = execution.getEndTime().getTime() - execution.getStartTime().getTime();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long useMemory = totalMemory - Runtime.getRuntime().freeMemory();
        LOGGER.info("|==========================================");
        LOGGER.info("| JOB_NAME         : " + execution.getJobInstance().getJobName());
        LOGGER.info("| JOB_EXECUTION_ID : " + execution.getId());
        LOGGER.info("| JOB_INSTANCE_ID  : " + execution.getJobId());
        LOGGER.info("| EXIT_CODE        : " + execution.getExitStatus().getExitCode());
        LOGGER.info("| ----------------------------------------");
        LOGGER.info("| START_TIME : " + execution.getStartTime());
        LOGGER.info("| END_TIME   : " + execution.getEndTime());
        LOGGER.info("| EXEC_TIME  : " + execTime/1000.0 + "(sec)");
        LOGGER.info("| ----------------------------------------");
        LOGGER.info("| USE_MEMORY   : " + useMemory/1000/1000 + "(MB)");
        LOGGER.info("| TOTAL_MEMORY : " + totalMemory/1000/1000 + "(MB)");
        LOGGER.info("| MAX_MEMORY   : " + maxMemory/1000/1000+ "(MB)");
        LOGGER.info("|==========================================");
    }
}
