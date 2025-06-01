package j2q.core;

import j2q.setup.definition.design.schema.enums.DbF;

final class LInSQLBuilderShared {
    public static SqlUserSelection getSqlUserSelection(Object selectionObject) {
        return getSqlUserSelectionMain(selectionObject, null, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, String asAlias) {
        return getSqlUserSelectionMain(selectionObject, asAlias, null);
    }
    public static SqlUserSelection getSqlUserSelection(Object selectionObject, Boolean inQuotesRequirement) {
        return getSqlUserSelectionMain(selectionObject, null, inQuotesRequirement);
    }
    private static SqlUserSelection getSqlUserSelectionMain(Object selectionObject, String asAlias, Boolean inQuotesRequirement) {
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

        return new SQLFieldFromConstant(selectionObject, asAlias, inQuotesRequirement);
    }
}
