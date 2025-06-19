package j2q.db.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceResolver {
    private @Autowired DataSourceProviderForSQLite dataSourceForSQLite;
    private @Autowired DataSourceProviderForMSSQL dataSourceForMSSQL;
    private @Autowired DataSourceProviderForDB2i dataSourceProviderForDB2i;

    public DataSourceProvider getDefaultDataSource(WorkWithDataSource.DataSourceType dataSourceType) {
        return switch (dataSourceType) {
            case SQLITE -> dataSourceForSQLite;
            case MSSQL -> dataSourceForMSSQL;
            case DB2_I -> dataSourceProviderForDB2i;
        };
    }
}
