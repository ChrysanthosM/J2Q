package j2q.core;

import j2q.commons.CommonMethods;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class SQLFunctionTRANSLATE extends SQLFunction {
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return IDeploySQLFunctions.TypeOfSQLFunction.TRANSLATE; }

    public SQLFunctionTRANSLATE(@Nonnull Object... args) { super.init(null, null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String result = CommonMethods.stringsConcat(false,
                "TRANSLATE(", super.getFirstParamSelectedFieldForSQL(forSQLRetrieverForDB, null), ", ",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(1), ", ",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(2));
        if (super.getParams().size() == 4)
            result = result.concat(", ".concat(super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(3)));
        result = result.concat(")");

        return getFinalValueAsAlias(result, super.getAsAlias());
    }

    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
