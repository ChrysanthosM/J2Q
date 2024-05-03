package j2q.core;

import j2q.commons.CommonMethods;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

final class BuildSQLWorkTable extends BuildSQLCore {
    @Getter(AccessLevel.PACKAGE) private final DbTable dbTable;
    @Getter(AccessLevel.PACKAGE) private final String tableAsAlias;

    public static BuildSQLWorkTable createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLWorkTable(forSQLRetrieverForDB); }
    private BuildSQLWorkTable(SQLRetrieverForDBs forSQLRetrieverForDB) {
        this.dbTable = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyDbTable();
        this.tableAsAlias = StringUtils.defaultString(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyAsAlias());

//        final String finalTableName = (StringUtils.isBlank(tableAsAlias)) ? tableAsAlias : tableAsAlias.concat(".");
//        Map<DbF, String> tempWorkTableHasFields = new HashMap<>();
//        if (forSQLRetrieverForDB.getTypeOfNamingSystemOrNormalized() == J2SQL.TypeOfComparison.SYSTEM) {
//            this.dbTable.getDbtHasFieldsNameEnum_SystemName().forEach((k, v) -> tempWorkTableHasFields.put(k, finalTableName.concat(v)));
//        } else {
//            this.dbTable.getDbtHasFieldsNameEnum_NormalName().forEach((k, v) -> tempWorkTableHasFields.put(k, finalTableName.concat(v)));
//        }

        String useTableName = CommonMethods.stringsConcat(false,
                StringUtils.defaultString(forSQLRetrieverForDB.getDbPrefixForTableLocation()),
                (forSQLRetrieverForDB.getTypeOfNamingSystemOrNormalized() == LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM)
                        ? this.dbTable.getDbTableInfo().getDbtSystemName()
                        : this.dbTable.getDbTableInfo().getDbtNormalName());

        super.setStringForSQL(CommonMethods.stringsConcat(false,
                useTableName,
                Strings.isNullOrEmpty(this.tableAsAlias)
                        ? StringUtils.EMPTY
                        : CommonMethods.stringsConcat(false, " AS ", this.tableAsAlias)));
    }
}
