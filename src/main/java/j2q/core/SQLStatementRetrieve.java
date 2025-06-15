package j2q.core;

import j2q.db.datasource.WorkWithDataSource;
import lombok.Getter;

@Getter
final class SQLStatementRetrieve {
    private final SQLRetrieverForDBs sqlRetrieverForDB;
    SQLStatementRetrieve(WorkWithDataSource.DataSourceType typeOfDB,
                         LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized,
                         String dbPrefixForTable) {
        switch (typeOfDB) {
            case SQLITE -> this.sqlRetrieverForDB = new SQLRetrieverForDB_SQLite(typeOfNamingSystemOrNormalized);
            case DB2_AS400 -> this.sqlRetrieverForDB = new SQLRetrieverForDB_DB2(typeOfNamingSystemOrNormalized, dbPrefixForTable);
            case MSSQL -> this.sqlRetrieverForDB = new SQLRetrieverForDB_MSSQL(typeOfNamingSystemOrNormalized, dbPrefixForTable);
            default -> throw new UnsupportedOperationException(typeOfDB + " is not supported");
        }
    }

    void setWorkLInSQLBuilderParams(LInSQLBuilderParams workLInSQLBuilderParams) { this.sqlRetrieverForDB.setWorkLInSQLBuilderParams(workLInSQLBuilderParams); }

    BuildSQLWorkTable getWorkBuildSQLWorkTable() { return this.sqlRetrieverForDB.getWorkBuildSQLWorkTable(); }
    BuildSQLJoinWith getWorkBuildSQLJoinWith() { return this.sqlRetrieverForDB.getWorkBuildSQLJoinWith(); }
    BuildSQLSelectFields getWorkBuildSQLSelectFields() { return this.sqlRetrieverForDB.getWorkBuildSQLSelectFields(); }
    BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return this.sqlRetrieverForDB.getWorkBuildSQLWhereFilters(); }
    BuildSQLOrderBy getWorkBuildSQLOrderBy() { return this.sqlRetrieverForDB.getWorkBuildSQLOrderBy(); }
    BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return this.sqlRetrieverForDB.getWorkBuildSQLGroupByHavingValues(); }

    String getSQLStatementForSelect() { return this.sqlRetrieverForDB.getSQLStatementForSelect().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    String getSQLStatementForDelete() { return this.sqlRetrieverForDB.getSQLStatementForDelete().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    String getSQLStatementForUpdate() { return this.sqlRetrieverForDB.getSQLStatementForUpdate().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    String getSQLStatementForInsert() { return this.sqlRetrieverForDB.getSQLStatementForInsert().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    String getSQLStatementForInsertGetOnlyValues() { return this.sqlRetrieverForDB.getSQLStatementForInsertGetOnlyValues().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }

}
