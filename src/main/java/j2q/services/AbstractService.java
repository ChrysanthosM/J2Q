package j2q.services;

import j2q.AppConfig;
import j2q.db.JdbcIO;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

public abstract class AbstractService {
    @Autowired private ApplicationContext context;
    @Getter private DataSource defaultDataSource;
    @Autowired @Getter private JdbcIO jdbcIO;

    @PostConstruct
    private void init() {
        defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource().getDS();
    }
}
