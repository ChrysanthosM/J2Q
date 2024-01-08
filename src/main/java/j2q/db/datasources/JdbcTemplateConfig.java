package j2q.db.datasources;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    @Bean(name = "sqliteDataSource")
    public DataSource sqliteDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlite:D:\\MyDocuments\\Games\\TWMultiPlayer\\TWP.db");
        return dataSource;
    }
    @Bean(name = "sqliteJdbcTemplate")
    public JdbcTemplate sqliteJdbcTemplate(@Qualifier("sqliteDataSource") DataSource sqliteDataSource) {
        return new JdbcTemplate(sqliteDataSource);
    }

    @Bean(name = "mssqlDataSource")
    public DataSource mssqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=databaseName=CHRYSANTHOS-PC\\SQLEXPRESS");
        dataSource.setUsername("df5user");
        dataSource.setPassword("df5user");
        return dataSource;
    }
    @Bean(name = "mssqlJdbcTemplate")
    public JdbcTemplate mssqlJdbcTemplate(@Qualifier("mssqlDataSource") DataSource mssqlDataSource) {
        return new JdbcTemplate(mssqlDataSource);
    }

    @Bean(name = "db2iDataSource")
    public DataSource db2iDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:as400://YOUR_IBM_I_SYSTEM/YOUR_DATABASE_NAME");
        dataSource.setUsername("YOUR_DB2_USER");
        dataSource.setPassword("YOUR_DB2_PASSWORD");
        return dataSource;
    }
    @Bean(name = "db2iJdbcTemplate")
    public JdbcTemplate db2iJdbcTemplate(@Qualifier("db2iDataSource") DataSource db2iDataSource) {
        return new JdbcTemplate(db2iDataSource);
    }
}
