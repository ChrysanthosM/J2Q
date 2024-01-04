package j2q.core.sqlCreator.sqlResolvers;

import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import lombok.Getter;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public abstract class SqlUserSelection implements ISqlUserSelection {
    @Getter private boolean ignoreTableAsAlias = false;
    public void setIgnoreTableAsAlias() { this.ignoreTableAsAlias = true; }

    @Override public abstract Type getTypeOfSelection();
    @Override public abstract String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB);
    @Override public abstract void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args);

    private String hasPrefix = null;
    @Override public String getHasPrefix() { return this.hasPrefix; }
    @Override public void setHasPrefix(@Nullable String hasPrefix) { this.hasPrefix = hasPrefix; }

    private String asAlias = null;
    @Override public String getAsAlias() { return this.asAlias; }
    @Override public void setAsAlias(String asAlias) { this.asAlias = asAlias; }
}
