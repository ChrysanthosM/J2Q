package j2sql.definitions.design.schema.enums;

import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.IDeployFilters;
import j2sql.core.sqlCreator.sqlResolvers.sqlFilters.IProvideDataTypeForSQL;

import java.util.List;

import static j2sql.db.model.GlobalFieldModelDefinition.* ;
import static j2sql.db.model.GlobalFieldModelDefinition.DbFieldDataType.* ;
import static j2sql.commons.CommonMethods.splitCamelCase;

public final class GlobalFieldsDefinition {
    public enum DbF implements IDeployFilters, IProvideDataTypeForSQL {
        ALL("*"),

        RecID("Sys_RecID", DATATYPE_INTEGER),
        UserStamp("Sys_UserStamp", DATATYPE_TEXT),
        DateStamp("Sys_DateStamp", DATATYPE_TEXT),

        UserName("Sys_UserName", DATATYPE_TEXT),
        UserPassword("Sys_Password", DATATYPE_TEXT),

        EntityType("Sys_EntityType", DATATYPE_TEXT),
        EntityNumber("Sys_EntityNumber", DATATYPE_INTEGER),

        OptionType("Sys_OptionType", DATATYPE_TEXT),
        OptionName("Sys_OptionName", DATATYPE_TEXT),
        OptionValue("Sys_OptionValue", DATATYPE_TEXT),
        OptionDetails("Sys_OptionDetails", DATATYPE_TEXT),

        ;

        private final String systemName;
        private final DbFieldDataType fieldDataType;
        private final String asAlias;
        DbF(String systemName) {
            this.systemName = systemName;
            this.fieldDataType = null;
            this.asAlias = null;
        }
        DbF(String systemName, DbFieldDataType fieldDataType) {
            this.systemName = systemName;
            this.fieldDataType = fieldDataType;
            this.asAlias = splitCamelCase(this.name());
        }
        public String getSystemName() {
            return this.systemName;
        }
        public DbFieldDataType getFieldDataType() {
            return this.fieldDataType;
        }
        public String getAsAlias() {
            return this.asAlias;
        }

        public List<String> getAcceptedValues() {
            return GlobalFieldValuesDefinition.getValues(this);
        }

        @Override
        public DataTypeForSQL getDataTypeForSQL() {
            return this.getFieldDataType().getDataTypeForSQL();
        }
    }

}
