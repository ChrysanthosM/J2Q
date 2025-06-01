package j2q.core;

import lombok.Setter;

abstract sealed class WhenThen implements IWhen, IResolveObjectForSQL
        permits WhenThenSearched, WhenThenSimple {

    private final Object thenExpression;
    @Setter private Boolean inQuotesRequirement;

    protected WhenThen(Object thenExpression) {
        this.thenExpression = thenExpression;
    }

    protected String getThen(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return " THEN " + LInSQLBuilderShared.getSqlUserSelection(this.thenExpression, this.inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
}
