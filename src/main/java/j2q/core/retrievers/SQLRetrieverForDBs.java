package j2q.core.retrievers;

import j2q.core.linsql.LinSQL;
import j2q.core.linsql.LInSQLBuilderParams;
import j2q.core.creator.sqlbuilder.BuildSQLWhereFilters;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public sealed abstract class SQLRetrieverForDBs extends SQLRetrieverCore implements IDefaultsSQLRetrieverForDBs
        permits SQLRetrieverForDB_DB2, SQLRetrieverForDB_MSSQL, SQLRetrieverForDB_SQLite {
    private final LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized;
    private final String dbPrefixForTableLocation;
    private final boolean tableMustPrefixFields;
    SQLRetrieverForDBs(LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized, String dbPrefixForTableLocation, boolean tableMustPrefixFields) {
        this.typeOfNamingSystemOrNormalized = typeOfNamingSystemOrNormalized;
        this.dbPrefixForTableLocation = dbPrefixForTableLocation;
        this.tableMustPrefixFields = tableMustPrefixFields;
    }
    @Override public LinSQL.TypeOfNamingSystemOrNormalized getTypeOfNamingSystemOrNormalized() { return this.typeOfNamingSystemOrNormalized; }
    @Override public String getDbPrefixForTableLocation() { return this.dbPrefixForTableLocation; }
    @Override public boolean getTableMustPrefixFields() {
        return this.tableMustPrefixFields;
    }

    public boolean isNamingIsNormalized() { return (this.typeOfNamingSystemOrNormalized == LinSQL.TypeOfNamingSystemOrNormalized.NORMALIZED); }

    @Getter @Setter(AccessLevel.PROTECTED) private LInSQLBuilderParams workLInSQLBuilderParams = null;

    @Override public String createComments(String comments) { return StringUtils.isBlank(comments) ? StringUtils.EMPTY : " /*".concat(comments).concat("*/") ; }


    protected String getSQLStatementFromWhereGroupByOrderBy() {
        StringBuilder returnStmt = new StringBuilder();
        returnStmt.append("FROM ").append(super.getWorkBuildSQLWorkTable().getStringForSQL());
        returnStmt.append(super.getWorkBuildSQLJoinWith().getJoinWithTablesOnFieldsForSQL());
        returnStmt.append(BuildSQLWhereFilters.getWhereFiltersWithJoins(super.getWorkBuildSQLWhereFilters().getStringForSQL(), super.getWorkBuildSQLJoinWith().getWhereJoinFiltersForSQL()));
        returnStmt.append(super.getWorkBuildSQLGroupByHavingValues().getStringForSQL());
        returnStmt.append(super.getWorkBuildSQLOrderBy().getStringForSQL());
        returnStmt.append(super.getWorkBuildSQLUnionWith().getStringForSQL());
        return returnStmt.toString();
    }


    @Override public String getDefaultSQLStatementForSelect() { throw new UnsupportedOperationException(); }

    @Override public String getDefaultSQLStatementForDelete() {
        InitializationStatus initSts = initSQLStatementForDelete(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        StringBuilder returnStmt = new StringBuilder("DELETE ");
        returnStmt.append("FROM ").append(super.getWorkBuildSQLWorkTable().getStringForSQL());
        returnStmt.append(BuildSQLWhereFilters.getWhereFilters(super.getWorkBuildSQLWhereFilters().getStringForSQL()));
        return returnStmt.toString().trim();
    }

    @Override public String getDefaultSQLStatementForUpdate() {
        InitializationStatus initSts = initSQLStatementForUpdate(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        StringBuilder returnStmt = new StringBuilder("UPDATE ");
        returnStmt.append(super.getWorkBuildSQLWorkTable().getStringForSQL());
        returnStmt.append(super.getWorkBuildSQLUpdateFields().getStringForSQL());
        returnStmt.append(BuildSQLWhereFilters.getWhereFilters(super.getWorkBuildSQLWhereFilters().getStringForSQL()));
        return returnStmt.toString().trim();
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
        StringBuilder returnStmt = new StringBuilder();
        returnStmt.append(super.getWorkBuildSQLInsertRows().getInsertIntoFieldsForSQL());
        returnStmt.append("VALUES ").append(super.getWorkBuildSQLInsertRows().getStringForSQL());
        return returnStmt.toString();
    }

    @Override public String getDefaultSQLStatementForInsertGetOnlyValues() { return null; }



}
