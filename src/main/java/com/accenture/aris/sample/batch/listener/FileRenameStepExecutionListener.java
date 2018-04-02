package com.accenture.aris.sample.batch.listener;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.util.FileCopyUtils;

import com.accenture.aris.core.GeneralFailureException;

public class FileRenameStepExecutionListener extends StepExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRenameStepExecutionListener.class);
    
    private String targetFilename;
    
    public void setTargetFilename(String targetFilename) {
        this.targetFilename = targetFilename;
    }
    
    @Override
    public ExitStatus afterStep(StepExecution execution) {
        if ("COMPLETED".equals(execution.getExitStatus().getExitCode())) {
            File file = null;
            File newFile = null;
            try {
                file = new File(this.targetFilename);
                if (file.exists() == false) {
                    LOGGER.error("filename={} not exists." , this.targetFilename);
                    throw new GeneralFailureException("filename=" + this.targetFilename + " not exists.");
                }
                newFile = new File(this.targetFilename + "." + new Date().getTime());
                FileCopyUtils.copy(file, newFile);
                file.deleteOnExit();
            } catch (IOException e) {
                LOGGER.error("file rename failure. source=" + file.getAbsolutePath() + ", target=" + newFile.getAbsolutePath());
                throw new GeneralFailureException("file rename failure. source=" + file.getAbsolutePath() + ", target=" + newFile.getAbsolutePath(), e);
            }
            ExecutionContext ctx = execution.getJobExecution().getExecutionContext();
            ctx.put("FileRenameStepExecutionListener_newFilename", newFile.getAbsolutePath());
            LOGGER.info("new filename={}.", newFile.getAbsolutePath());
        }
        return execution.getExitStatus();
    }
}
