package j2q.db.datasources;

import j2q.db.model.GlobalDBDefinition;

import javax.sql.DataSource;

public interface IDataSource {
    DataSource getDS();
    GlobalDBDefinition.TypeOfDB getTypeOfDB();
}
