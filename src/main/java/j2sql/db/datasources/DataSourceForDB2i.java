package j2sql.db.datasources;

import j2sql.db.model.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceForDB2i implements IDataSource {
    private @Autowired @Qualifier("db2iDataSource") DataSource db2iDataSource;

    @Override public DataSource getDS() { return db2iDataSource; }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.DB2_AS400; }
}
