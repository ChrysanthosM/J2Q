package j2q.core;

import j2q.commons.CommonMethods;
import j2q.db.definition.GlobalFieldModelDefinition;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

final class SQLFieldOperation extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private Object object;
    private String operation;

    public SQLFieldOperation(Object object, String operation) {
        super();
        init(null, operation, object);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String expression, @Nullable Object... args) {
        assert args != null;
        this.object = args[0];
        this.operation = expression;
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        SqlUserSelection sqlUserSelection = LInSQLBuilderShared.getSqlUserSelection(this.object, GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC);
        String keepAlias = sqlUserSelection.getAsAlias();
        sqlUserSelection.setAsAlias(null);
        sqlUserSelection.setIgnoreTableAsAlias();
        return CommonMethods.stringsConcat(false,
                sqlUserSelection.getResolveObjectForSQL(forSQLRetrieverForDB),
                StringUtils.SPACE, LinSQLCommons.asAliasMain(keepAlias),
                StringUtils.SPACE, this.operation);
    }
}
