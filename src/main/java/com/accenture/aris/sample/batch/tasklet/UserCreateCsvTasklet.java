package com.accenture.aris.sample.batch.tasklet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.aris.sample.business.service.UserService;

public class UserCreateCsvTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateCsvTasklet.class);
    
    public static final String DEFAULT_OUTPUT_DIR = "/temp";
    
    @Autowired
    private UserService service;
    
    private String outputDir = DEFAULT_OUTPUT_DIR;
    
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
        
        File csvFile = File.createTempFile("TEMP", ".csv", new File(outputDir));
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(csvFile));
            service.downloadWithHandlerService(null, os);
        } catch (Exception ex) {
            LOGGER.error(
                "CSV File:[{}] create failure. exception:{}", csvFile.getAbsolutePath(), ex);
            ex.printStackTrace();
            contribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch(Exception ex) {
                    // nothing
                }
            }
        }
        contribution.setExitStatus(ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
