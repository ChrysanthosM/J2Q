package j2q.core.tds;

import j2q.definitions.design.schema.PairOfTableField;
import j2q.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.definitions.design.schema.enums.GlobalTablesDefinition;

import java.util.List;

public interface IDbTable {
    GlobalTablesDefinition.DbT getDbT();
    String getSystemName();
    String getTablePrefixForFields();
    List<GlobalFieldsDefinition.DbF> getHasKeys();
    default Boolean getIsAutoIncrease() { return false; }
    default Boolean getPutAutoStamp() { return false; }

    List<PairOfTableField> getDbFs();
}
