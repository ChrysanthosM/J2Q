package j2sql.core.sqlCreator.sqlResolvers.sqlFilters;

import j2sql.core.linSQL.LinSQL;
import j2sql.core.sqlCreator.LInSQLBuilderShared;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ValueWhere extends AbstractWhere {
    @Override TypeOfWhere getTypeOfWhere() { return TypeOfWhere.WhereValue; }

    private final LinSQL.TypeOfComparison typeOfComparison;
    private final Object compareValue;

    public ValueWhere(@Nonnull Object whereObject, @Nullable LinSQL.TypeOfComparison typeOfComparison, @Nullable Object compareValue) {
        super(whereObject);
        this.typeOfComparison = typeOfComparison;
        this.compareValue = compareValue;
    }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(super.whereObjectForSQL(forSQLRetrieverForDB, (this.typeOfComparison == null) ? null : forSQLRetrieverForDB.getComparisonType().get(this.typeOfComparison)));
        if (this.compareValue != null) {
            SqlUserSelection valueWhere = LInSQLBuilderShared.getSqlUserSelection(this.compareValue, super.getDataTypeForSQL());
            valueWhere.setIgnoreTableAsAlias();
            returnValue.append(valueWhere.getResolveObjectForSQL(forSQLRetrieverForDB));
        }
        returnValue.append(super.resolveAttachedFilters(forSQLRetrieverForDB));
        return returnValue.append(super.resolveParenthesisRight()).toString();
    }
}
