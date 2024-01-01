package j2sql.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2sql.commons.CommonMethods;
import j2sql.db.model.GlobalFieldModelDefinition;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2sql.core.sqlRetriever.IDeploySQLFunctions;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
import j2sql.core.sqlCreator.LInSQLBuilderShared;
import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.WhenThen;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class SQLFunctionCASE extends SQLFunction {
    @Override
    public IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction() { return IDeploySQLFunctions.TypeOfSQLFunction.CASE; }

    public SQLFunctionCASE(@Nonnull Object... args) { super.init(null,null, args); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return forSQLRetrieverForDB.resolveSQLStringsFunction(this); }

    @Override
    public String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB) {
        GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL = (GlobalFieldModelDefinition.DataTypeForSQL) super.getParams().get(0);

        Optional<Object> caseOpt = (Optional<Object>) super.getParams().get(1);
        String caseExpression = caseOpt.map(o -> LInSQLBuilderShared.getSqlUserSelection(o, dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB)).orElse(null);

        String elseExpression = LInSQLBuilderShared.getSqlUserSelection(super.getParams().get(2), dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB);

        List<Object> whenList = super.getParams().subList(3, super.getParams().size()) ;
        whenList.stream().filter(Objects::nonNull).forEach(f -> ((WhenThen) f).setDataTypeForSQL(dataTypeForSQL));
        List<String> searchListResolved = Lists.newArrayList();
        whenList.stream().filter(Objects::nonNull).forEach(when -> searchListResolved.add(((WhenThen) when).getResolveObjectForSQL(forSQLRetrieverForDB)));
        String whenExpression = Joiner.on(StringUtils.SPACE).join(searchListResolved);

        return CommonMethods.stringsConcat(false, "CASE ", StringUtils.defaultString(caseExpression).concat(StringUtils.SPACE), whenExpression, " ELSE ", elseExpression, " END");
    }

    @Override
    public String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args) { throw new IllegalCallerException(getNonSupportedMsg()); }
}
