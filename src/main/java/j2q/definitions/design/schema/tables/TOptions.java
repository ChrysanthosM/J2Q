package j2q.definitions.design.schema.tables;

import j2q.definitions.design.schema.PairOfTableField;
import j2q.definitions.design.schema.enums.GlobalTablesDefinition;
import j2q.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.core.tds.DbTable;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TOptions extends DbTable {
    @Override public GlobalTablesDefinition.DbT getDbT() { return GlobalTablesDefinition.DbT.Options; }
    @Override public String getSystemName() { return "Sys_Options"; }
    @Override public String getTablePrefixForFields() { return "AB"; }
    @Override public List<GlobalFieldsDefinition.DbF> getHasKeys() { return List.of(GlobalFieldsDefinition.DbF.RecID); }
    @Override public Boolean getIsAutoIncrease() {
        return true;
    }
    @Override public Boolean getPutAutoStamp() {
        return true;
    }

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.RecID);
    public final PairOfTableField OPTION_TYPE = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.OptionType);
    public final PairOfTableField OPTION_NAME = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.OptionName);
    public final PairOfTableField OPTION_VALUE = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.OptionValue);
    public final PairOfTableField OPTION_DETAILS = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.OptionDetails);
    public final PairOfTableField USER_STAMP = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.UserStamp);
    public final PairOfTableField DATE_STAMP = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.DateStamp);
    @Override public List<PairOfTableField> getDbFs() {
        return ImmutableList.of(
                REC_ID, OPTION_TYPE, OPTION_NAME, OPTION_VALUE, OPTION_DETAILS);
    }
}
