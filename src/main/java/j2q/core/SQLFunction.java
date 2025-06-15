package j2q.core;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract sealed class SQLFunction extends SqlUserSelection
        permits SQLFunction1Param, SQLFunction2Params, SQLFunction3Params,
        SQLFunctionAggregates, SQLFunctionAggregatesWithPossibleALL,
        SQLFunctionCASE, SQLFunctionCONCAT, SQLFunctionTRANSLATE {
    static String getNonSupportedMsg() { return "Non Supported Method"; }

    @Override public Type getTypeOfSelection() { return this.getClass(); }
    abstract IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction();

    private List<Object> params = Lists.newArrayList();
    protected List<Object> getParams() { return this.params; }
    protected Object getParam() { return this.params.getFirst(); }
    protected void setParams(List<Object> params) { this.params = params; }
    protected void addParam(Object param) { this.params.add(param); }
    private List<String> paramsSelectedFieldForSQL = null;
    protected List<String> getParamsSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        if (CollectionUtils.isNotEmpty(this.paramsSelectedFieldForSQL)) return this.paramsSelectedFieldForSQL;
        this.paramsSelectedFieldForSQL = Lists.newArrayList();
        this.params.stream().filter(Objects::nonNull)
                .forEach(arg -> this.paramsSelectedFieldForSQL.add(LInSQLBuilderShared.getSqlUserSelection(arg, inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB)));
        return this.paramsSelectedFieldForSQL;
    }
    protected String getFirstParamSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        return LInSQLBuilderShared.getSqlUserSelection(this.params.getFirst(), inQuotesRequirement).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
    protected String getLastParamSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Boolean inQuotesRequirement) {
        SqlUserSelection mainParam = LInSQLBuilderShared.getSqlUserSelection(this.params.getLast(), inQuotesRequirement);
        mainParam.setIgnoreTableAsAlias();
        return mainParam.getResolveObjectForSQL(forSQLRetrieverForDB);
    }

    abstract String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB);
    abstract String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args);

    protected String resolverAllParamsInParenthesis(SQLRetrieverForDBs forSQLRetrieverForDB, IDeploySQLFunctions.TypeOfSQLFunction typeOfSQLFunction) {
        List<?> workParams = getParamsSelectedFieldForSQL(forSQLRetrieverForDB, null);
        String result = workParams.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", typeOfSQLFunction.name() + "(", ")"));
        return getFinalValueAsAlias(result, getAsAlias());
    }

    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        assert args != null;
        Stream.of(args).filter(Objects::nonNull).forEach(arg -> this.params.add(arg));
        super.setAsAlias(asAlias);
        super.setHasPrefix(setPrefix);
    }

    protected static String getFinalValueAsAlias(String value, String asAlias) {
        return LinSQLCommons.applyAsAlias(value, asAlias, true, false);
    }

}

