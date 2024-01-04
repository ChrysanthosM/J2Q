package j2q.core.linSQL;

import j2q.commons.CommonMethods;
import j2q.j2sql.J2SQLShared;
import j2q.definitions.design.schema.PairOfTableField;
import j2q.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.core.tds.DbTable;
import com.google.common.collect.Lists;
import j2q.core.sqlCreator.LInSQLBuilderShared;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.IWhere;
import j2q.core.sqlCreator.sqlResolvers.sqlUserField.SQLFieldFromTable;
import j2q.core.sqlCreator.sqlResolvers.SqlUserSelection;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import javax.swing.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Getter
public final class LInSQLBuilderParams {
    @Setter private J2SQLShared.TypeOfSQLStatement typeOfSQL = null;
    private MutablePair<DbTable, String> workWithDbTableAsAlias;
    private final List<SqlUserSelection> sqlUserSelections = Lists.newArrayList();
    private boolean selectDistinct = false;
    @Setter private boolean applyAutoAlias = false;
    private MutablePair<BigInteger, BigInteger> limitOffset;
    private MutablePair<BigInteger, BigInteger> offsetFetch;
    private final List<IWhere> whereClauses = Lists.newArrayList();
    private final List<MutablePair<SqlUserSelection, SortOrder>> orderByFields = Lists.newArrayList();
    private MutablePair<List<SqlUserSelection>, List<IWhere>> groupBySelectionsHavingValues;
    private final List<MutableTriple<LinSQL.TypeOfJoin, LinSQL, List<IWhere>>> joinWith = Lists.newArrayList();
    private final List<MutablePair<SqlUserSelection, SqlUserSelection>> updateFieldsSetValues = Lists.newArrayList();
    private final List<List<Object>> insertRowsFieldValues = Lists.newArrayList();
    private String insertRowsFromQuery = null;
    private final List<LinSQL> unionWithQueries = Lists.newArrayList();
    @Setter private String comments = null;

    void clearSQLPropertiesMain() {
        this.typeOfSQL = null;
        this.workWithDbTableAsAlias = null;
        this.sqlUserSelections.clear();
        this.selectDistinct = false;
        this.applyAutoAlias = false;
        this.limitOffset = null;
        this.offsetFetch = null;
        this.whereClauses.clear();
        this.orderByFields.clear();
        this.groupBySelectionsHavingValues = null;
        this.joinWith.clear();
        this.updateFieldsSetValues.clear();
        this.insertRowsFieldValues.clear();
        this.insertRowsFromQuery = null;
        this.unionWithQueries.clear();
        this.comments = null;
    }

    //-------Distinct Result
    void setSelectDistinct() { this.selectDistinct = true; }

    //-------Table
    public DbTable getWorkWithTableOnlyDbTable() { return (DbTable) CommonMethods.getKeyOr(this.workWithDbTableAsAlias, null); }
    public String getWorkWithTableOnlyAsAlias() { return (String) CommonMethods.getValueOr(this.workWithDbTableAsAlias, null); }
    void setWorkWithDbTableAsAlias(DbTable setWorkWithTable, String asAlias) { this.workWithDbTableAsAlias = MutablePair.of(setWorkWithTable, asAlias); }

    //-------Select Fields/Constants/Functions/StringsFunctions
    List<GlobalFieldsDefinition.DbF> getUserSelectionsOnlyDbFieldEnum() {
        List<GlobalFieldsDefinition.DbF> returnList = Lists.newArrayList();
        sqlUserSelections.stream().filter(s -> s instanceof SQLFieldFromTable).forEach(s -> returnList.add(((SQLFieldFromTable) s).getDbFieldEnum()));
        return returnList;
    }
    void addUserSelection(Object userSelection, String asAlias) { this.sqlUserSelections.add(LInSQLBuilderShared.getSqlUserSelection(userSelection, asAlias)); }

    //-------GroupBy/Having
    void setGroupBySelectionsHavingValues(MutablePair<List<Object>, List<IWhere>> groupBySelectionsHavingValues) {
        List<SqlUserSelection> groupByUserSelections = Lists.newArrayList();
        groupBySelectionsHavingValues.getLeft().stream().filter(Objects::nonNull).forEach(g -> groupByUserSelections.add(LInSQLBuilderShared.getSqlUserSelection(g)));
        this.groupBySelectionsHavingValues = MutablePair.of(groupByUserSelections, groupBySelectionsHavingValues.getRight());
    }

    //-------OrderBy
    void addOrdering(Object userSelection, SortOrder sortOrder) { this.orderByFields.add(MutablePair.of(LInSQLBuilderShared.getSqlUserSelection(userSelection), sortOrder)); }

    //-------Limit/Offset
    void setLimitOffset(MutablePair<BigInteger, BigInteger> limitOffset) { this.limitOffset = limitOffset; }

    //-------Offset/Fetch
    void setOffsetFetch(MutablePair<BigInteger, BigInteger> offsetFetch) { this.offsetFetch = offsetFetch; }

    //-------Where Filters
    void addWhereClause(IWhere whereClause) { this.whereClauses.add(whereClause); }

    //-------Join With
    void addJoinWith(MutableTriple<LinSQL.TypeOfJoin, LinSQL, List<IWhere>> joinWith) { this.joinWith.add(joinWith); }
    MutableTriple<LinSQL.TypeOfJoin, LinSQL, List<IWhere>> getLastJoin() {
        if (CollectionUtils.isEmpty(this.joinWith)) throw new IllegalCallerException("Nothing to Join With");
        return this.joinWith.get(this.joinWith.size() - 1);
    }

    //-------Union With
    void addUnionWithQuery(LinSQL unionWithQuery) { this.unionWithQueries.add(unionWithQuery); }

    //-------Update Fields - Set Values
    void addUpdateFieldSetValue(Object updField, Object setValue) {
        this.updateFieldsSetValues.add(MutablePair.of(
                LInSQLBuilderShared.getSqlUserSelection(updField),
                LInSQLBuilderShared.getSqlUserSelection(setValue, ((PairOfTableField) updField).getDbf().getFieldDataType().getDataTypeForSQL())));
    }

    //-------Insert Rows, set Field Values
    void addInsertRowsFieldValues(List<Object> insertRowsFieldValues) {
        this.insertRowsFieldValues.add(insertRowsFieldValues);
    }
    //-------Insert Rows, from Query
    void setInsertRowsFromQuery(String fromQuery) { this.insertRowsFromQuery = fromQuery; }

}
