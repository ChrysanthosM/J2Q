package j2q.core.tds;

import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.db.model.GlobalFieldModelDefinition;
import lombok.Getter;

import java.util.List;

@Getter
public final class DbField {
    private final DbF dbfNameEnum;

    private final String dbfNormalName;
    private final String dbfSystemName;
    private final String dbfAsAlias;

    private final GlobalFieldModelDefinition.DbFieldDataType dbfDataType;
    private final GlobalFieldModelDefinition.DataTypeForSQL dbfDataTypeForSQL;

    private final List<String> dbfAcceptedValues;

    public DbField(DbF dbF) {
        this.dbfNameEnum = dbF;

        this.dbfNormalName = this.dbfNameEnum.name();
        this.dbfSystemName = this.dbfNameEnum.getSystemName();
        this.dbfAsAlias = this.dbfNameEnum.getAsAlias();

        this.dbfDataType = this.dbfNameEnum.getFieldDataType();
        this.dbfDataTypeForSQL = this.dbfDataType == null ? null : this.dbfDataType.getDataTypeForSQL();

        this.dbfAcceptedValues = this.dbfNameEnum.getAcceptedValues();
    }
}
