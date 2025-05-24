package j2q.db.datasources;

import j2q.db.definition.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceForMSSQL implements IDataSource {
    private @Autowired @Qualifier("mssqlDataSource") Optional<DataSource> mssqlDataSource;

    @Override public DataSource getDS() { return mssqlDataSource.orElseThrow(); }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.MSSQL; }
}
