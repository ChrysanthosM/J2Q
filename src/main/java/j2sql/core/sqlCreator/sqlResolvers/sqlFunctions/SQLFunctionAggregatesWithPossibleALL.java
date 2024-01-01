package j2sql.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2sql.commons.CommonMethods;
import j2sql.core.linSQL.LinSQLCommons;
import j2sql.core.sqlRetriever.IDeploySQLFunctions;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
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
