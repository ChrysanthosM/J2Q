package j2q.db.datasources;

import j2q.db.definition.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceForSQLite implements IDataSource {
    private @Autowired @Qualifier("sqliteDataSource") Optional<DataSource> sqliteDataSource;

    @Override public DataSource getDS() { return sqliteDataSource.orElseThrow(); }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.SQLite; }
}
