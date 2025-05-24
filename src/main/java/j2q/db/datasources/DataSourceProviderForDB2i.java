package j2q.db.datasources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceProviderForDB2i extends AbstractDataSourceProvider {

    @Autowired
    public DataSourceProviderForDB2i(@Qualifier("db2iDataSource") Optional<DataSource> db2iDataSource) {
        super(db2iDataSource);
    }
}
