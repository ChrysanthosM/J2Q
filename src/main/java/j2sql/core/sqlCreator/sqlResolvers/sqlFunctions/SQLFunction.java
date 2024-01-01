package j2sql.core.sqlCreator.sqlResolvers.sqlFunctions;

import j2sql.core.linSQL.LinSQLCommons;
import j2sql.core.sqlCreator.LInSQLBuilderShared;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
import j2sql.db.model.GlobalFieldModelDefinition;
import com.google.common.collect.Lists;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.IDeploySQLFunctions;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class SQLFunction extends SqlUserSelection {
    static String getNonSupportedMsg() { return "Non Supported Method"; }

    @Override public Type getTypeOfSelection() { return this.getClass(); }
    public abstract IDeploySQLFunctions.TypeOfSQLFunction getTypeOfSQLFunction();

    private List<Object> params = Lists.newArrayList();
    protected List<Object> getParams() { return this.params; }
    protected Object getParam() { return this.params.get(0); }
    protected void setParams(List<Object> params) { this.params = params; }
    protected void addParam(Object param) { this.params.add(param); }
    private List<String> paramsSelectedFieldForSQL = null;
    protected List<String> getParamsSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        if (CollectionUtils.isNotEmpty(this.paramsSelectedFieldForSQL)) return this.paramsSelectedFieldForSQL;
        this.paramsSelectedFieldForSQL = Lists.newArrayList();
        this.params.stream().filter(Objects::nonNull)
                .forEach(arg -> this.paramsSelectedFieldForSQL.add(LInSQLBuilderShared.getSqlUserSelection(arg, dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB)));
        return this.paramsSelectedFieldForSQL;
    }
    protected String getFirstParamSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        return LInSQLBuilderShared.getSqlUserSelection(this.params.get(0), dataTypeForSQL).getResolveObjectForSQL(forSQLRetrieverForDB);
    }
    protected String getLastParamSelectedFieldForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        SqlUserSelection mainParam = LInSQLBuilderShared.getSqlUserSelection(this.params.get(this.params.size() - 1), dataTypeForSQL);
        mainParam.setIgnoreTableAsAlias();
        return mainParam.getResolveObjectForSQL(forSQLRetrieverForDB);
    }

    public abstract String defaultResolver(SQLRetrieverForDBs forSQLRetrieverForDB);
    public abstract String alternateResolver(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable Object... args);


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

