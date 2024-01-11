package j2q.core.sqlCreator;

import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import j2q.db.model.GlobalFieldModelDefinition;
import j2q.core.tds.DbField;
import j2q.core.tds.DbFieldInstances;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.IDeployFilters;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.IProvideDataTypeForSQL;
import j2q.core.sqlCreator.sqlResolvers.sqlUserField.SQLFieldObject;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public final class PairOfTableField implements IDeployFilters, IProvideDataTypeForSQL {
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
