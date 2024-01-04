package j2q.db.datasources;

import j2q.db.model.GlobalDBDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceForMSSQL implements IDataSource {
    private @Autowired @Qualifier("mssqlDataSource") DataSource mssqlDataSource;

    @Override public DataSource getDS() { return mssqlDataSource; }
    @Override public GlobalDBDefinition.TypeOfDB getTypeOfDB() { return GlobalDBDefinition.TypeOfDB.MSSQL; }
}
