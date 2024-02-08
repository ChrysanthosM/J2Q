package j2q.core.creator.resolvers.filters;

import j2q.core.creator.LInSQLBuilderShared;
import j2q.core.retrievers.SQLRetrieverForDBs;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;

public final class BetweenValuesWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereBetween; }

    private final Pair<Object, Object> betweenValues;

    public BetweenValuesWhere(@Nonnull Object whereObject, @Nonnull Pair<Object, Object> betweenValues) {
        super(whereObject);
        this.betweenValues = betweenValues;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB));
        returnValue.append(LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getLeft(), super.getDataTypeForSQL()).getResolveObjectForSQL(forSQLRetrieverForDB));
        returnValue.append(" AND ");
        returnValue.append(LInSQLBuilderShared.getSqlUserSelection(this.betweenValues.getRight(), super.getDataTypeForSQL()).getResolveObjectForSQL(forSQLRetrieverForDB));
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
