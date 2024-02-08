package j2q.core.creator.resolvers.functions;

import j2q.commons.CommonMethods;
import j2q.core.linsql.LinSQLCommons;
import j2q.core.retrievers.IDeploySQLFunctions;
import j2q.core.retrievers.SQLRetrieverForDBs;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SQLFunctionAggregatesWithPossibleALL extends SQLFunction {
    private final IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction;
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return this.typeOfSQLFunction; }

    public SQLFunctionAggregatesWithPossibleALL(IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction, @Nonnull Object... args) {
        this.typeOfSQLFunction = typeOfSQLFunction;
        super.init(null,null, args);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }
    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        String funcParam = StringUtils.EMPTY;
        if (super.getParams().size() == 1) {
            funcParam = LinSQLCommons.ASTERISK;
        } else {
            if (super.getParam() == Boolean.valueOf(true)) {
                funcParam = "DISTINCT ";
            }
        }

        String result = CommonMethods.stringsConcat(false, this.typeOfSQLFunction.name(), "(", funcParam,
                funcParam.equals(LinSQLCommons.ASTERISK) ? StringUtils.EMPTY : super.getLastParamSelectedFieldForSQL(forSQLRetrieverForDB, null),
                ")");
        return getFinalValueAsAlias(result, getAsAlias());
    }
    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
