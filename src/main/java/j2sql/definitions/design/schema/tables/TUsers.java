package j2sql.definitions.design.schema.tables;

import j2sql.definitions.design.schema.PairOfTableField;
import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.definitions.design.schema.enums.GlobalTablesDefinition;
import j2sql.core.tds.DbTable;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class TUsers extends DbTable {
    @Override public GlobalTablesDefinition.DbT getDbT() { return GlobalTablesDefinition.DbT.Users; }
    @Override public String getSystemName() { return "Sys_Users"; }
    @Override public String getTablePrefixForFields() { return "AC"; }
    @Override public List<GlobalFieldsDefinition.DbF> getHasKeys() { return List.of(GlobalFieldsDefinition.DbF.RecID); }

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.RecID);
    public final PairOfTableField USER_NAME = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.UserName);
    public final PairOfTableField USER_PASSWORD = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.UserPassword);
    @Override public List<PairOfTableField> getDbFs() {
        return ImmutableList.of(
                REC_ID, USER_NAME, USER_PASSWORD);
    }
}
