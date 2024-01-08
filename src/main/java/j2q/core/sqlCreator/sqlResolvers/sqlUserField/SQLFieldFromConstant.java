package j2q.core.sqlCreator.sqlResolvers.sqlUserField;

import j2q.core.linSQL.LinSQLCommons;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import j2q.setup.definitions.design.schema.enums.GlobalFieldValuesDefinition;
import j2q.db.model.GlobalFieldModelDefinition;
import com.google.common.base.Preconditions;
import j2q.core.sqlCreator.sqlResolvers.SqlUserSelection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Optional;

public final class SQLFieldFromConstant extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private static final String Q_MARK = "?";
    private Object value;
    private final Optional<GlobalFieldModelDefinition.DataTypeForSQL> dataTypeForSQL;

    public SQLFieldFromConstant(@Nonnull Object value, @Nullable String asAlias, @Nonnull GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) {
        init(null, asAlias, value);
        this.dataTypeForSQL = Optional.ofNullable(dataTypeForSQL);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        Preconditions.checkNotNull(args);
        this.value = args[0];
        if (this.value instanceof GlobalFieldValuesDefinition.IValueFor enumValue) {
            this.value = enumValue.getValue();
        }
        super.setAsAlias(asAlias);
    }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        boolean inQuotes = true;
        if (this.dataTypeForSQL.isPresent()) {
            inQuotes = (this.dataTypeForSQL.get() == GlobalFieldModelDefinition.DataTypeForSQL.TEXT);
        }
        if (this.value.equals(Q_MARK)) inQuotes = false;
        return LinSQLCommons.applyAsAlias(String.valueOf(this.value), super.getAsAlias(), false, inQuotes);
//        return this.value.toString() + asAliasMain(this.asAlias);
    }
}
