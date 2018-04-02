package com.accenture.aris.common.batch.item;

public class DelimitedLineAggregator<T> extends org.springframework.batch.item.file.transform.DelimitedLineAggregator<T> {

    public DelimitedLineAggregator() {
        // nothing
    }
    
    @Override
    public void setDelimiter(String delimiter) {
        if ("TAB".equals(delimiter)) {
            super.setDelimiter("\t");
        } else if ("COMMA".equals(delimiter)) {
            super.setDelimiter(",");
        } else if ("SPACE".equals(delimiter)) {
            super.setDelimiter(" ");
        } else {
            super.setDelimiter(delimiter);
        }
    }
}
