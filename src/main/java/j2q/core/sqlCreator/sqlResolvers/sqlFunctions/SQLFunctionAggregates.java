package j2q.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2q.commons.CommonMethods;
import j2q.core.sqlRetriever.IDeploySQLFunctions;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SQLFunctionAggregates extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    public SQLFunctionAggregates(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String funcParam = StringUtils.EMPTY;
        if (super.getParams().size() > 1) {
            if (super.getParam() == Boolean.valueOf(true)) {
                funcParam = "DISTINCT ";
            }
        }
        String result = CommonMethods.stringsConcat(false, this.typeOfSQLFunction.name(), "(", funcParam.concat(super.getLastParamSelectedFieldForSQL(forSQLRetrieverForDB, null)), ")");
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
