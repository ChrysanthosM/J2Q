package j2q.core;

import j2q.setup.definition.design.schema.enums.DbF;
import j2q.setup.definition.design.schema.enums.DbT;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

@Getter(AccessLevel.PROTECTED)
abstract sealed class DbTable implements IDbTable permits TTable {
    public final DbF ALL = DbF.ALL;

    protected abstract DbT getDbT();
    protected abstract String getSystemName();
    protected abstract String getTablePrefixForFields();
    protected abstract List<DbF> getHasKeys();
    protected abstract Boolean getAutoIncrease();
    protected abstract Boolean getPutAutoStamp();

    protected abstract List<PairOfTableField> getDbFs();

    private DbTableInfo dbTableInfo = null;

    @PostConstruct
    private void loadTableInfo() {
        dbTableInfo = new DbTableInfo(this);
    }

    public Pair<DbTable, String> as(@Nonnull J2SQLShared.PFX asAlias) { return as(asAlias.name()); }
    public Pair<DbTable, String> as(@Nonnull String asAlias) { return Pair.of(this, asAlias); }
}
