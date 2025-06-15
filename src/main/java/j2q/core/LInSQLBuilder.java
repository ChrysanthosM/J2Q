package j2q.core;

import j2q.db.datasource.WorkWithDataSource;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;


final class LInSQLBuilder {
    @Getter(AccessLevel.PACKAGE) private final LInSQLBuilderParams workLInSQLBuilderParams = new LInSQLBuilderParams();

    private final SQLStatementRetrieve sqlStatementRetrieve;
    SQLRetrieverForDBs getSqlRetrieverForDB() { return this.sqlStatementRetrieve.getSqlRetrieverForDB(); }

    static LInSQLBuilder createDefault(WorkWithDataSource.DataSourceType dataSourceType, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized) {
        return new LInSQLBuilder(dataSourceType, StringUtils.EMPTY, false, typeOfNamingSystemOrNormalized);
    }

    static LInSQLBuilder createForDB2(String dbPrefixForTableOrLocation) { return createForDB2(dbPrefixForTableOrLocation, LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM, false); }
    static LInSQLBuilder createForDB2(String dbPrefixForTableOrLocation, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized) { return createForDB2(dbPrefixForTableOrLocation, typeOfNamingSystemOrNormalized, false); }
    static LInSQLBuilder createForDB2(String dbPrefixForTableOrLocation, boolean tableMustPrefixFields) { return createForDB2(dbPrefixForTableOrLocation, LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM, tableMustPrefixFields); }
    static LInSQLBuilder createForDB2(String dbPrefixForTableOrLocation, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized, boolean tableMustPrefixFields) {
        return new LInSQLBuilder(WorkWithDataSource.DataSourceType.DB2_AS400, dbPrefixForTableOrLocation, tableMustPrefixFields, typeOfNamingSystemOrNormalized);
    }

    private LInSQLBuilder(WorkWithDataSource.DataSourceType typeOfDB, String dbPrefixForTableOrLocation, boolean tableMustPrefixFields, LinSQL.TypeOfNamingSystemOrNormalized typeOfNamingSystemOrNormalized) {
        this.sqlStatementRetrieve = new SQLStatementRetrieve(typeOfDB, dbPrefixForTableOrLocation, tableMustPrefixFields, typeOfNamingSystemOrNormalized);
    }


    void clearSQLProperties() { this.workLInSQLBuilderParams.clearSQLPropertiesMain(); }

    private String sqlStatement = null;
    String getSQLStatement() {
        if (StringUtils.isNotBlank(this.sqlStatement)) return this.sqlStatement;

        if (this.workLInSQLBuilderParams.isApplyAutoAlias()) {
            this.workLInSQLBuilderParams.getJoinWith().forEach(j -> j.getMiddle().setApplyAutoAlias());
            this.workLInSQLBuilderParams.getUnionWithQueries().forEach(LinSQL::setApplyAutoAlias);
        }

        this.sqlStatementRetrieve.setWorkLInSQLBuilderParams(this.workLInSQLBuilderParams);
        this.sqlStatement = switch (this.workLInSQLBuilderParams.getTypeOfSQL()) {
            case SQL_SELECT -> this.sqlStatementRetrieve.getSQLStatementForSelect();
            case SQL_INSERT -> this.sqlStatementRetrieve.getSQLStatementForInsert();
            case SQL_UPDATE -> this.sqlStatementRetrieve.getSQLStatementForUpdate();
            case SQL_DELETE -> this.sqlStatementRetrieve.getSQLStatementForDelete();
        };

        this.sqlStatement = StringUtils.trimToEmpty(this.sqlStatement.replaceAll("\\s+", StringUtils.SPACE).replaceAll("\\s+,", ","));
        return this.sqlStatement;
    }
    String getFromInsertOnlyTheValues() {
        if (StringUtils.isNotBlank(this.sqlStatement)) return this.sqlStatement;
        this.sqlStatementRetrieve.setWorkLInSQLBuilderParams(this.workLInSQLBuilderParams);
        this.sqlStatement = this.sqlStatementRetrieve.getSQLStatementForInsertGetOnlyValues();
        this.sqlStatement = StringUtils.trimToEmpty(this.sqlStatement.replaceAll("\\s+", StringUtils.SPACE).replaceAll("\\s+,", ","));
        return this.sqlStatement;
    }


    BuildSQLWorkTable getWorkBuildSQLWorkTable() { return this.sqlStatementRetrieve.getWorkBuildSQLWorkTable(); }
    BuildSQLJoinWith getWorkBuildSQLJoinWith() { return this.sqlStatementRetrieve.getWorkBuildSQLJoinWith(); }
    BuildSQLSelectFields getWorkBuildSQLSelectFields() { return this.sqlStatementRetrieve.getWorkBuildSQLSelectFields(); }
    BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return this.sqlStatementRetrieve.getWorkBuildSQLWhereFilters(); }
    BuildSQLOrderBy getWorkBuildSQLOrderBy() { return this.sqlStatementRetrieve.getWorkBuildSQLOrderBy(); }
    BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return this.sqlStatementRetrieve.getWorkBuildSQLGroupByHavingValues(); }
}
