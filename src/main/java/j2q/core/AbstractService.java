package j2q.core;

import j2q.AppConfig;
import j2q.db.JdbcIO;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract non-sealed class AbstractService<T> implements IBaseDAO<T> {
    private @Autowired ApplicationContext context;
    @Getter private DataSource defaultDataSource;
    @Autowired @Getter private JdbcIO jdbcIO;

    @PostConstruct
    private void init() {
        defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource().getDS();
    }

    protected static String getBulkInsertSQL(String query, int size) {
        final String putValuesParams = query.substring(query.indexOf("VALUES ") + "VALUES ".length());
        final String repeatedValues = IntStream.range(0, size).mapToObj(i -> putValuesParams).collect(Collectors.joining(", "));
        return query.replace(putValuesParams, repeatedValues);
    }
}
