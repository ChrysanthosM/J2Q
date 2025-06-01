package j2q.core;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Optional;

final class SQLFieldFromConstant extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private static final String Q_MARK = "?";
    private Object value;
    private final Optional<Boolean> inQuotesRequirement;

    SQLFieldFromConstant(@Nonnull Object value, @Nullable String asAlias, @Nullable Boolean inQuotesRequirement) {
        init(null, asAlias, value);
        this.inQuotesRequirement = Optional.ofNullable(inQuotesRequirement);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        this.value = args[0];
        if (this.value instanceof IValueFor enumValue) {
            this.value = enumValue.getValue();
        }
        super.setAsAlias(asAlias);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        boolean inQuotes = true;
        if (this.inQuotesRequirement.isPresent()) {
            inQuotes = (this.inQuotesRequirement.get());
        }
        if (this.value.equals(Q_MARK)) inQuotes = false;
        return LinSQLCommons.applyAsAlias(String.valueOf(this.value), super.getAsAlias(), false, inQuotes);
//        return this.value.toString() + asAliasMain(this.asAlias);
    }
}
