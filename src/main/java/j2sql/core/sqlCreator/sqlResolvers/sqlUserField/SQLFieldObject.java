package j2sql.core.sqlCreator.sqlResolvers.sqlUserField;

import j2sql.core.sqlCreator.LInSQLBuilderShared;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public final class SQLFieldObject extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private Object object;

    public SQLFieldObject(@Nullable Object object, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, object);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        assert args != null;
        this.object = args[0];
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return LInSQLBuilderShared.getSqlUserSelection(this.object, super.getAsAlias()).getResolveObjectForSQL(forSQLRetrieverForDB) ;
    }
}
