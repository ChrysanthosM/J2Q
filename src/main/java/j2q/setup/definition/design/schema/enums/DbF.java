package j2q.setup.definition.design.schema.enums;

import j2q.core.DbFieldValues;
import j2q.core.IDeployFilters;
import j2q.core.IDeployOrdering;
import j2q.core.IProvideDataTypeForSQL;
import j2q.db.definition.DbFieldDataType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.List;

import static j2q.db.definition.DbFieldDataType.DATATYPE_INTEGER;
import static j2q.db.definition.DbFieldDataType.DATATYPE_TEXT;

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
    DbF(String systemName, DbFieldDataType fieldDataType, String asAlias) {
        this.systemName = systemName;
        this.fieldDataType = fieldDataType;
        this.asAlias = asAlias;
    }

    public List<String> getAcceptedValues() {
        return DbFieldValues.getValues(this);
    }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.getFieldDataType().getInQuotesRequirement();
    }


    private static String splitCamelCase(@Nonnull String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // Insert space before uppercase letters that follow a lowercase letter
            if (i > 0 && Character.isUpperCase(currentChar) && Character.isLowerCase(input.charAt(i - 1))) {
                result.append(StringUtils.SPACE);
            }
            result.append(currentChar);
        }
        return result.toString();
    }
}
