package j2q.core.sqlRetriever;

import j2q.core.linSQL.LinSQL;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.swing.*;
import java.math.BigInteger;
import java.util.Map;

final class SQLRetrieverForDB_DB2 extends SQLRetrieverForDBs {
    private static final Map<LinSQL.TypeOfComparison, String> comparisonType =
            new ImmutableMap.Builder<LinSQL.TypeOfComparison, String>()
                    .put(LinSQL.TypeOfComparison.IS_NULL, "IS NULL")
                    .put(LinSQL.TypeOfComparison.LT , "<")
                    .put(LinSQL.TypeOfComparison.LE, "<=")
                    .put(LinSQL.TypeOfComparison.EQ, "=")
                    .put(LinSQL.TypeOfComparison.GE, ">=")
                    .put(LinSQL.TypeOfComparison.GT, ">")
                    .put(LinSQL.TypeOfComparison.NE, "<>")
                    .build();
    private static final Map<SortOrder, String> orderByType =
            new ImmutableMap.Builder<SortOrder, String>()
                    .put(SortOrder.ASCENDING, "ASC")
                    .put(SortOrder.DESCENDING , "DESC")
                    .build();
    private static final Map<LinSQL.TypeOfJoin, String> joinType =
            new ImmutableMap.Builder<LinSQL.TypeOfJoin, String>()
                    .put(LinSQL.TypeOfJoin.FULL, "FULL JOIN")
                    .put(LinSQL.TypeOfJoin.JOIN , "INNER JOIN")
                    .put(LinSQL.TypeOfJoin.LEFT , "LEFT JOIN")
                    .put(LinSQL.TypeOfJoin.RIGHT , "RIGHT JOIN")
                    .build();

    SQLRetrieverForDB_DB2(LinSQL.TypeOfNamingSystemOrNormalized namingSystemOrNormalized, @Nullable String dbPrefixForTable, boolean tableMustPrefixFields) {
        super(namingSystemOrNormalized, Strings.nullToEmpty(dbPrefixForTable), tableMustPrefixFields);
    }

    @Override public String getNullWord() { return "NULL"; }
    @Override public Map<LinSQL.TypeOfComparison, String> getComparisonType() { return comparisonType; }
    @Override public Map<SortOrder, String> getOrderByType() { return orderByType; }
    @Override public Map<LinSQL.TypeOfJoin, String> getJoinType() { return joinType; }

    @Override public String getLimitOffsetForSQL(Pair<BigInteger, BigInteger> setLimitOffset) {
        String limitOffset = StringUtils.EMPTY;
        if (setLimitOffset != null) {
            if (setLimitOffset.getLeft() != null) { limitOffset = "LIMIT ".concat(setLimitOffset.getLeft().toString()); }
            if (setLimitOffset.getRight() != null) { limitOffset = limitOffset.concat(" OFFSET ".concat(setLimitOffset.getRight().toString())); }
        }
        return limitOffset;
    }
    @Override public String getOffsetFetchForSQL(Pair<BigInteger, BigInteger> setOffsetFetch) {
        String offsetFetch = StringUtils.EMPTY;
        if (setOffsetFetch != null) {
            if (setOffsetFetch.getLeft() != null) { offsetFetch = "OFFSET ".concat(setOffsetFetch.getLeft().toString()).concat(" ROWS "); }
            if (setOffsetFetch.getRight() != null) { offsetFetch = offsetFetch.concat("FETCH FIRST ".concat(setOffsetFetch.getRight().toString()).concat(" ROWS ONLY ")); }
        }
        return offsetFetch;
    }


    @Override
    public String getSQLStatementForSelect() {
        InitializationStatus initSts = initSQLStatementForSelect(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        StringBuilder returnStmt = new StringBuilder();
        returnStmt.append(super.getWorkBuildSQLSelectFields().getStringForSQL().trim())
                .append(StringUtils.defaultString(super.getWorkBuildSQLJoinWith().getSelectedJoinFieldsForSQL(), StringUtils.SPACE));
        returnStmt.append(super.getSQLStatementFromWhereGroupByOrderBy());
        returnStmt.append(getLimitOffsetForSQL(super.getWorkLInSQLBuilderParams().getLimitOffset()));
        returnStmt.append(getOffsetFetchForSQL(super.getWorkLInSQLBuilderParams().getOffsetFetch()));
        return returnStmt.toString();
    }



}
