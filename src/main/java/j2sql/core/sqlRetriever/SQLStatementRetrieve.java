package j2sql.core.sqlRetriever;

import j2sql.core.linSQL.LinSQL;
import j2sql.core.linSQL.LInSQLBuilderParams;
import j2sql.core.sqlCreator.sqlBuilder.*;
import j2sql.db.model.GlobalDBDefinition;
import lombok.Getter;

public final class SQLStatementRetrieve {
    @Getter private final SQLRetrieverForDBs sqlRetrieverForDB;
    public SQLStatementRetrieve(GlobalDBDefinition.TypeOfDB typeOfDB, String dbPrefixForTableLocation, boolean tableMustPrefixFields,
                                LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized) {
        switch (typeOfDB) {
            case SQLite -> this.sqlRetrieverForDB = new SQLRetrieverForDB_SQLite(typeOfNamingSystemOrNormalized);
            case DB2_AS400 -> this.sqlRetrieverForDB = new SQLRetrieverForDB_DB2(typeOfNamingSystemOrNormalized, dbPrefixForTableLocation, tableMustPrefixFields);
            case MSSQL -> this.sqlRetrieverForDB = new SQLRetrieverForDB_MSSQL(typeOfNamingSystemOrNormalized, dbPrefixForTableLocation, tableMustPrefixFields);
            default -> throw new UnsupportedOperationException(typeOfDB + " is not supported");
        }
    }

    public void setWorkLInSQLBuilderParams(LInSQLBuilderParams workLInSQLBuilderParams) { this.sqlRetrieverForDB.setWorkLInSQLBuilderParams(workLInSQLBuilderParams); }

    public BuildSQLWorkTable getWorkBuildSQLWorkTable() { return this.sqlRetrieverForDB.getWorkBuildSQLWorkTable(); }
    public BuildSQLJoinWith getWorkBuildSQLJoinWith() { return this.sqlRetrieverForDB.getWorkBuildSQLJoinWith(); }
    public BuildSQLSelectFields getWorkBuildSQLSelectFields() { return this.sqlRetrieverForDB.getWorkBuildSQLSelectFields(); }
    public BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return this.sqlRetrieverForDB.getWorkBuildSQLWhereFilters(); }
    public BuildSQLOrderBy getWorkBuildSQLOrderBy() { return this.sqlRetrieverForDB.getWorkBuildSQLOrderBy(); }
    public BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return this.sqlRetrieverForDB.getWorkBuildSQLGroupByHavingValues(); }

    public String getSQLStatementForSelect() { return this.sqlRetrieverForDB.getSQLStatementForSelect().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    public String getSQLStatementForDelete() { return this.sqlRetrieverForDB.getSQLStatementForDelete().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    public String getSQLStatementForUpdate() { return this.sqlRetrieverForDB.getSQLStatementForUpdate().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    public String getSQLStatementForInsert() { return this.sqlRetrieverForDB.getSQLStatementForInsert().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }
    public String getSQLStatementForInsertGetOnlyValues() { return this.sqlRetrieverForDB.getSQLStatementForInsertGetOnlyValues().concat(this.sqlRetrieverForDB.createComments(this.sqlRetrieverForDB.getWorkLInSQLBuilderParams().getComments())); }

}
