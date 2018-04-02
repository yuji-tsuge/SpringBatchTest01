package com.accenture.aris.common.batch.item;

import static org.springframework.util.ClassUtils.getShortName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * JdbcPagingItemReaderクラス。<p>
 * 
 * SybaseのjConnentのマニュアルには、以下のように記述されています。<br>
 * 
 * カーソル・クローズ時のロックの解放<p>
 *
 * Adaptive Server 15.7 では、release_locks_on_close オプションを含めるように
 * declare cursor 構文が拡張されています。このオプションは、カーソルのクローズ時に
 * 独立性レベル 2 および 3 でカーソルの共有ロックを解放します。jConnect 接続で適用するには
 * RELEASE_LOCKS_ON_CURSOR_CLOSE接続プロパティを true に設定します。
 * デフォルトのRELEASE_LOCKS_ON_CURSOR_CLOSE 値は false です。
 * この設定は、release_locks_on_close をサポートしているサーバに接続されている場合にのみ有効です。
 * release_locks_on_close の詳細については、『ASE リファレンス・マニュアル：コマンド』を参照してください。<p>
 *
 * PreparedStatement オブジェクトでのカーソルの使用方法<p>
 *
 * PreparedStatement を一度作成すれば、入力パラメータに同じ値や異なる値を指定して、何度も使用できます。
 * カーソルとともにPreparedStatement オブジェクトを使用する場合は、使用が終わるたびにカーソルをクローズして、
 * 次に使用するときに再度オープンする必要があります。カーソルは結果セットをクローズするとクローズされます (ResultSet.close)。
 * カーソルの prepared 文を実行すると、カーソルがオープンされます (PreparedStatement.executeQuery)。<p>
 *
 * 上記の説明からは、ResultSet.close()を呼び出せばカーソルはクローズするが、JDBC接続の場合には、カーソルが
 * クローズしても、必ず共有ロックが解放されるわけではないということらしいです。<p> 
 * 
 * これを受けて、このクラスではResultSet.close()と同じタイミングでPremeareｄStatement.close()を呼び出すように実装しています。
 * 
 * @author tadahiro.a.murakami
 * @param <T>
 */
public class JdbcPagingItemReader<T> extends AbstractPagingItemReader<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcPagingItemReader.class);
    
    private Map<String, Object> parameterValues;
    private DataSource dataSource;
    private String sql;
    private RowMapper<T> rowMapper;
    private PreparedStatementSetter preparedStatementSetter;
    private Connection conn;
    private PreparedStatement sts;
    
    public JdbcPagingItemReader() {
        setName(getShortName(JdbcPagingItemReader.class));
    }

    public void setParameterValues(Map<String, Object> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {
        this.preparedStatementSetter = preparedStatementSetter;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    @Override
    protected void doOpen() throws Exception {
        super.doOpen();
        conn = dataSource.getConnection();
        LOGGER.info("open connection. conn={}", conn);
    }
    
    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new CopyOnWriteArrayList<T>();
        } else {
            results.clear();
        }
        
        ResultSet rs = null;
        boolean isLastPage = false;
        try {
            
            Map<String, Object> parameters = new HashMap<String, Object>();
            if (parameterValues != null) {
                parameters.putAll(parameterValues);
            }
            parameters = modifyParameters(parameters);
            
            // ------------------------------------------
            // [step.1] execute query
            // ------------------------------------------
            if (sts == null) {
                String sqlStr = createSql(sql, parameters);
                sts = conn.prepareStatement(sqlStr, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                if (preparedStatementSetter != null) {
                    preparedStatementSetter.setValues(sts);
                }
                LOGGER.info("Connection={} Statement={}", conn, sts);
                LOGGER.info("parameters={} Setter={}", parameters, preparedStatementSetter);
            }
            rs = sts.executeQuery();
            
            // ------------------------------------------
            // [step.2] skip rows
            // ------------------------------------------
            for(int i=0; i < getPage() * getPageSize(); i++) {
                if (rs.next() == false) {
                    isLastPage = true;
                    break;
                }
            }
            
            // ------------------------------------------
            // [step.3] create result objects
            // ------------------------------------------
            if (isLastPage == false) {
                List<T> items = new ArrayList<T>(getPageSize());
                for (int i=0; i < getPageSize(); i++) {
                    if(rs.next() == false) {
                        isLastPage = true;
                        break;
                    }
                    T item = rowMapper.mapRow(rs, getPage() * getPageSize() + i);
                    items.add(item);
                }
                results.addAll(items);
                
                if (isLastPage == true) {
                    LOGGER.info("Page={}, Item Size={}, this page is last Page.", getPage(), items.size());
                } else {
                    LOGGER.info("Page={}, Item Size={}.", getPage(), items.size());
                }
                LOGGER.debug("items value={}", items);
            }
        } catch(SQLException ex) {
            LOGGER.error("catch SQLException.", ex);
            throw new IllegalStateException("catch SQLException.", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                prepaerdStatementCloseIfNecessary(isLastPage);
            } catch(Exception ex) {
                // nothing
            }
        }
    }
    
    @Override
    protected void doClose() throws Exception {
        conn.close();
        LOGGER.info("close connection. conn={}", conn);
        super.doClose();
    }
    
    /**
     * Hot spot for Statement close.
     * @param isLastPage
     */
    protected void prepaerdStatementCloseIfNecessary(boolean isLastPage) {
        try {
            //if (sts != null && isLastPage == true) {
            //    sts.close();
            //    sts = null;
            //}
            if (sts != null) {
                sts.close();
                sts = null;
            }
        } catch(Exception ex) {
            // nothing
        }
    }
    
    /**
     * Hot spot for query Parameters.
     * @param parameters
     * @return modify parameters
     */
    protected Map<String, Object> modifyParameters(Map<String, Object> parameters) {
        return parameters;
    }
    
    /**
     * Hot spot for query. 
     * @param sqlStr
     * @param parameters
     * @return modify sql;
     */
    protected String createSql(String sqlStr, Map<String, Object> parameters) {
        return sqlStr;
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
        // nothing
    }

}
