package j2q.setup.definitions.design.schema.tables;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import j2q.core.tds.DbTable;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TAutoNumbering extends DbTable {
    @Override public GlobalTablesDefinition.DbT getDbT() { return GlobalTablesDefinition.DbT.AutoNumbering; }
    @Override public String getSystemName() { return "Sys_AutoNumbering"; }
    @Override public String getTablePrefixForFields() { return "AA"; }
    @Override public List<GlobalFieldsDefinition.DbF> getHasKeys() { return List.of(GlobalFieldsDefinition.DbF.RecID); }
    @Override public Boolean getIsAutoIncrease() {
        return true;
    }

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.RecID);
    public final PairOfTableField ENTITY_TYPE = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.EntityType);
    public final PairOfTableField ENTITY_NUMBER = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.EntityNumber);
    @Override public List<PairOfTableField> getDbFs() {
        return ImmutableList.of(
                REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }
}
