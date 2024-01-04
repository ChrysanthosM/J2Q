package j2q.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2q.commons.CommonMethods;
import j2q.core.sqlRetriever.IDeploySQLFunctions;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SQLFunction3Params extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    public SQLFunction3Params(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String result = CommonMethods.stringsConcat(false, this.typeOfSQLFunction.name(), "(",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(0), ", ",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(1), ", ",
                super.getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null).get(2), ")");
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
