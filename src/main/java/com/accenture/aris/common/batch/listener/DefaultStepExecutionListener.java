package com.accenture.aris.common.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class DefaultStepExecutionListener implements StepExecutionListener {

    private static final Logger LOGGER = 
            LoggerFactory.getLogger(DefaultStepExecutionListener.class);
        
    @Override
    public void beforeStep(StepExecution execution) {
    }
    
    @Override
    public ExitStatus afterStep(StepExecution execution) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long useMemory = totalMemory - Runtime.getRuntime().freeMemory();
        LOGGER.info("|==========================================");
        LOGGER.info("| StepName  :" + execution.getStepName());
        LOGGER.info("| ExitCode  :" + execution.getExitStatus().getExitCode());
        LOGGER.info("| ----------------------------------------");
        LOGGER.info("| ReadCount     :" + execution.getReadCount());
        LOGGER.info("| WriteCount    :" + execution.getWriteCount());
        LOGGER.info("| WriteSkipCount:" + execution.getWriteSkipCount());
        LOGGER.info("| SkipCount     :" + execution.getSkipCount());
        LOGGER.info("| CommitCount   :" + execution.getCommitCount());
        LOGGER.info("| ----------------------------------------");
        LOGGER.info("| Use/Total/Max Memory : " + useMemory/1000/1000 + "(MB)/" + totalMemory/1000/1000 + "(MB)/" + maxMemory/1000/1000+ "(MB)");
        LOGGER.info("|==========================================");
        return execution.getExitStatus();
    }

}
