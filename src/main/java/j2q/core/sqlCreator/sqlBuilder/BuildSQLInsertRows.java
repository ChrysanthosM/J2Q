package j2q.core.sqlCreator.sqlBuilder;

import j2q.commons.CommonMethods;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2q.core.sqlCreator.LInSQLBuilderShared;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public final class BuildSQLInsertRows extends BuildSQLCore {
    private String insertIntoFieldsForSQL = null;

    public static BuildSQLInsertRows createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLInsertRows(forSQLRetrieverForDB); }
    private BuildSQLInsertRows(SQLRetrieverForDBs forSQLRetrieverForDB) {
        boolean putAutoStamp = forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getPutAutoStamp();

        final List<GlobalFieldsDefinition.DbF> intoDbFs = Lists.newArrayList();
        if (!forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getIsAutoIncrease()) {
            intoDbFs.addAll(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys());
        }
        forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbTableInfo().getDbtHasDbFields().stream()
                .filter(f -> !forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getHasKeys().contains(f.getDbfNameEnum()))
                .forEach(f -> intoDbFs.add(f.getDbfNameEnum()));
        if (CollectionUtils.isEmpty(intoDbFs)) return;

        final List<List<Object>> insertRowsFieldValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getInsertRowsFieldValues();
        if (CollectionUtils.isEmpty(insertRowsFieldValues)) {
            insertRowsFieldValues.add(Lists.newArrayList(Collections.nCopies(intoDbFs.size(), "?")));
        }
        String[] insertRowsForSQL = new String[insertRowsFieldValues.size()];
        IntStream.range(0, insertRowsForSQL.length).parallel()
                .forEach(i -> insertRowsForSQL[i] = getInsertRowForSQL(forSQLRetrieverForDB, intoDbFs, putAutoStamp, insertRowsFieldValues.get(i)));
        super.setStringForSQL(Joiner.on(", ").join(insertRowsForSQL));

        if (putAutoStamp) {
            intoDbFs.add(GlobalFieldsDefinition.DbF.UserStamp);
            intoDbFs.add(GlobalFieldsDefinition.DbF.DateStamp);
        }

        String tableHasPrefixForFields = StringUtils.defaultString(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getTablePrefixForFields());
        final List<String> insertIntoFieldsForSQL = Lists.newArrayList();
        intoDbFs.forEach(f -> insertIntoFieldsForSQL.add(forSQLRetrieverForDB.isNamingIsNormalized() ? f.name() : tableHasPrefixForFields.concat(f.getSystemName())));
        this.insertIntoFieldsForSQL = CommonMethods.stringsConcat(true, Joiner.on(", ").join(insertIntoFieldsForSQL));
    }

    public String getInsertIntoFieldsForSQL() { return this.insertIntoFieldsForSQL.concat(StringUtils.SPACE); }


    private String getInsertRowForSQL(SQLRetrieverForDBs forSQLRetrieverForDB,
                                      List<GlobalFieldsDefinition.DbF> intoDbF, boolean isAutoStamp, List<Object> rowFieldValues) {
        List<String> fieldValuesForSQL = Lists.newArrayList();
        for (int i = 0; i < intoDbF.size(); i++) {
            fieldValuesForSQL.add(LInSQLBuilderShared.getSqlUserSelection(
                    rowFieldValues.get(i), intoDbF.get(i).getFieldDataType().getDataTypeForSQL()).getResolveObjectForSQL(forSQLRetrieverForDB));
        }

        if (isAutoStamp) {
            fieldValuesForSQL.add(System.getenv("USERNAME"));
            fieldValuesForSQL.add(new Timestamp(System.currentTimeMillis()).toString());
        }
        return CommonMethods.stringsConcat(true, Joiner.on(", ").join(fieldValuesForSQL));
    }
}
