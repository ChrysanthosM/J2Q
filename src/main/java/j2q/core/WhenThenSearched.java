package j2q.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class WhenThenSearched extends WhenThen {
    private final IWhere searchCondition;

    WhenThenSearched(@Nonnull IWhere searchCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.searchCondition = searchCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String returnValue = "WHEN " + BuildSQLWhereFilters.getResolveFilterForSQL(forSQLRetrieverForDB, this.searchCondition, true) +
                super.getThen(forSQLRetrieverForDB);
        return returnValue;
    }
}
