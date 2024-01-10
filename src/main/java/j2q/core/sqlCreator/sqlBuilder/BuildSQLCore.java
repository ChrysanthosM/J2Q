package j2q.core.sqlCreator.sqlBuilder;

import org.apache.commons.lang3.StringUtils;

abstract sealed class BuildSQLCore implements IBuildSQLCore
        permits BuildSQLGroupByHavingValues, BuildSQLInsertRows, BuildSQLJoinWith, BuildSQLOrderBy, BuildSQLSelectFields,
        BuildSQLUnionWith, BuildSQLUpdateFields, BuildSQLWhereFilters, BuildSQLWorkTable {

    private String stringForSQL = StringUtils.EMPTY;
    @Override public String getStringForSQL() { return this.stringForSQL.concat(StringUtils.SPACE); }
    @Override public void setStringForSQL(String setString) { this.stringForSQL = setString; }

}
