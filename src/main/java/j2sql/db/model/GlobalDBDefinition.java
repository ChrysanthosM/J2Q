package j2sql.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GlobalDBDefinition {
    @Getter
    @AllArgsConstructor
    public enum TypeOfDB {
        DB2_AS400(null, "$.", true),
        MSSQL(null, null, false),
        SQLite(".db", null, false);

        final String attributeExtension;
        final String tablePrefixToReplace;
        final Boolean tableMustPrefixFields;
    }
}
