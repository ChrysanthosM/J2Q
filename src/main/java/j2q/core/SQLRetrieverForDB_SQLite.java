package j2q.core;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Map;

final class SQLRetrieverForDB_SQLite extends SQLRetrieverForDBs {
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
                    .put(LinSQL.TypeOfJoin.FULL, "FULL OUTER JOIN")
                    .put(LinSQL.TypeOfJoin.JOIN , "INNER JOIN")
                    .put(LinSQL.TypeOfJoin.LEFT , "LEFT JOIN")
                    .put(LinSQL.TypeOfJoin.RIGHT , StringUtils.EMPTY)
                    .build();

    SQLRetrieverForDB_SQLite(LinSQL.TypeOfNamingSystemOrNormalized namingSystemOrNormalized) {
        super(namingSystemOrNormalized, StringUtils.EMPTY);
    }

    @Override public String getNullWord() { return "NULL"; }
    @Override public Map<LinSQL.TypeOfComparison, String> getComparisonType() { return comparisonType; }
    @Override public Map<SortOrder, String> getOrderByType() { return orderByType; }
    @Override public Map<LinSQL.TypeOfJoin, String> getJoinType() { return joinType; }

    @Override public String getLimitOffsetForSQL(Pair<BigInteger, BigInteger> setLimitOffset) {
        String limitOffset = StringUtils.EMPTY;
        if (setLimitOffset != null) {
            if (setLimitOffset.getLeft() != null) {limitOffset = "LIMIT ".concat(setLimitOffset.getLeft().toString());}
            if (setLimitOffset.getRight() != null) {limitOffset = limitOffset.concat(" OFFSET ".concat(setLimitOffset.getRight().toString()));}
        }
        return limitOffset;
    }
    @Override public String getOffsetFetchForSQL(Pair<BigInteger, BigInteger> setOffsetFetch) { return StringUtils.EMPTY; }

    @Override
    public String getSQLStatementForSelect() {
        InitializationStatus initSts = initSQLStatementForSelect(this);
        if (initSts.getSts() == InitializationStatus.ReturnSts.ERROR_EXISTS) throw new RuntimeException(initSts.getExc());

        String returnStmt = super.getWorkBuildSQLSelectFields().getStringForSQL() +
                super.getWorkBuildSQLJoinWith().getSelectedJoinFieldsForSQL() + StringUtils.SPACE +
                super.getSQLStatementFromWhereGroupByOrderBy() + StringUtils.SPACE +
                getLimitOffsetForSQL(super.getWorkLInSQLBuilderParams().getLimitOffset()) + StringUtils.SPACE +
                getOffsetFetchForSQL(super.getWorkLInSQLBuilderParams().getOffsetFetch());
        return returnStmt;
    }

    @Override
    public String resolveSQLStringsFunction(SQLFunction sqlFunction) {
        switch (sqlFunction.getTypeOfSQLFunction()) {
            case CONCAT -> { return sqlFunction.alternateResolver(this, " || "); }
            default -> { return sqlFunction.defaultResolver(this); }
        }
    }
}
