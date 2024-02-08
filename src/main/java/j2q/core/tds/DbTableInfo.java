package j2q.core.tds;

import j2q.core.creator.PairOfTableField;
import j2q.setup.definitions.design.schema.enums.DbF;
import j2q.setup.definitions.design.schema.enums.DbT;
import j2q.db.definition.GlobalFieldModelDefinition;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public final class DbTableInfo {
    private final DbT dbtNameEnum;

    private final String dbtNormalName;
    private final String dbtSystemName;

    private final List<DbF> dbtHasKeys;
    private final Boolean dbtIsAutoIncrease;
    private final Boolean dbtPutAutoStamp;

    private final String dbtHasFieldsPrefix;
    private final List<DbF> dbtHasDbFieldNamesEnum;
    private final List<DbField> dbtHasDbFields;

    private final List<String> dbtHasFieldsNormalNames;
    private final List<String> dbtHasFieldsSystemNames;
    private final List<String> dbtHasFieldsAsAlias;
    private final List<GlobalFieldModelDefinition.DbFieldDataType> dbtHasFieldsDataType ;
    private final List<GlobalFieldModelDefinition.DataTypeForSQL> dbtHasFieldsDataTypeForSQL;

    private final ImmutableMap<DbF, String> dbtHasFieldsNameEnum_NormalName;
    private final ImmutableMap<DbF, String> dbtHasFieldsNameEnum_SystemName;
    private final ImmutableMap<DbF, String> dbtHasFieldsNameEnum_AsAlias;
    private final ImmutableMap<DbF, GlobalFieldModelDefinition.DbFieldDataType> dbtHasFieldsNameEnum_DataType;
    private final ImmutableMap<DbF, GlobalFieldModelDefinition.DataTypeForSQL> dbtHasFieldsNameEnum_DataTypeForSQL;

    public DbTableInfo(DbTable dbTable) {
        this.dbtNameEnum = dbTable.getDbT();

        this.dbtNormalName = this.dbtNameEnum.name();
        this.dbtSystemName = dbTable.getSystemName();

        this.dbtHasKeys = ImmutableList.copyOf(dbTable.getHasKeys());
        this.dbtIsAutoIncrease = dbTable.getAutoIncrease();
        this.dbtPutAutoStamp = dbTable.getPutAutoStamp();

        this.dbtHasFieldsPrefix = dbTable.getTablePrefixForFields();
        this.dbtHasDbFieldNamesEnum = ImmutableList.copyOf(dbTable.getDbFs().stream().map(PairOfTableField::getDbf).collect(Collectors.toList()));

        this.dbtHasDbFields = ImmutableList.copyOf(this.dbtHasDbFieldNamesEnum.stream().map(DbFieldInstances::getMapTableInstance).collect(Collectors.toList()));
        this.dbtHasFieldsNormalNames = ImmutableList.copyOf(this.dbtHasDbFields.stream().map(DbField::getDbfNormalName).collect(Collectors.toList()));
        this.dbtHasFieldsSystemNames = ImmutableList.copyOf(this.dbtHasDbFields.stream().map(DbField::getDbfSystemName).collect(Collectors.toList()));
        this.dbtHasFieldsAsAlias = ImmutableList.copyOf(this.dbtHasDbFields.stream().map(DbField::getDbfAsAlias).collect(Collectors.toList()));
        this.dbtHasFieldsDataType = ImmutableList.copyOf(this.dbtHasDbFields.stream().map(DbField::getDbfDataType).collect(Collectors.toList()));
        this.dbtHasFieldsDataTypeForSQL = ImmutableList.copyOf(this.dbtHasDbFields.stream().map(DbField::getDbfDataTypeForSQL).collect(Collectors.toList()));

//        for (int i = 0; i < this.dbtHasDbFieldNamesEnum.size(); i++) {
//            this.dbtHasFieldsNameEnum_NormalName.put(this.dbtHasDbFieldNamesEnum.get(i), this.dbtHasFieldsNormalNames.get(i));
//            this.dbtHasFieldsNameEnum_SystemName.put(this.dbtHasDbFieldNamesEnum.get(i), this.dbtHasFieldsSystemNames.get(i));
//            this.dbtHasFieldsNameEnum_AsAlias.put(this.dbtHasDbFieldNamesEnum.get(i), this.dbtHasFieldsAsAlias.get(i));
//            this.dbtHasFieldsNameEnum_DataType.put(this.dbtHasDbFieldNamesEnum.get(i), this.dbtHasFieldsDataType.get(i));
//            this.dbtHasFieldsNameEnum_DataTypeForSQL.put(this.dbtHasDbFieldNamesEnum.get(i), this.dbtHasFieldsDataTypeForSQL.get(i));
//        }
        this.dbtHasFieldsNameEnum_NormalName = ImmutableMap.copyOf((Map<? extends DbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsNormalNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_SystemName = ImmutableMap.copyOf((Map<? extends DbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsSystemNames::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_AsAlias = ImmutableMap.copyOf((Map<? extends DbF, ? extends String>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsAsAlias::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_DataType = ImmutableMap.copyOf((Map<? extends DbF, ? extends GlobalFieldModelDefinition.DbFieldDataType>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsDataType::get, (existing, replacement) -> existing, HashMap::new)));
        this.dbtHasFieldsNameEnum_DataTypeForSQL = ImmutableMap.copyOf((Map<? extends DbF, ? extends GlobalFieldModelDefinition.DataTypeForSQL>)
                IntStream.range(0, this.dbtHasDbFieldNamesEnum.size()).boxed()
                        .collect(Collectors.toMap(this.dbtHasDbFieldNamesEnum::get, this.dbtHasFieldsDataTypeForSQL::get, (existing, replacement) -> existing, HashMap::new)));
    }
}
