package j2q.core;

import j2q.db.datasources.IDataSource;
import j2q.setup.definitions.design.schema.enums.DbF;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class LinSQL {
    public enum TypeOfNamingSystemOrNormalized { SYSTEM, NORMALIZED }
    public enum TypeOfLogicalOperator { AND, OR }
    public enum TypeOfComparison { IS_NULL, LT, LE, EQ, GE, GT, NE }
    public enum TypeOfJoin { FULL, JOIN, LEFT, RIGHT }

    private final LInSQLBuilder workLInSQLBuilder;
    public SQLRetrieverForDBs getSqlRetrieverForDB() { return workLInSQLBuilder.getSqlRetrieverForDB(); }
    public BuildSQLWorkTable getWorkBuildSQLWorkTable() { return workLInSQLBuilder.getWorkBuildSQLWorkTable(); }
    public BuildSQLJoinWith getWorkBuildSQLJoinWith() { return workLInSQLBuilder.getWorkBuildSQLJoinWith(); }
    public BuildSQLSelectFields getWorkBuildSQLSelectFields() { return workLInSQLBuilder.getWorkBuildSQLSelectFields(); }
    public BuildSQLWhereFilters getWorkBuildSQLWhereFilters() { return workLInSQLBuilder.getWorkBuildSQLWhereFilters(); }
    public BuildSQLOrderBy getWorkBuildSQLOrderBy() { return workLInSQLBuilder.getWorkBuildSQLOrderBy(); }
    public BuildSQLGroupByHavingValues getWorkBuildSQLGroupByHavingValues() { return workLInSQLBuilder.getWorkBuildSQLGroupByHavingValues(); }
    public List<String> getFieldMapper() { return workLInSQLBuilder.getSqlRetrieverForDB().getFieldMapper(); }

    public static LinSQL create(IDataSource dataSource, boolean normalizeNames) { return new LinSQL(dataSource, normalizeNames); }
    private LinSQL(IDataSource dataSource, boolean normalizeNames) {
        switch (dataSource.getTypeOfDB()) {
            case DB2_AS400 -> workLInSQLBuilder = LInSQLBuilder.createForDB2(dataSource.getTypeOfDB().getTablePrefixToReplace(), normalizeNames ? TypeOfNamingSystemOrNormalized.NORMALIZED : TypeOfNamingSystemOrNormalized.SYSTEM, dataSource.getTypeOfDB().getTableMustPrefixFields());
            case SQLite -> workLInSQLBuilder = LInSQLBuilder.createForSQLite(normalizeNames ? TypeOfNamingSystemOrNormalized.NORMALIZED : TypeOfNamingSystemOrNormalized.SYSTEM);
            case MSSQL -> workLInSQLBuilder = LInSQLBuilder.createForMSSQL(normalizeNames ? TypeOfNamingSystemOrNormalized.NORMALIZED : TypeOfNamingSystemOrNormalized.SYSTEM);
            default -> throw new IllegalCallerException("dataSource.getTypeOfDB not valid");
        }
    }


    public void clear() { workLInSQLBuilder.clearSQLProperties(); }

    public void loadSQL() {
        workLInSQLBuilder.getSQLStatement();
    }

    public String getSQL() { return workLInSQLBuilder.getSQLStatement(); }

    public String getFromInsertOnlyTheValues() { return workLInSQLBuilder.getFromInsertOnlyTheValues(); }

    public void attachComments(@Nonnull String comments) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setComments(comments);
    }

    public void setApplyAutoAlias() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(true);
    }

    public void distinct() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setSelectDistinct();
    }

    public void limitOffset(int setLimit, int setOffset) {
        Preconditions.checkArgument(setLimit >= 0);
        Preconditions.checkArgument(setOffset >= 0);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setLimitOffset(MutablePair.of(BigInteger.valueOf(setLimit), BigInteger.valueOf(setOffset)));
    }
    public void offsetFetch(int setOffset, int setFetch) {
        Preconditions.checkArgument(setOffset >= 0);
        Preconditions.checkArgument(setFetch >= 0);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setOffsetFetch(MutablePair.of(BigInteger.valueOf(setOffset), BigInteger.valueOf(setFetch)));
    }

    public void insertInto(@Nonnull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void updateInto(@Nonnull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_UPDATE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void deleteFrom(@Nonnull DbTable setWorkWithTable) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_DELETE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, StringUtils.EMPTY);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setApplyAutoAlias(false);
    }

    public void from(@Nonnull DbTable setWorkWithTable, @Nullable String asAlias) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_SELECT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setWorkWithDbTableAsAlias(setWorkWithTable, Strings.nullToEmpty(asAlias));
    }

    public void select(@Nonnull Object... addSelectedFields) {
        Stream.of(addSelectedFields).filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addUserSelection(o, StringUtils.EMPTY));
    }
    public void selectAll() {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUserSelection(DbF.ALL, StringUtils.EMPTY);
    }
