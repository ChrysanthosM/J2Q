package j2q.setup.definitions.design.schema.tables;

import j2q.core.PairOfTableField;
import j2q.core.TTable;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import org.springframework.stereotype.Component;

@Component
public final class TOptions extends TTable {
    public TOptions() {
        super(DbT.Options);
        setDbFs(REC_ID, OPTION_TYPE, OPTION_NAME, OPTION_VALUE, OPTION_DETAILS);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(DbF.RecID);
    public final PairOfTableField OPTION_TYPE = getPairOfTableField(DbF.OptionType);
    public final PairOfTableField OPTION_NAME = getPairOfTableField(DbF.OptionName);
    public final PairOfTableField OPTION_VALUE = getPairOfTableField(DbF.OptionValue);
    public final PairOfTableField OPTION_DETAILS = getPairOfTableField(DbF.OptionDetails);
    public final PairOfTableField USER_STAMP = getPairOfTableField(DbF.UserStamp);
    public final PairOfTableField DATE_STAMP = getPairOfTableField(DbF.DateStamp);
}
