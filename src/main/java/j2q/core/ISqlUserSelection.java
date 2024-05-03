package j2q.core;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

sealed interface ISqlUserSelection extends IResolveObjectForSQL, IDeployFilters
        permits SqlUserSelection {
    Type getTypeOfSelection();
    void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args);

    String getHasPrefix();
    void setHasPrefix(@Nullable String hasPrefix);

    String getAsAlias();
    void setAsAlias(@Nullable String asAlias);
}
