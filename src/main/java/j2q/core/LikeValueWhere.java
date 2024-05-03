package j2q.core;

import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class LikeValueWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereLike; }

    private final String compareValue;
    private final String escapeLeft;
    private final String escapeRight;

    public LikeValueWhere(@Nonnull Object whereObject, @Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) {
        super(whereObject);
        this.compareValue = compareValue;
        this.escapeLeft = escapeLeft;
        this.escapeRight = escapeRight;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String returnValue = super.whereObjectForSQL(forSQLRetrieverForDB) + LinSQLCommons.QUOTE + Strings.nullToEmpty(escapeLeft) +
                Strings.nullToEmpty(this.compareValue) +
                Strings.nullToEmpty(escapeRight) + LinSQLCommons.QUOTE +
                super.resolveAttachedFilters(forSQLRetrieverForDB) +
                super.resolveParenthesisRight();
        return returnValue;
    }
}
