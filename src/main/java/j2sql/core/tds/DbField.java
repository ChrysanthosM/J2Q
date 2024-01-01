package j2sql.core.tds;

import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.db.model.GlobalFieldModelDefinition;
import lombok.Getter;

import java.util.List;

public final class DbField {
    @Getter private final GlobalFieldsDefinition.DbF dbfNameEnum;

    @Getter private final String dbfNormalName;
    @Getter private final String dbfSystemName;
    @Getter private final String dbfAsAlias;

    @Getter private final GlobalFieldModelDefinition.DbFieldDataType dbfDataType;
    @Getter private final GlobalFieldModelDefinition.DataTypeForSQL dbfDataTypeForSQL;

    @Getter private final List<String> dbfAcceptedValues;

    public DbField(GlobalFieldsDefinition.DbF dbF) {
        this.dbfNameEnum = dbF;

        this.dbfNormalName = this.dbfNameEnum.name();
        this.dbfSystemName = this.dbfNameEnum.getSystemName();
        this.dbfAsAlias = this.dbfNameEnum.getAsAlias();

        this.dbfDataType = this.dbfNameEnum.getFieldDataType();
        this.dbfDataTypeForSQL = this.dbfDataType == null ? null : this.dbfDataType.getDataTypeForSQL();

        this.dbfAcceptedValues = this.dbfNameEnum.getAcceptedValues();
    }
}
