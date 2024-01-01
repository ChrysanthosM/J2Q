package j2sql.core.tds;

import j2sql.definitions.design.schema.PairOfTableField;
import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.definitions.design.schema.enums.GlobalTablesDefinition;

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
