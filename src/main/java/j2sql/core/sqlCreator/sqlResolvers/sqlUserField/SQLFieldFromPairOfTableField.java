package j2sql.core.sqlCreator.sqlResolvers.sqlUserField;

import j2sql.definitions.design.schema.PairOfTableField;
import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;

public final class SQLFieldFromPairOfTableField extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private PairOfTableField pairOfTableField;
    private SQLFieldFromTable sqlFieldFromTable;

    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField) {
        super();
        init(null, null, pairOfTableField);
    }
    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField, @Nullable String asAlias) {
        super();
        init(null, asAlias, pairOfTableField);
    }
    public SQLFieldFromPairOfTableField(@Nonnull PairOfTableField pairOfTableField, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, pairOfTableField);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        assert args != null;
        this.pairOfTableField = (PairOfTableField) args[0];
        this.sqlFieldFromTable = new SQLFieldFromTable(this.pairOfTableField.getDbf(), asAlias, setPrefix);
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    public GlobalFieldsDefinition.DbF getDbFieldEnum() { return this.pairOfTableField.getDbf(); }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        if (isIgnoreTableAsAlias()) this.sqlFieldFromTable.setIgnoreTableAsAlias();
        return sqlFieldFromTable.getResolveObjectForSQLMain(forSQLRetrieverForDB, this.pairOfTableField.getDbt());
    }
}
