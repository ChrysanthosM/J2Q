package j2q.db.datasource;

import javax.sql.DataSource;
import java.util.Optional;

public sealed abstract class AbstractDataSourceProvider implements DataSourceProvider
        permits DataSourceProviderForDB2i, DataSourceProviderForMSSQL, DataSourceProviderForSQLite {

    protected final Optional<DataSource> optionalDataSource;

    protected AbstractDataSourceProvider(Optional<DataSource> optionalDataSource) {
        this.optionalDataSource = optionalDataSource;
    }

    @Override
    public DataSource getDS() {
        return optionalDataSource.orElseThrow(() -> new IllegalStateException("DataSource not configured"));
    }
}
