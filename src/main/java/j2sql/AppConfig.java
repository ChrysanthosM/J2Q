package j2sql;

import j2sql.db.datasources.DataSourceForSQLite;
import j2sql.db.datasources.IDataSource;
import j2sql.core.tds.DbFieldInstances;
import j2sql.core.tds.DbTableInstances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Autowired private DbFieldInstances dbFieldInstances;
    @Autowired private DbTableInstances dbTableInstances;

    @Autowired private DataSourceForSQLite defaultDataSource;
    public IDataSource getDefaultDataSource() { return defaultDataSource; }
}
