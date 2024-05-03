package j2q.core;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;

final class InSubSelectWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereInSubSelect; }

    private final String inSubSelect;

    public InSubSelectWhere(@Nonnull Object whereObject, @Nonnull String inSubSelect) {
        super(whereObject);
        this.inSubSelect = inSubSelect;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String returnValue = super.whereObjectForSQL(forSQLRetrieverForDB) + "(" + Strings.nullToEmpty(this.inSubSelect).trim() + ")" +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
        return returnValue;
    }
}
