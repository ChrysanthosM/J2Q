package j2q.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2q.commons.CommonMethods;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static j2q.core.sqlRetriever.IDeploySQLFunctions.TypeOfSQLFunction;

public final class SQLFunction2Params extends SQLFunction {
    private final TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    public SQLFunction2Params(TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
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
