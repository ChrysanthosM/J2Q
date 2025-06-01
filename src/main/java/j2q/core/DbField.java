package j2q.core;

import j2q.db.definition.DbFieldDataType;
import j2q.setup.definition.design.schema.enums.DbF;
import lombok.Getter;

import java.util.List;

@Getter
final class DbField {
    private final DbF dbfNameEnum;

    private final String dbfNormalName;
    private final String dbfSystemName;
    private final String dbfAsAlias;

    private final DbFieldDataType dbfDataType;
    private final Boolean dbfInQuotesRequirement;

    private final List<String> dbfAcceptedValues;

    DbField(DbF dbF) {
        this.dbfNameEnum = dbF;

        this.dbfNormalName = this.dbfNameEnum.name();
        this.dbfSystemName = this.dbfNameEnum.getSystemName();
        this.dbfAsAlias = this.dbfNameEnum.getAsAlias();

        this.dbfDataType = this.dbfNameEnum.getFieldDataType();
        this.dbfInQuotesRequirement = this.dbfDataType == null ? null : this.dbfDataType.getInQuotesRequirement();

        this.dbfAcceptedValues = this.dbfNameEnum.getAcceptedValues();
    }
}
