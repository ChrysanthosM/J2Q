package j2q.core;

import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;

import java.util.List;

sealed interface IDbTable permits DbTable {
    DbT getDbT();
//    String getSystemName();
//    String getTablePrefixForFields();
//    List<DbF> getHasKeys();
//    Boolean getAutoIncrease();
//    Boolean getPutAutoStamp();
//
//    List<PairOfTableField> getDbFs();
}
