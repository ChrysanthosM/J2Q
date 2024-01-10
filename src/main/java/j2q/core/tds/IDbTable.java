package j2q.core.tds;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;

import java.util.List;

public sealed interface IDbTable permits DbTable {
    GlobalTablesDefinition.DbT getDbT();
    String getSystemName();
    String getTablePrefixForFields();
    List<GlobalFieldsDefinition.DbF> getHasKeys();
    Boolean getAutoIncrease();
    Boolean getPutAutoStamp();

    List<PairOfTableField> getDbFs();
}
