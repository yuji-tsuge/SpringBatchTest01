package com.accenture.aris.common.batch.item;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ClassUtils.getShortName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.item.database.AbstractPagingItemReader;

public class MyBatisAutoPagingItemReader<T> extends AbstractPagingItemReader<T> {

    private String queryId;

    private SqlSessionFactory sqlSessionFactory;

    private SqlSessionTemplate sqlSessionTemplate;

    private Map<String, Object> parameterValues;

    public MyBatisAutoPagingItemReader() {
        setName(getShortName(MyBatisAutoPagingItemReader.class));
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        notNull(sqlSessionFactory);
        sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        notNull(queryId);
    }

    @Override
    protected void doReadPage() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (parameterValues != null) {
            parameters.putAll(parameterValues);
        }
        // 間違って、SQL側でページングしないようにパラメータの追加をコメントアウト
        //parameters.put("_page", getPage());
        //parameters.put("_pagesize", getPageSize());
        //parameters.put("_skiprows", getPage() * getPageSize());
        if (results == null) {
            results = new CopyOnWriteArrayList<T>();
        } else {
            results.clear();
        }
        
        // MyBatisの処理でページングを行う（WEBと同じ方式）
        //results.addAll(sqlSessionTemplate.<T> selectList(queryId, parameters));
        results.addAll(sqlSessionTemplate.<T> selectList(queryId, parameters, new RowBounds(getPage() * getPageSize(), getPageSize())));
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }

}
