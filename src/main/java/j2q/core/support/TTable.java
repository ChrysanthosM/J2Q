package j2q.core.support;

import com.google.common.collect.ImmutableList;
import j2q.core.sqlCreator.PairOfTableField;
import j2q.core.tds.DbTable;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class TTable extends DbTable {
    private final GlobalTablesDefinition.DbT dbT;
    private final String systemName;
    private final String tablePrefixForFields;
    private final List<GlobalFieldsDefinition.DbF> hasKeys;
    private Boolean autoIncrease = false;
    private Boolean putAutoStamp = false;

    private List<PairOfTableField> dbFs;

    public TTable(GlobalTablesDefinition.DbT dbT, String systemName, String tablePrefixForFields, List<GlobalFieldsDefinition.DbF> hasKeys) {
        this.dbT = dbT;
        this.systemName = systemName;
        this.tablePrefixForFields = tablePrefixForFields;
        this.hasKeys = ImmutableList.copyOf(hasKeys);
    }
    public void setAutoIncrease() {
        this.autoIncrease = true;
    }
    public void setPutAutoStamp() {
        this.putAutoStamp = true;
    }

    public void setDbFs(PairOfTableField... dbFs) { this.dbFs = ImmutableList.copyOf(dbFs); }

    public PairOfTableField getPairOfTableField(GlobalFieldsDefinition.DbF forDbF) { return PairOfTableField.of(getDbT(), forDbF); }
}
