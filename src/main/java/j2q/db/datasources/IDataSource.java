package j2q.db.datasources;

import j2q.db.definition.GlobalDBDefinition;

import javax.sql.DataSource;

public sealed interface IDataSource permits DataSourceForDB2i, DataSourceForMSSQL, DataSourceForSQLite {
    DataSource getDS();
    GlobalDBDefinition.TypeOfDB getTypeOfDB();
}
