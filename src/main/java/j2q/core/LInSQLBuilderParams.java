package j2q.core;

import j2q.setup.definition.design.schema.sqlite.enums.DbF;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import javax.swing.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
final class LInSQLBuilderParams {
    @Setter private J2SQLShared.TypeOfSQLStatement typeOfSQL = null;
    private MutablePair<DbTable, String> workWithDbTableAsAlias = new MutablePair<>();
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
        this.workWithDbTableAsAlias = new MutablePair<>();
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
//    public DbTable getWorkWithTableOnlyDbTable() { return (DbTable) CommonMethods.getKeyOr(this.workWithDbTableAsAlias, null); }
//    public String getWorkWithTableOnlyAsAlias() { return (String) CommonMethods.getValueOr(this.workWithDbTableAsAlias, null); }
    public DbTable getWorkWithTableOnlyDbTable() { return Objects.requireNonNullElse(this.workWithDbTableAsAlias.getLeft(), null);  }
    public String getWorkWithTableOnlyAsAlias() { return Objects.requireNonNullElse(this.workWithDbTableAsAlias.getRight(), null); }
    void setWorkWithDbTableAsAlias(DbTable setWorkWithTable, String asAlias) { this.workWithDbTableAsAlias = MutablePair.of(setWorkWithTable, asAlias); }

    //-------Select Fields/Constants/Functions/StringsFunctions
    List<DbF> getUserSelectionsOnlyDbFieldEnum() {
        return sqlUserSelections.stream()
                .filter(s -> s instanceof SQLFieldFromTable)
                .map(s -> ((SQLFieldFromTable) s).getDbFieldEnum())
                .collect(Collectors.toList());
    }
    void addUserSelection(Object userSelection, String asAlias) { this.sqlUserSelections.add(LInSQLBuilderShared.getSqlUserSelection(userSelection, asAlias)); }

    //-------GroupBy/Having
    void setGroupBySelectionsHavingValues(MutablePair<List<Object>, List<IWhere>> groupBySelectionsHavingValues) {
        List<SqlUserSelection> groupByUserSelections = groupBySelectionsHavingValues.getLeft().stream()
                .filter(Objects::nonNull)
                .map(LInSQLBuilderShared::getSqlUserSelection)
                .collect(Collectors.toList());
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
        return this.joinWith.getLast();
    }

    //-------Union With
    void addUnionWithQuery(LinSQL unionWithQuery) { this.unionWithQueries.add(unionWithQuery); }

    //-------Update Fields - Set Values
    void addUpdateFieldSetValue(Object updField, Object setValue) {
        this.updateFieldsSetValues.add(MutablePair.of(
                LInSQLBuilderShared.getSqlUserSelection(updField),
                LInSQLBuilderShared.getSqlUserSelection(setValue, ((PairOfTableField) updField).getDbf().getFieldDataType().getInQuotesRequirement())));
    }

    //-------Insert Rows, set Field Values
    void addInsertRowsFieldValues(List<Object> insertRowsFieldValues) {
        this.insertRowsFieldValues.add(insertRowsFieldValues);
    }
    //-------Insert Rows, from Query
    void setInsertRowsFromQuery(String fromQuery) { this.insertRowsFromQuery = fromQuery; }

}
