package j2sql.definitions.design.schema;

import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.definitions.design.schema.enums.GlobalTablesDefinition;
import j2sql.db.model.GlobalFieldModelDefinition;
import j2sql.core.tds.DbField;
import j2sql.core.tds.DbFieldInstances;
import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.IDeployFilters;
import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.IProvideDataTypeForSQL;
import j2sql.core.sqlCreator.sqlResolvers.sqlUserField.SQLFieldObject;

import javax.annotation.Nonnull;

public class PairOfTableField implements IDeployFilters, IProvideDataTypeForSQL {
    public static PairOfTableField of(GlobalTablesDefinition.DbT dbt, GlobalFieldsDefinition.DbF dbf) { return new PairOfTableField(dbt, dbf); }
    private final GlobalTablesDefinition.DbT dbt;
    private final GlobalFieldsDefinition.DbF dbf;
    private final DbField dbField;

    private PairOfTableField(GlobalTablesDefinition.DbT dbt, GlobalFieldsDefinition.DbF dbf) {
        this.dbt = dbt;
        this.dbf = dbf;
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbf);
    }

    public GlobalTablesDefinition.DbT getDbt() {
        return dbt;
    }
    public GlobalFieldsDefinition.DbF getDbf() {
        return dbf;
    }
    public DbField getDbField() {
        return dbField;
    }


    public SQLFieldObject as(@Nonnull String asAlias) { return new SQLFieldObject(this, asAlias, null); }

    @Override
    public GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL() {
        return this.dbf.getFieldDataType().getDataTypeForSQL();
    }
}
