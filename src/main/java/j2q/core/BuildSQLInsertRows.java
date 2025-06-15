package j2q.core;

import j2q.setup.definition.design.schema.enums.DbF;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class BuildSQLInsertRows extends BuildSQLCore {
    private String insertIntoFieldsForSQL = null;

    static BuildSQLInsertRows createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLInsertRows(forSQLRetrieverForDB); }
    private BuildSQLInsertRows(SQLRetrieverForDBs forSQLRetrieverForDB) {
        boolean putAutoStamp = forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getPutAutoStamp();

        final List<DbF> intoDbFs = Lists.newArrayList();
        if (!forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getAutoIncrease()) {
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
            intoDbFs.add(DbF.UserStamp);
            intoDbFs.add(DbF.DateStamp);
        }

        String tableHasPrefixForFields = forSQLRetrieverForDB.getTableMustPrefixFields()
                ? StringUtils.defaultString(forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getTablePrefixForFields())
                : StringUtils.EMPTY;
        final List<String> insertIntoFieldsForSQL = Lists.newArrayList();
        intoDbFs.forEach(f -> insertIntoFieldsForSQL.add(forSQLRetrieverForDB.isNamingIsNormalized() ? f.name() : tableHasPrefixForFields.concat(f.getSystemName())));
        this.insertIntoFieldsForSQL = insertIntoFieldsForSQL.stream().collect(Collectors.joining(", ", "(", ")"));
    }

    public String getInsertIntoFieldsForSQL() { return this.insertIntoFieldsForSQL.concat(StringUtils.SPACE); }


    private String getInsertRowForSQL(SQLRetrieverForDBs forSQLRetrieverForDB,
                                      List<DbF> intoDbF, boolean isAutoStamp, List<Object> rowFieldValues) {
        List<String> fieldValuesForSQL = IntStream.range(0, intoDbF.size())
                .mapToObj(i -> LInSQLBuilderShared.getSqlUserSelection(rowFieldValues.get(i), intoDbF.get(i).getFieldDataType().getInQuotesRequirement())
                        .getResolveObjectForSQL(forSQLRetrieverForDB))
                .collect(Collectors.toList());
        if (isAutoStamp) {
            fieldValuesForSQL.add(System.getenv("USERNAME"));
            fieldValuesForSQL.add(new Timestamp(System.currentTimeMillis()).toString());
        }
        return fieldValuesForSQL.stream().collect(Collectors.joining(", ", "(", ")"));
    }
}
