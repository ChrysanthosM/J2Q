package j2q.setup.definitions.design.schema.tables;

import j2q.core.sqlCreator.PairOfTableField;
import j2q.core.support.TTable;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2q.setup.definitions.design.schema.enums.GlobalTablesDefinition;
import j2q.core.tds.DbTable;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TAutoNumbering extends TTable {
    public TAutoNumbering() {
        super(GlobalTablesDefinition.DbT.AutoNumbering, "Sys_AutoNumbering", "AA", List.of(GlobalFieldsDefinition.DbF.RecID));
        setAutoIncrease();
        setDbFs(REC_ID, ENTITY_TYPE, ENTITY_NUMBER);
    }

    public final PairOfTableField REC_ID = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.RecID);
    public final PairOfTableField ENTITY_TYPE = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.EntityType);
    public final PairOfTableField ENTITY_NUMBER = PairOfTableField.of(getDbT(), GlobalFieldsDefinition.DbF.EntityNumber);
}
