package j2q.db.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceProviderForSQLite extends AbstractDataSourceProvider {

    @Autowired
    public DataSourceProviderForSQLite(@Qualifier("sqliteDataSource") Optional<DataSource> sqliteDataSource) {
        super(sqliteDataSource);
    }

    @Override public boolean isAutomaticTransactional() { return false; }
}
