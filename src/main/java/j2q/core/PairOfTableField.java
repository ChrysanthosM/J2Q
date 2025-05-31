package j2q.core;

import j2q.setup.definition.design.schema.enums.DbF;
import j2q.setup.definition.design.schema.enums.DbT;
import j2q.db.definition.GlobalFieldModelDefinition;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(DbT dbt, DbF dbf) { return new PairOfTableField(dbt, dbf); }

    private final DbT dbt;
    private final DbF dbf;
    private final DbField dbField;
    private PairOfTableField(DbT dbt, DbF dbf) {
        this.dbt = dbt;
        this.dbf = dbf;
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbf);
    }

    public SQLFieldObject as(@Nonnull String asAlias) { return new SQLFieldObject(this, asAlias, null); }

    @Override
    public GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL() {
        return this.dbf.getFieldDataType().getDataTypeForSQL();
    }
}
