package j2q.core;

import j2q.db.definition.GlobalFieldModelDefinition;
import lombok.Setter;

sealed abstract class WhenThen implements IWhen, IResolveObjectForSQL
        permits WhenThenSearched, WhenThenSimple {
    private final Object thenExpression;
    @Setter private GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL;

    protected WhenThen(Object thenExpression) {
        this.thenExpression = thenExpression;
    }

    protected String getThen(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return " THEN " + LInSQLBuilderShared.getSqlUserSelection(this.thenExpression, this.dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
}
