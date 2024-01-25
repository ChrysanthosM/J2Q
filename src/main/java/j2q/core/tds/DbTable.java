package j2q.core.tds;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import j2q.core.j2sql.J2SQLShared;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

public abstract non-sealed class DbTable implements IDbTable {
    public final DbF ALL = DbF.ALL;

    @Override public abstract DbT getDbT();
    @Override public abstract String getSystemName();
    @Override public abstract String getTablePrefixForFields();
    @Override public abstract List<DbF> getHasKeys();
    @Override public abstract Boolean getAutoIncrease();
    @Override public abstract Boolean getPutAutoStamp();

    @Override public abstract List<PairOfTableField> getDbFs();

    @Getter private DbTableInfo dbTableInfo = null;

    @PostConstruct
    public void loadTableInfo() {
        dbTableInfo = new DbTableInfo(this);
    }

    public Pair<DbTable, String> as(@Nonnull J2SQLShared.PFX asAlias) { return as(asAlias.name()); }
    public Pair<DbTable, String> as(@Nonnull String asAlias) { return Pair.of(this, asAlias); }
}
