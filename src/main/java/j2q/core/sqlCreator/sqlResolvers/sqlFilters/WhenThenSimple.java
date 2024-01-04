package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.sqlCreator.LInSQLBuilderShared;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class WhenThenSimple extends WhenThen {
    private final Object whenCondition;

    public WhenThenSimple(@Nonnull Object whenCondition, @Nullable Object thenExpression) {
        super(thenExpression);
        this.whenCondition = whenCondition;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder("WHEN ");
        returnValue.append(LInSQLBuilderShared.getSqlUserSelection(this.whenCondition).getResolveObjectForSQL(forSQLRetrieverForDB));
        returnValue.append(super.getThen(forSQLRetrieverForDB));
        return returnValue.toString();
    }
}
