package j2sql.db.datasources;

import j2sql.db.model.GlobalDBDefinition;

import javax.sql.DataSource;

public interface IDataSource {
    DataSource getDS();
    GlobalDBDefinition.TypeOfDB getTypeOfDB();
}
