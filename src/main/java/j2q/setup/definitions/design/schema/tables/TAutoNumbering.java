package j2q.setup.definitions.design.schema.tables;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.core.support.TTable;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import org.springframework.stereotype.Component;

@Component
public class TAutoNumbering extends TTable {
    public TAutoNumbering() {
        super(DbT.AutoNumbering);
        setDbFs(REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), DbF.RecID);
    public final PairOfTableField ENTITY_TYPE = PairOfTableField.of(getDbT(), DbF.EntityType);
    public final PairOfTableField ENTITY_NUMBER = PairOfTableField.of(getDbT(), DbF.EntityNumber);
}
