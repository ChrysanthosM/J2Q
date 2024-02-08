package j2q.core.creator.resolvers.filters;

import j2q.core.creator.sqlbuilder.BuildSQLWhereFilters;
import j2q.core.retrievers.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class WhenThenSearched extends WhenThen {
    private final IWhere searchCondition;

    public WhenThenSearched(@Nonnull IWhere searchCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.searchCondition = searchCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder("WHEN ");
        returnValue.append(BuildSQLWhereFilters.getResolveFilterForSQL(forSQLRetrieverForDB, this.searchCondition, true));
        returnValue.append(super.getThen(forSQLRetrieverForDB));
        return returnValue.toString();
    }
}
