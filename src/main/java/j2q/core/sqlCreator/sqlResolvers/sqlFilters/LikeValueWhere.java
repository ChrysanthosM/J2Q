package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.linSQL.LinSQLCommons;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class LikeValueWhere extends AbstractWhere {
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
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB));
        returnValue.append(LinSQLCommons.QUOTE).append(Strings.nullToEmpty(escapeLeft));
        returnValue.append(Strings.nullToEmpty(this.compareValue));
        returnValue.append(Strings.nullToEmpty(escapeRight)).append(LinSQLCommons.QUOTE);
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
