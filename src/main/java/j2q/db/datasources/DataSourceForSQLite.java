package j2q.db.datasources;

import j2q.db.definition.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public final class DataSourceForSQLite implements IDataSource {
    private @Autowired @Qualifier("sqliteDataSource") DataSource sqliteDataSource;

    @Override public DataSource getDS() { return sqliteDataSource; }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.SQLite; }
}
