package j2q.core;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import j2q.db.datasource.WorkWithDataSource;
import jdk.jfr.Description;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.*;

public final class J2SQL {
    private static final Set<String> OPERATION_SYMBOLS = ImmutableSet.of("+", "-", "*", "/");
    private final WorkWithDataSource forDataSource;
    private final LinSQL workLinSQL;
    private final boolean normalizeNames;

    @Description("Returns a new J2SQL with system names")
    public static J2SQL create(WorkWithDataSource forDataSource) { return create(forDataSource, false); }
    @Description("Returns a new J2SQL with System or Normalized names")
    public static J2SQL create(WorkWithDataSource forDataSource, boolean normalizeNames) { return new J2SQL(forDataSource, normalizeNames); }
    private J2SQL(WorkWithDataSource forDataSource, boolean normalizeNames) {
        this.forDataSource = forDataSource;
        this.normalizeNames = normalizeNames;
        this.workLinSQL = LinSQL.create(this.forDataSource, this.normalizeNames);
    }
    
    @Description("Select Operation")
    public static SQLFieldOperation operation(@Nonnull Object addSelectedField, @Nonnull String operation) {
        Preconditions.checkArgument(OPERATION_SYMBOLS.contains(StringUtils.left(StringUtils.trimToEmpty(operation), 1)));
        return new SQLFieldOperation(addSelectedField, operation);
    }

    @Description("Select SQL Fields/Constants/SQLFunctions as Alias")
    public static SQLFieldObject asAlias(@Nonnull Object addSelectedField, @Nonnull String asAlias) { return new SQLFieldObject(addSelectedField, asAlias, null); }

//    @Description("get MutablePair for Sorting")
//    public static MutablePair<Object, SortOrder> by(@Nonnull Object orderBy, @Nonnull SortOrder sortOrder) { return MutablePair.of(orderBy, sortOrder); }
//
//    @Description("get MutablePair for Sorting Ascending")
//    public static MutablePair<Object, SortOrder> asc(@Nonnull Object orderByAscending) { return MutablePair.of(orderByAscending, SortOrder.ASCENDING); }
//
//    @Description("get MutablePair for Sorting Descending")
//    public static MutablePair<Object, SortOrder> desc(@Nonnull Object orderByDescending) { return MutablePair.of(orderByDescending, SortOrder.DESCENDING); }

    @Description("Clear attributes")
    public void clear() {
        workLinSQL.clear();
    }
    @Description("Get J2SQL for SQL Statement")
    public J2SQL loadSQL() {
        workLinSQL.loadSQL();
        return this;
    }

    @Description("Get the SQL Statement")
    public String getSQL() { return workLinSQL.getSQL(); }

    @Description("Get the Inserted Values from Insert SQL Statement")
    public String getFromInsertOnlyTheValues() { return workLinSQL.getFromInsertOnlyTheValues(); }

    @Description("Attach Comments at SQL Statement")
    public J2SQL attachComments(@Nonnull String comments) {
        workLinSQL.attachComments(comments);
        return this;
    }

    @Description("Apply Auto Alias to Selected DB Fields")
    public J2SQL setApplyAutoAlias() {
        workLinSQL.setApplyAutoAlias();
        return this;
    }

    @Description("Set SQL Result to be Distinct")
    public J2SQL distinct() {
        workLinSQL.distinct();
        return this;
    }

    @Description("Set Limit/Offset Rows")
    public J2SQL limitOffset(int setLimit, int setOffset) {
        workLinSQL.limitOffset(setLimit, setOffset);
        return this;
    }
    @Description("Set Offset/Fetch Rows")
    public J2SQL offsetFetch(int setOffset, int setFetch) {
        workLinSQL.offsetFetch(setOffset, setFetch);
        return this;
    }

    @Description("Insert Into Table")
    public J2SQL insertInto(@Nonnull DbTable setWorkWithTable) {
        workLinSQL.insertInto(setWorkWithTable);
        return this;
    }
    @Description("Update Into Table")
    public J2SQL updateInto(@Nonnull DbTable setWorkWithTable) {
        workLinSQL.updateInto(setWorkWithTable);
        return this;
    }
    @Description("Delete From Table")
    public J2SQL deleteFrom(@Nonnull DbTable setWorkWithTable) {
        workLinSQL.deleteFrom(setWorkWithTable);
        return this;
    }