//    @Description("Add selected SQL Field/Constant/SQLFunction")
//    public J2SQL selectAsAlias(@Nonnull Object addSelectedField, @Nullable String asAlias) {
//        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUserSelection(addSelectedField, Strings.nullToEmpty(asAlias));
//        return this;
//    }

    public void addFilters(@Nonnull IWhere... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.AND, false, filters));
    }
    public void where(@Nonnull IWhere... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(null, false, filters));
    }
    public void andNot(@Nonnull IWhere... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.AND, true, filters));
    }
    public void or(@Nonnull IWhere... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.OR, false, filters));
    }
    public void orNot(@Nonnull IWhere... filters) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(GroupOfWheres.getGroupOfFilters(TypeOfLogicalOperator.OR, true, filters));
    }
    public void whereExists(@Nonnull LinSQL existQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(J2SQLShared.Filter.whereExists(existQuery));
    }
    public void whereExists(@Nonnull String existQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addWhereClause(J2SQLShared.Filter.whereExists(existQuery));
    }

    public void orderBy(@Nonnull SortOrder sortOrder, @Nonnull Object... addOrderBy) {
        Preconditions.checkArgument(sortOrder == SortOrder.ASCENDING || sortOrder == SortOrder.DESCENDING);
        Stream.of(addOrderBy).filter(Objects::nonNull).forEach(s -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(s, sortOrder));
    }
    @SafeVarargs
    public final void orderBy(@Nonnull MutablePair<Object, SortOrder>... addOrderBy) {
        Stream.of(addOrderBy).filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(o.left, o.right));
    }
    public void orderBy(@Nonnull List<MutablePair<Object, SortOrder>> addOrderBy) {
        addOrderBy.stream().filter(Objects::nonNull).forEach(o -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addOrdering(o.left, o.right));
    }

    public void groupBy(@Nonnull Object... setGroupBy) {
        List<Object> groupBy = Stream.of(setGroupBy).filter(Objects::nonNull).collect(Collectors.toList());
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setGroupBySelectionsHavingValues(MutablePair.of(groupBy, new ArrayList<>()));
    }
    public void having(@Nonnull IWhere... filters) {
        List<IWhere> whereList = Stream.of(filters).filter(Objects::nonNull).toList();
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getGroupBySelectionsHavingValues().getRight().addAll(whereList);
    }


    public void joinWith(@Nonnull TypeOfJoin typeOfJoin, @Nonnull LinSQL joinWith, @Nullable IWhere... joinOn) {
        List<IWhere> joinOnList = Lists.newArrayList();
        if (joinOn != null) Stream.of(joinOn).filter(Objects::nonNull).forEach(joinOnList::add);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addJoinWith(MutableTriple.of(typeOfJoin, joinWith, joinOnList));
    }
    public LinSQL on(@Nonnull IWhere... joinOn) {
        List<IWhere> joinOnList = Stream.of(joinOn).filter(Objects::nonNull).collect(Collectors.toList());
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().setRight(joinOnList);
        return this;
    }
    public void addJoinFilters(@Nonnull IWhere... addWhereFiltersToMainQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().addFilters(addWhereFiltersToMainQuery);
    }
    public void fromJoinSelectOnly(@Nonnull Object... addSelectFields) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().select(addSelectFields);
    }
    public void fromJoinSelectOnlyAsAlias(@Nonnull Object addSelectField, @Nullable String asAlias) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().getLastJoin().getMiddle().select(addSelectField, asAlias);
    }

    public void UNION(@Nonnull LinSQL unionWithLinSQL) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUnionWithQuery(unionWithLinSQL);
    }

    public void updateFieldSetValue(@Nonnull PairOfTableField updField, @Nonnull Object setValue) {
        Preconditions.checkArgument(updField.getDbf() != DbF.ALL);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_UPDATE);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().addUpdateFieldSetValue(updField, setValue);
    }

    @SafeVarargs
    public final void insertRows(@Nonnull List<Object>... setRows) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        Stream.of(setRows).filter(CollectionUtils::isNotEmpty).forEach(rowValues -> workLInSQLBuilder.getWorkLInSQLBuilderParams().addInsertRowsFieldValues(rowValues));
    }
    public void insertRowsFrom(@Nonnull String fromQuery) {
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setTypeOfSQL(J2SQLShared.TypeOfSQLStatement.SQL_INSERT);
        workLInSQLBuilder.getWorkLInSQLBuilderParams().setInsertRowsFromQuery(fromQuery);
    }
}
