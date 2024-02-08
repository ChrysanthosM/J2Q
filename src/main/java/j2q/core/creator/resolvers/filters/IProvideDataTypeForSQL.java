package j2q.core.creator.resolvers.filters;

import j2q.db.definition.GlobalFieldModelDefinition;

public interface IProvideDataTypeForSQL {
    GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL();
}
