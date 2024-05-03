package j2q.core;

import j2q.commons.CommonMethods;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


final class SQLFunction2Params extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    public SQLFunction2Params(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null, null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String result = CommonMethods.stringsConcat(false, this.typeOfSQLFunction.name(), "(",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(0), ", ",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(1),
                ")");
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
