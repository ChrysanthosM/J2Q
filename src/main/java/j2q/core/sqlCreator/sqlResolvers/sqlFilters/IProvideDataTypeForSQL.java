package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.db.model.GlobalFieldModelDefinition;

public interface IProvideDataTypeForSQL {
    GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL();
}
