package j2q.core;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;

final class SQLFunctionTRANSLATE extends SQLFunction {
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return IDeploySQLFunctions.TypeOfSQLFunction.TRANSLATE; }

    SQLFunctionTRANSLATE(@Nonnull Object... args) { super.init(null, null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<?> params = super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null);
        String result = "TRANSLATE(" + super.getFirstParamSelectedFieldForSQL(forSQLRetrieverForDB, null) + ", " +
                params.get(1) + ", " + params.get(2);
        if (super.getParams().size() == 4)
            result = result.concat(", ".concat(super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(3)));
        result = result.concat(")");

        return getFinalValueAsAlias(result, super.getAsAlias());
    }

    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
