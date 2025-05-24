package j2q.db.datasources;

import j2q.db.definition.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public final class DataSourceForDB2i implements IDataSource {
    private @Autowired @Qualifier("db2iDataSource") Optional<DataSource> db2iDataSource;

    @Override public DataSource getDS() { return db2iDataSource.orElseThrow(); }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.DB2_AS400; }
}
