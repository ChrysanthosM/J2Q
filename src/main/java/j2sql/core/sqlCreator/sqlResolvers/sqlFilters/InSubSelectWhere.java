package j2sql.core.sqlCreator.sqlResolvers.sqlFilters;

import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
import com.google.common.base.Strings;

import javax.annotation.Nonnull;

public final class InSubSelectWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereInSubSelect; }

    private final String inSubSelect;

    public InSubSelectWhere(@Nonnull Object whereObject, @Nonnull String inSubSelect) {
        super(whereObject);
        this.inSubSelect = inSubSelect;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB));
        returnValue.append("(").append(Strings.nullToEmpty(this.inSubSelect).trim()).append(")") ;
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
