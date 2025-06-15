package j2q.core;

import com.google.common.base.Strings;
import jakarta.annotation.Nonnull;

final class ExistsWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereExist; }

    private final String inSubSelect;

    ExistsWhere(@Nonnull String inSubSelect) {
        super(null);
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
