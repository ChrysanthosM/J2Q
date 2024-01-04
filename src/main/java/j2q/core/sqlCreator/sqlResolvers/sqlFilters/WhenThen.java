package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.sqlCreator.LInSQLBuilderShared;
import j2q.core.sqlCreator.sqlResolvers.IResolveObjectForSQL;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import j2q.db.model.GlobalFieldModelDefinition;
import lombok.Setter;

public abstract class WhenThen implements IWhen, IResolveObjectForSQL {
    private final Object thenExpression;
    @Setter private GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL;

    protected WhenThen(Object thenExpression) {
        this.thenExpression = thenExpression;
    }

    protected String getThen(SQLRetrieverForDBs forSQLRetrieverForDB) {
        StringBuilder returnValue = new StringBuilder(" THEN ");
        returnValue.append(LInSQLBuilderShared.getSqlUserSelection(this.thenExpression, this.dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB));
        return returnValue.toString();
    }
}
