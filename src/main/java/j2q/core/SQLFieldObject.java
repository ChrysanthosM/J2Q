package j2q.core;

import jakarta.annotation.Nullable;
import java.lang.reflect.Type;

final class SQLFieldObject extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private Object object;

    SQLFieldObject(@Nullable Object object, @Nullable String asAlias, @Nullable String setPrefix) {
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
