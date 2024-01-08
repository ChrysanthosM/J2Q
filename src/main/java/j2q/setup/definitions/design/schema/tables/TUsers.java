package j2q.setup.definitions.design.schema.tables;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.core.support.TTable;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TUsers extends TTable {
    public TUsers() {
        super(GlobalTablesDefinition.DbT.Users, "Sys_Users", "AC", List.of(GlobalFieldsDefinition.DbF.RecID));
        setDbFs(REC_ID, USER_NAME, USER_PASSWORD);
    }

    public final PairOfTableField REC_ID = getPairOfTableField(GlobalFieldsDefinition.DbF.RecID);
    public final PairOfTableField USER_NAME = getPairOfTableField(GlobalFieldsDefinition.DbF.UserName);
    public final PairOfTableField USER_PASSWORD = getPairOfTableField(GlobalFieldsDefinition.DbF.UserPassword);
}
