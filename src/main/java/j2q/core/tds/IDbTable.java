package j2q.core.tds;

import j2q.core.creator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;

import java.util.List;

public sealed interface IDbTable permits DbTable {
    DbT getDbT();
    String getSystemName();
    String getTablePrefixForFields();
    List<DbF> getHasKeys();
    Boolean getAutoIncrease();
    Boolean getPutAutoStamp();

    List<PairOfTableField> getDbFs();
}
