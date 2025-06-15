package j2q.setup.definition.design.schema.table;

import j2q.core.PairOfTableField;
import j2q.core.TTable;
import j2q.setup.definition.design.schema.enums.DbF;
import j2q.setup.definition.design.schema.enums.DbT;
import org.springframework.stereotype.Component;

@Component
public final class TUsers extends TTable {
    public TUsers() {
        super(DbT.USERS);
        setDbFs(REC_ID, USER_NAME, USER_PASSWORD);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField USER_NAME = getPairOfTableField(DbF.USER_NAME);
    public final PairOfTableField USER_PASSWORD = getPairOfTableField(DbF.USER_PASSWORD);
}
