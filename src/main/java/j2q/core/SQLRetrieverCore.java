package j2q.core;

import j2q.setup.definitions.design.schema.enums.DbF;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Set;

abstract sealed class SQLRetrieverCore implements IDeployMethods, IDeploySQLStatements, IDeploySQLFunctions
        permits SQLRetrieverForDBs {
    protected abstract String getDbPrefixForTableLocation();
    protected abstract LinSQL.TypeOfNamingSystemOrNormalized getTypeOfNamingSystemOrNormalized();
    protected abstract boolean getTableMustPrefixFields();

    //initSQLStatement
    @Getter @Setter(AccessLevel.PROTECTED) private BuildSQLWorkTable workBuildSQLWorkTable = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLSelectFields workBuildSQLSelectFields = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLWhereFilters workBuildSQLWhereFilters = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLOrderBy workBuildSQLOrderBy = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLGroupByHavingValues workBuildSQLGroupByHavingValues = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLJoinWith workBuildSQLJoinWith = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLUnionWith workBuildSQLUnionWith = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLUpdateFields workBuildSQLUpdateFields = null;
    @Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED) private BuildSQLInsertRows workBuildSQLInsertRows = null;

    @Getter private final Set<Triple<DbTable, String, List<DbF>>> availableTablesWithFields = Sets.newHashSet();
    void addAvailableTableWithFields(Triple<DbTable, String, List<DbF>> tableWithFields) { this.availableTablesWithFields.add(tableWithFields); }
    void addAvailableTableWithFields(Set<Triple<DbTable, String, List<DbF>>> tablesWithFields) { this.availableTablesWithFields.addAll(tablesWithFields); }
    protected void clearAvailableTablesWithFields() { this.availableTablesWithFields.clear(); }

    @Getter private final List<String> fieldMapper = Lists.newArrayList();
    void addFieldMapper(String fieldMapper) { this.fieldMapper.add(fieldMapper); }
    protected void clearFieldMapper() { this.fieldMapper.clear(); }

    private static void initSQLStatementCommons(SQLRetrieverForDBs initSQLRetrieverForDB) {
        initSQLRetrieverForDB.setWorkBuildSQLWorkTable(null);
        initSQLRetrieverForDB.setWorkBuildSQLSelectFields(null);
        initSQLRetrieverForDB.setWorkBuildSQLWhereFilters(null);
        initSQLRetrieverForDB.setWorkBuildSQLOrderBy(null);
        initSQLRetrieverForDB.setWorkBuildSQLGroupByHavingValues(null);
        initSQLRetrieverForDB.setWorkBuildSQLJoinWith(null);
        initSQLRetrieverForDB.setWorkBuildSQLUnionWith(null);
        initSQLRetrieverForDB.setWorkBuildSQLUpdateFields(null);
        initSQLRetrieverForDB.setWorkBuildSQLInsertRows(null);

        initSQLRetrieverForDB.clearAvailableTablesWithFields();
        initSQLRetrieverForDB.clearFieldMapper();

        initSQLRetrieverForDB.setWorkBuildSQLWorkTable(BuildSQLWorkTable.createFor(initSQLRetrieverForDB));
    }

    protected static InitializationStatus initSQLStatementForSelect(SQLRetrieverForDBs initSQLRetrieverForDB) {
        try {
            initSQLStatementCommons(initSQLRetrieverForDB);
            initSQLRetrieverForDB.setWorkBuildSQLSelectFields(BuildSQLSelectFields.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLWhereFilters(BuildSQLWhereFilters.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLOrderBy(BuildSQLOrderBy.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLGroupByHavingValues(BuildSQLGroupByHavingValues.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLJoinWith(BuildSQLJoinWith.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLUnionWith(BuildSQLUnionWith.createFor(initSQLRetrieverForDB));
            return InitializationStatus.returnOK();
        } catch (Exception e) {
            return InitializationStatus.returnException(e);
        }
    }
    protected static InitializationStatus initSQLStatementForDelete(SQLRetrieverForDBs initSQLRetrieverForDB) {
        try {
            initSQLStatementCommons(initSQLRetrieverForDB);
            initSQLRetrieverForDB.setWorkBuildSQLWhereFilters(BuildSQLWhereFilters.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLOrderBy(BuildSQLOrderBy.createFor(initSQLRetrieverForDB));
            return InitializationStatus.returnOK();
        } catch (Exception e) {
            return InitializationStatus.returnException(e);
        }
    }
    protected static InitializationStatus initSQLStatementForUpdate(SQLRetrieverForDBs initSQLRetrieverForDB) {
        try {
            initSQLStatementCommons(initSQLRetrieverForDB);
            initSQLRetrieverForDB.setWorkBuildSQLUpdateFields(BuildSQLUpdateFields.createFor(initSQLRetrieverForDB));
            initSQLRetrieverForDB.setWorkBuildSQLWhereFilters(BuildSQLWhereFilters.createFor(initSQLRetrieverForDB));
            return InitializationStatus.returnOK();
        } catch (Exception e) {
            return InitializationStatus.returnException(e);
        }
    }
    protected static InitializationStatus initSQLStatementForInsert(SQLRetrieverForDBs initSQLRetrieverForDB) {
        try {
            initSQLStatementCommons(initSQLRetrieverForDB);
            initSQLRetrieverForDB.setWorkBuildSQLInsertRows(BuildSQLInsertRows.createFor(initSQLRetrieverForDB));
            return InitializationStatus.returnOK();
        } catch (Exception e) {
            return InitializationStatus.returnException(e);
        }
    }


    protected static class InitializationStatus {
        enum ReturnSts { OK, ERROR_EXISTS }

        private final ReturnSts sts;
        private String msg = StringUtils.EMPTY;
        private Exception exc = null;

        private InitializationStatus(ReturnSts setSts) {
            this.sts = setSts;
        }
        private InitializationStatus(ReturnSts setSts, String setMsg) {
            this.sts = setSts;
            this.msg = setMsg;
        }
        private InitializationStatus(ReturnSts setSts, Exception setExc) {
            this.sts = setSts;
            this.exc = setExc;
        }
        protected ReturnSts getSts() {
            return this.sts;
        }
        protected String getMsg() {
            return this.msg;
        }
        protected Exception getExc() {
            return this.exc;
        }

        protected static InitializationStatus returnOK() {
            return new InitializationStatus(ReturnSts.OK);
        }
        protected static InitializationStatus returnError(String setMsg) {
            return new InitializationStatus(ReturnSts.ERROR_EXISTS, setMsg);
        }
        protected static InitializationStatus returnException(Exception setExc) {
            return new InitializationStatus(ReturnSts.ERROR_EXISTS, setExc);
        }
    }


}