    @Description("Select From Table as Alias")
    public J2SQL from(@Nonnull Pair<DbTable, String> setWorkWithTableAsAlias) { return this.from(setWorkWithTableAsAlias.getLeft(), Strings.nullToEmpty(setWorkWithTableAsAlias.getRight())); }
    @Description("Select From Table")
    public J2SQL from(@Nonnull DbTable setWorkWithTable) { return this.from(setWorkWithTable, StringUtils.EMPTY); }
    @Description("Select From Table as Prefixed Alias")
    public J2SQL from(@Nonnull DbTable setWorkWithTable, @Nonnull J2SQLShared.PFX asAlias) { return this.from(setWorkWithTable, asAlias.name()); }
    @Description("Select From Table as Alias")
    public J2SQL from(@Nonnull DbTable setWorkWithTable, @Nullable String asAlias) {
        workLinSQL.from(setWorkWithTable, asAlias);
        return this;
    }

    @Description("Add selected SQL Fields/Constants/SQLFunctions")
    public J2SQL select(@Nonnull Object... addSelectedFields) {
        workLinSQL.select(addSelectedFields);
        return this;
    }
    @Description("Select All with asterisk *")
    public J2SQL selectAll() {
        workLinSQL.selectAll();
        return this;
    }

    @Description("add Filters")
    private J2SQL addFilters(@Nonnull IWhere... filters) {
        workLinSQL.addFilters(filters);
        return this;
    }
    @Description("add Filters")
    public J2SQL where(@Nonnull IWhere... filters) {
        workLinSQL.where(filters);
        return this;
    }
    @Description("add 'AND' Filter")
    public J2SQL and(@Nonnull IWhere... filters) {
        this.addFilters(filters);
        return this;
    }
    @Description("add 'AND NOT' Filter")
    public J2SQL andNot(@Nonnull IWhere... filters) {
        workLinSQL.andNot(filters);
        return this;
    }
    @Description("add 'OR' Filter")
    public J2SQL or(@Nonnull IWhere... filters) {
        workLinSQL.or(filters);
        return this;
    }
    @Description("add 'OR NOT' Filter")
    public J2SQL orNot(@Nonnull IWhere... filters) {
        workLinSQL.orNot(filters);
        return this;
    }
    @Description("add Exists filter query")
    public J2SQL whereExists(@Nonnull J2SQL existQuery) {
        workLinSQL.whereExists(existQuery.workLinSQL);
        return this;
    }
    @Description("add Exists filter query")
    public J2SQL whereExists(@Nonnull String existQuery) {
        workLinSQL.whereExists(existQuery);
        return this;
    }

    @Description("add Order By - All ASCENDING")
    public J2SQL orderBy(@Nonnull Object... addOrderBy) { return this.orderBy(SortOrder.ASCENDING, addOrderBy); }
    @Description("add Order By")
    public J2SQL orderBy(@Nonnull SortOrder sortOrderAll, @Nonnull Object... addOrderBy) {
        workLinSQL.orderBy(sortOrderAll, addOrderBy);
        return this;
    }
    @Description("add Order By")
    public J2SQL orderBy(@Nonnull MutablePair<Object, SortOrder>... addOrderBy) {
        workLinSQL.orderBy(addOrderBy);
        return this;
    }
    @Description("add Order By")
    public J2SQL orderBy(@Nonnull List<MutablePair<Object, SortOrder>> addOrderBy) {
        workLinSQL.orderBy(addOrderBy);
        return this;
    }

    @Description("set Group By")
    public J2SQL groupBy(@Nonnull Object... setGroupBy) {
        workLinSQL.groupBy(setGroupBy);
        return this;
    }
    @Description("add Having Values")
    public J2SQL having(@Nonnull IWhere... filters) {
        workLinSQL.having(filters);
        return this;
    }


