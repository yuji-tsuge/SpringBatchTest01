package com.accenture.aris.common.batch.item;

public class FlatFileItemWriter<T> extends org.springframework.batch.item.file.FlatFileItemWriter<T> {

    @Override
    public void setLineSeparator(String lineSeparator) {
        if ("CRLF".equals(lineSeparator)) {
            super.setLineSeparator("\r\n");
        } else if ("LF".equals(lineSeparator)) {
            super.setLineSeparator("\n");
        } else {
            super.setLineSeparator(lineSeparator);
        }
    }
}
