package j2q.setup.definition.design.schema.db2i.table;

import j2q.core.PairOfTableField;
import j2q.core.TTable;
import j2q.setup.definition.design.schema.db2i.enums.DbF;
import j2q.setup.definition.design.schema.db2i.enums.DbT;
import org.springframework.stereotype.Component;

@Component
public final class TAutoNumbering extends TTable {
    private TAutoNumbering() {
        super(DbT.AUTO_NUMBERING);
        setDbFs(REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField ENTITY_TYPE = getPairOfTableField(DbF.ENTITY_TYPE);
    public final PairOfTableField ENTITY_NUMBER = getPairOfTableField(DbF.ENTITY_NUMBER);
}
