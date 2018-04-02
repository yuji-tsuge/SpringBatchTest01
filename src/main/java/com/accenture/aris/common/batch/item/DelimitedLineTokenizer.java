package com.accenture.aris.common.batch.item;

public class DelimitedLineTokenizer extends org.springframework.batch.item.file.transform.DelimitedLineTokenizer {

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
