package j2q;

import j2q.db.datasources.DataSourceForSQLite;
import j2q.db.datasources.IDataSource;
import j2q.core.tds.DbFieldInstances;
import j2q.core.tds.DbTableInstances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private @Autowired DbFieldInstances dbFieldInstances;
    private @Autowired DbTableInstances dbTableInstances;

    private @Autowired DataSourceForSQLite defaultDataSource;
    public IDataSource getDefaultDataSource() { return defaultDataSource; }
}
