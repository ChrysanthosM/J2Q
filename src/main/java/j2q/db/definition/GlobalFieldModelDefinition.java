package j2q.db.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class GlobalFieldModelDefinition {
    public enum DataTypeForSQL { NUMERIC, TEXT }

    @Getter
    @AllArgsConstructor
    public enum DbFieldDataType {
        DATATYPE_TEXT(DataTypeForSQL.TEXT),
        DATATYPE_INTEGER(DataTypeForSQL.NUMERIC),
        DATATYPE_DOUBLE(DataTypeForSQL.NUMERIC),
        DATATYPE_DATE(DataTypeForSQL.NUMERIC),
        DATATYPE_BOOLEAN(DataTypeForSQL.NUMERIC)
        ;

        private final DataTypeForSQL dataTypeForSQL;
    }
}
