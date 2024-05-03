package j2q.core;

import j2q.db.definition.GlobalFieldModelDefinition;

public interface IProvideDataTypeForSQL {
    GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL();
}
