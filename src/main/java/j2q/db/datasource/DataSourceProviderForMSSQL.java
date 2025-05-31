package j2q.db.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceProviderForMSSQL extends AbstractDataSourceProvider {

    @Autowired
    public DataSourceProviderForMSSQL(@Qualifier("mssqlDataSource") Optional<DataSource> mssqlDataSource) {
        super(mssqlDataSource);
    }

    @Override public int getMaxParamsAllowedInQuery() { return 2099; }
}