    @Description("Join with Table/Join On fields/Join Where Filters")
    public J2SQL joinWith(@Nonnull LinSQL.TypeOfJoin typeOfJoin, @Nonnull J2SQL joinWith, @Nullable IWhere... joinOn) {
        workLinSQL.joinWith(typeOfJoin, joinWith.workLinSQL, joinOn);
        return this;
    }
    @Description("Left Join with") public J2SQL leftJoin(@Nonnull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Left Join with") public J2SQL leftJoin(@Nonnull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Left Join with") public J2SQL leftJoin(@Nonnull DbTable joinWith, @Nonnull String asAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Left Join with") public J2SQL leftJoin(@Nonnull DbTable joinWith, @Nonnull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.LEFT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Right Join with") public J2SQL rightJoin(@Nonnull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Right Join with") public J2SQL rightJoin(@Nonnull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Right Join with") public J2SQL rightJoin(@Nonnull DbTable joinWith, @Nonnull String asAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Right Join with") public J2SQL rightJoin(@Nonnull DbTable joinWith, @Nonnull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.RIGHT, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Inner Join with") public J2SQL innerJoin(@Nonnull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Inner Join with") public J2SQL innerJoin(@Nonnull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Inner Join with") public J2SQL innerJoin(@Nonnull DbTable joinWith, @Nonnull String asAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Inner Join with") public J2SQL innerJoin(@Nonnull DbTable joinWith, @Nonnull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.JOIN, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Full Join with") public J2SQL fullJoin(@Nonnull Pair<DbTable, String> joinWithAsAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWithAsAlias.getLeft(), joinWithAsAlias.getRight())); }
    @Description("Full Join with") public J2SQL fullJoin(@Nonnull DbTable joinWith) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith)); }
    @Description("Full Join with") public J2SQL fullJoin(@Nonnull DbTable joinWith, @Nonnull String asAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Full Join with") public J2SQL fullJoin(@Nonnull DbTable joinWith, @Nonnull J2SQLShared.PFX asAlias) { return joinWith(LinSQL.TypeOfJoin.FULL, J2SQL.create(this.forDataSource, this.normalizeNames).from(joinWith, asAlias)); }
    @Description("Join On") public J2SQL on(@Nonnull IWhere... joinOn) {
        workLinSQL.on(joinOn);
        return this;
    }
    @Description("Join Where Filters, will be added in main Query 'Where' Filters")
    public J2SQL addJoinFilters(@Nonnull IWhere... addWhereFiltersToMainQuery) {
        workLinSQL.addJoinFilters(addWhereFiltersToMainQuery);
        return this;
    }
    @Description("Add selected only SQL Fields/Constants/SQLFunctions to main Query")
    public J2SQL fromJoinSelectOnly(@Nonnull Object... addSelectFields) {
        workLinSQL.fromJoinSelectOnly(addSelectFields);
        return this;
    }
    @Description("Add selected only SQL Fields/Constants/SQLFunctions to main Query")
    public J2SQL fromJoinSelectOnlyAsAlias(@Nonnull Object addSelectField, @Nullable String asAlias) {
        workLinSQL.fromJoinSelectOnlyAsAlias(addSelectField, asAlias);
        return this;
    }

    @Description("Union with J2SQL")
    public J2SQL UNION(@Nonnull J2SQL unionWithLinSQL) {
        workLinSQL.UNION(unionWithLinSQL.workLinSQL);
        return this;
    }

    @Description("Update field, Set value")
    public J2SQL updateFieldSetValue(@Nonnull PairOfTableField updField, @Nonnull Object setValue) {
        workLinSQL.updateFieldSetValue(updField, setValue);
        return this;
    }

    @Description("Insert Row, set field values")
    public J2SQL insertRow(@Nonnull Object... setFieldValues) { return insertRows(Arrays.asList(setFieldValues)); }
    @SafeVarargs
    @Description("Insert Rows, set rows with field values")
    public final J2SQL insertRows(@Nonnull List<Object>... setRows) {
        workLinSQL.insertRows(setRows);
        return this;
    }
    @Description("Insert Rows, from J2SQL")
    public J2SQL insertRowsFrom(@Nonnull J2SQL fromLinSQL) { return insertRowsFrom(fromLinSQL.getSQL()); }
    @Description("Insert Rows, from Query")
    public J2SQL insertRowsFrom(@Nonnull String fromQuery) {
        workLinSQL.insertRowsFrom(fromQuery);
        return this;
    }
}
