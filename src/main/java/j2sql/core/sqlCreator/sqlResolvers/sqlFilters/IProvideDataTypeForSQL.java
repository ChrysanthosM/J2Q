package j2sql.core.sqlCreator.sqlResolvers.sqlFilters;

import j2sql.db.model.GlobalFieldModelDefinition;

public interface IProvideDataTypeForSQL {
    GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL();
}
