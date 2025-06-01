package j2q.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class ValueWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereValue; }

    private final LinSQL.TypeOfComparison typeOfComparison;
    private final Object compareValue;

    ValueWhere(@Nonnull Object whereObject, @Nullable LinSQL.TypeOfComparison typeOfComparison, @Nullable Object compareValue) {
        super(whereObject);
        this.typeOfComparison = typeOfComparison;
        this.compareValue = compareValue;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB, (this.typeOfComparison == null) ? null : forSQLRetrieverForDB.getComparisonType().get(this.typeOfComparison)));
        if (this.compareValue != null) {
            SqlUserSelection valueWhere = LInSQLBuilderShared.getSqlUserSelection(this.compareValue, super.getInQuotesRequirement());
            valueWhere.setIgnoreTableAsAlias();
            returnValue.append(valueWhere.getResolveObjectForSQL(forSQLRetrieverForDB));
        }
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
