package j2q.core.creator;

import j2q.core.j2sql.J2SQLShared;
import j2q.core.creator.resolvers.fields.*;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.db.definition.GlobalFieldModelDefinition;
import j2q.core.creator.resolvers.functions.SQLFunction;
import j2q.core.creator.resolvers.SqlUserSelection;

public final class LInSQLBuilderShared {
    public static SqlUserSelection getSqlUserSelection(Object selectionObject) {
        return getSqlUserSelectionMain(selectionObject, null, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, String asAlias) {
        return getSqlUserSelectionMain(selectionObject, asAlias, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        return getSqlUserSelectionMain(selectionObject, null, dataTypeForSQL);
    }
    private static SqlUserSelection getSqlUserSelectionMain(Object selectionObject, String asAlias, GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        if (selectionObject instanceof SQLFieldOperation) return (SqlUserSelection) selectionObject;
        if (selectionObject instanceof SQLFieldObject) return (SqlUserSelection) selectionObject;
        if (selectionObject instanceof SQLFieldFromTable) return (SqlUserSelection) selectionObject;
        if (selectionObject instanceof SQLFieldFromPairOfTableField) return (SQLFieldFromPairOfTableField) selectionObject;

        if (selectionObject instanceof DbF) {
            return new SQLFieldFromTable((DbF) selectionObject, asAlias);
        }
        if (selectionObject instanceof PairOfTableField) {
            return new SQLFieldFromPairOfTableField((PairOfTableField) selectionObject, asAlias);
        }

        if (selectionObject instanceof J2SQLShared.SQLFunctionObject stringsFunction) {
            ((SQLFunction) stringsFunction.getSqlFunction()).setAsAlias(asAlias);
            return ((SQLFunction) stringsFunction.getSqlFunction());
        }

        return new SQLFieldFromConstant(selectionObject, asAlias, dataTypeForSQL);
    }
}
