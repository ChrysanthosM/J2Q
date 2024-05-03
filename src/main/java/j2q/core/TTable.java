package j2q.core;

import com.google.common.collect.ImmutableList;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class TTable extends DbTable {
    private final DbT dbT;
    private final String systemName;
    private final String tablePrefixForFields;
    private final List<DbF> hasKeys;
    private final Boolean autoIncrease;
    private final Boolean putAutoStamp;

    private List<PairOfTableField> dbFs;

    public TTable(DbT dbT) {
        this.dbT = dbT;
        this.systemName = dbT.getSystemName();
        this.tablePrefixForFields = dbT.getTablePrefixForFields();
        this.hasKeys = ImmutableList.copyOf(dbT.getHasKeys());
        this.autoIncrease = dbT.getAutoIncrease();
        this.putAutoStamp = dbT.getPutAutoStamp();
    }

    public void setDbFs(PairOfTableField... dbFs) { this.dbFs = ImmutableList.copyOf(dbFs); }

    public PairOfTableField getPairOfTableField(DbF forDbF) { return PairOfTableField.of(getDbT(), forDbF); }
}
