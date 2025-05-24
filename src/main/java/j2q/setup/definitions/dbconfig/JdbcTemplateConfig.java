package j2q.setup.definitions.dbconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Value("${sqlite.datasource.url:null}") private String sqliteDataSourceUrl;
    @Bean(name = "sqliteDataSource")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "sqlite")
    public DataSource sqliteDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(sqliteDataSourceUrl);
        return dataSource;
    }
    @Bean(name = "sqliteJdbcTemplate")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "sqlite")
    public JdbcTemplate sqliteJdbcTemplate(@Qualifier("sqliteDataSource") DataSource sqliteDataSource) {
        return new JdbcTemplate(sqliteDataSource);
    }

    @Value("${mssql.url:null}") private String mssqlUrl;
    @Value("${mssql.username:null}") private String mssqlUsername;
    @Value("${mssql.password:null}") private String mssqlPassword;
    @Bean(name = "mssqlDataSource")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "mssql")
    public DataSource mssqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(mssqlUrl);
        dataSource.setUsername(mssqlUsername);
        dataSource.setPassword(mssqlPassword);
        return dataSource;
    }
    @Bean(name = "mssqlJdbcTemplate")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "mssql")
    public JdbcTemplate mssqlJdbcTemplate(@Qualifier("mssqlDataSource") DataSource mssqlDataSource) {
        return new JdbcTemplate(mssqlDataSource);
    }

    @Value("${db2i.url:null}") private String db2iUrl;
    @Value("${db2i.username:null}") private String db2iUsername;
    @Value("${db2i.password:null}") private String db2iPassword;
    @Bean(name = "db2iDataSource")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "db2i")
    public DataSource db2iDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(db2iUrl);
        dataSource.setUsername(db2iUsername);
        dataSource.setPassword(db2iPassword);
        return dataSource;
    }
    @Bean(name = "db2iJdbcTemplate")
    @ConditionalOnProperty(name = "datasource.type", havingValue = "db2i")
    public JdbcTemplate db2iJdbcTemplate(@Qualifier("db2iDataSource") DataSource db2iDataSource) {
        return new JdbcTemplate(db2iDataSource);
    }
}
