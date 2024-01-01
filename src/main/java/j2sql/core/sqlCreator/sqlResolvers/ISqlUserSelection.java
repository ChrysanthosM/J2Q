package j2sql.core.sqlCreator.sqlResolvers;

import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.IDeployFilters;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

interface ISqlUserSelection extends IResolveObjectForSQL, IDeployFilters {
    Type getTypeOfSelection();
    void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args);

    String getHasPrefix();
    void setHasPrefix(@Nullable String hasPrefix);

    String getAsAlias();
    void setAsAlias(@Nullable String asAlias);
}
