package j2q.setup.definition.design.schema.sqlite.table;

import j2q.core.PairOfTableField;
import j2q.core.TTable;
import j2q.setup.definition.design.schema.sqlite.enums.DbF;
import j2q.setup.definition.design.schema.sqlite.enums.DbT;
import org.springframework.stereotype.Component;

@Component
public final class TOptions extends TTable {
    public TOptions() {
        super(DbT.OPTIONS);
        setDbFs(REC_ID, OPTION_TYPE, OPTION_NAME, OPTION_VALUE, OPTION_DETAILS);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.REC_ID);
    public final PairOfTableField OPTION_TYPE = getPairOfTableField(DbF.OPTION_TYPE);
    public final PairOfTableField OPTION_NAME = getPairOfTableField(DbF.OPTION_NAME);
    public final PairOfTableField OPTION_VALUE = getPairOfTableField(DbF.OPTION_VALUE);
    public final PairOfTableField OPTION_DETAILS = getPairOfTableField(DbF.OPTION_DETAILS);
    public final PairOfTableField USER_STAMP = getPairOfTableField(DbF.USER_STAMP);
    public final PairOfTableField DATE_STAMP = getPairOfTableField(DbF.DATE_STAMP);
}
