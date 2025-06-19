package j2q.core;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

abstract sealed class SQLRetrieverForDBs extends SQLRetrieverCore implements IDefaultsSQLRetrieverForDBs
        permits SQLRetrieverForDB_DB2_I, SQLRetrieverForDB_MSSQL, SQLRetrieverForDB_SQLite {

    private final LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized;
    private final String dbPrefixForTableLocation;

    SQLRetrieverForDBs(LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized, String dbPrefixForTableLocation) {
        this.typeOfNamingSystemOrNormalized = typeOfNamingSystemOrNormalized;
        this.dbPrefixForTableLocation = dbPrefixForTableLocation;
    }

    @Override public LinSQL.TypeOfNamingSystemOrNormalized getTypeOfNamingSystemOrNormalized() { return this.typeOfNamingSystemOrNormalized; }
    @Override public String getDbPrefixForTableLocation() { return this.dbPrefixForTableLocation; }

    boolean isNamingIsNormalized() { return (this.typeOfNamingSystemOrNormalized == LinSQL.TypeOfNamingSystemOrNormalized.NORMALIZED); }

    @Getter @Setter(AccessLevel.PROTECTED) private LInSQLBuilderParams workLInSQLBuilderParams = null;

    @Override public String createComments(String comments) { return StringUtils.isBlank(comments) ? StringUtils.EMPTY : " /*".concat(comments).concat("*/") ; }


    protected String getSQLStatementFromWhereGroupByOrderBy() {
        String returnStmt = "FROM " + super.getWorkBuildSQLWorkTable().getStringForSQL() +
                super.getWorkBuildSQLJoinWith().getJoinWithTablesOnFieldsForSQL() +
                BuildSQLWhereFilters.getWhereFiltersWithJoins(super.getWorkBuildSQLWhereFilters().getStringForSQL(), super.getWorkBuildSQLJoinWith().getWhereJoinFiltersForSQL()) +
                super.getWorkBuildSQLGroupByHavingValues().getStringForSQL() +
                super.getWorkBuildSQLOrderBy().getStringForSQL() +
                super.getWorkBuildSQLUnionWith().getStringForSQL();
        return returnStmt;
    }


    @Override public String getDefaultSQLStatementForSelect() { throw new UnsupportedOperationException(); }

    @Override public String getDefaultSQLStatementForDelete() {
        InitializationStatus initSts = initSQLStatementForDelete(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        String returnStmt = "DELETE " + "FROM " + super.getWorkBuildSQLWorkTable().getStringForSQL() +
                BuildSQLWhereFilters.getWhereFilters(super.getWorkBuildSQLWhereFilters().getStringForSQL());
        return returnStmt.trim();
    }

    @Override public String getDefaultSQLStatementForUpdate() {
        InitializationStatus initSts = initSQLStatementForUpdate(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        String returnStmt = "UPDATE " + super.getWorkBuildSQLWorkTable().getStringForSQL() +
                super.getWorkBuildSQLUpdateFields().getStringForSQL() +
                BuildSQLWhereFilters.getWhereFilters(super.getWorkBuildSQLWhereFilters().getStringForSQL());
        return returnStmt.trim();
    }

    @Override public String getDefaultSQLStatementForInsert() {
        InitializationStatus initSts = initSQLStatementForInsert(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        StringBuilder returnStmt = new StringBuilder("INSERT INTO ");
        returnStmt.append(super.getWorkBuildSQLWorkTable().getStringForSQL());
        if (Strings.isNullOrEmpty(workLInSQLBuilderParams.getInsertRowsFromQuery())) {
            returnStmt.append(getSQLStatementForInsertFromValues());
        } else {
            returnStmt.append(workLInSQLBuilderParams.getInsertRowsFromQuery());
        }
        return returnStmt.toString().trim();
    }
    private String getSQLStatementForInsertFromValues() {
        String returnStmt = super.getWorkBuildSQLInsertRows().getInsertIntoFieldsForSQL() +
                "VALUES " + super.getWorkBuildSQLInsertRows().getStringForSQL();
        return returnStmt;
    }

    @Override public String getDefaultSQLStatementForInsertGetOnlyValues() { return null; }



}
