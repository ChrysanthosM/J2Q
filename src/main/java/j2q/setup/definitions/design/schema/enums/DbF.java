package j2q.setup.definitions.design.schema.enums;

import j2q.core.DbFieldValues;
import j2q.core.IDeployFilters;
import j2q.core.IDeployOrdering;
import j2q.core.IProvideDataTypeForSQL;
import j2q.db.definition.GlobalFieldModelDefinition;
import lombok.Getter;

import java.util.List;

import static j2q.commons.CommonMethods.splitCamelCase;
import static j2q.db.definition.GlobalFieldModelDefinition.DbFieldDataType.DATATYPE_INTEGER;
import static j2q.db.definition.GlobalFieldModelDefinition.DbFieldDataType.DATATYPE_TEXT;

@Getter
public enum DbF implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
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
    private final GlobalFieldModelDefinition.DbFieldDataType fieldDataType;
    private final String asAlias;

    DbF(String systemName) {
        this.systemName = systemName;
        this.fieldDataType = null;
        this.asAlias = null;
    }

    DbF(String systemName, GlobalFieldModelDefinition.DbFieldDataType fieldDataType) {
        this.systemName = systemName;
        this.fieldDataType = fieldDataType;
        this.asAlias = splitCamelCase(this.name());
    }

    public List<String> getAcceptedValues() {
        return DbFieldValues.getValues(this);
    }

    @Override
    public GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL() {
        return this.getFieldDataType().getDataTypeForSQL();
    }
}
