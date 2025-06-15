package j2q.core;

import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.tuple.Pair;

final class BetweenValuesWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereBetween; }

    private final Pair<Object, Object> betweenValues;

    BetweenValuesWhere(@Nonnull Object whereObject, @Nonnull Pair<Object, Object> betweenValues) {
        super(whereObject);
        this.betweenValues = betweenValues;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String returnValue = super.whereObjectForSQL(forSQLRetrieverForDB) + LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getLeft(), super.getInQuotesRequirement()).getResolveObjectForSQL(forSQLRetrieverForDB) +
                " AND " +
                LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getRight(), super.getInQuotesRequirement()).getResolveObjectForSQL(forSQLRetrieverForDB) +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
        return returnValue;
    }
}
