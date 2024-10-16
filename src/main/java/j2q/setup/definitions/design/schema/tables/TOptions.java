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

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), DbF.RecID);
    public final PairOfTableField OPTION_TYPE = PairOfTableField.of(getDbT(), DbF.OptionType);
    public final PairOfTableField OPTION_NAME = PairOfTableField.of(getDbT(), DbF.OptionName);
    public final PairOfTableField OPTION_VALUE = PairOfTableField.of(getDbT(), DbF.OptionValue);
    public final PairOfTableField OPTION_DETAILS = PairOfTableField.of(getDbT(), DbF.OptionDetails);
    public final PairOfTableField USER_STAMP = PairOfTableField.of(getDbT(), DbF.UserStamp);
    public final PairOfTableField DATE_STAMP = PairOfTableField.of(getDbT(), DbF.DateStamp);
}
