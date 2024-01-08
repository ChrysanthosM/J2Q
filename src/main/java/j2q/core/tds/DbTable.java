package j2q.core.tds;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import j2q.j2sql.J2SQLShared;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class DbTable implements IDbTable {
    public final GlobalFieldsDefinition.DbF ALL = GlobalFieldsDefinition.DbF.ALL;

    @Override public abstract GlobalTablesDefinition.DbT getDbT();
    @Override public abstract String getSystemName();
    @Override public abstract String getTablePrefixForFields();
    @Override public abstract List<GlobalFieldsDefinition.DbF> getHasKeys();

    @Override public abstract List<PairOfTableField> getDbFs();

    @Getter private DbTableInfo dbTableInfo = null;

    @PostConstruct
    public void loadTableInfo() {
        dbTableInfo = new DbTableInfo(this);
    }

    public Pair<DbTable, String> as(@Nonnull J2SQLShared.PFX asAlias) { return as(asAlias.name()); }
    public Pair<DbTable, String> as(@Nonnull String asAlias) { return Pair.of(this, asAlias); }
}
