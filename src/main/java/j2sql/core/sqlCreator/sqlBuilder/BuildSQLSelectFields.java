package j2sql.core.sqlCreator.sqlBuilder;

import j2sql.commons.CommonMethods;
import j2sql.core.linSQL.LinSQLCommons;
import j2sql.core.sqlCreator.sqlResolvers.sqlUserField.SQLFieldFromPairOfTableField;
import com.google.common.collect.Lists;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class BuildSQLSelectFields extends BuildSQLCore {
    @Getter(AccessLevel.PACKAGE) private String selectedFieldsString;

    public static BuildSQLSelectFields createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLSelectFields(forSQLRetrieverForDB); }
    private BuildSQLSelectFields(SQLRetrieverForDBs forSQLRetrieverForDB) {
        final List<SqlUserSelection> selectFields = Lists.newArrayList();
        if (CollectionUtils.isEmpty(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getSqlUserSelections())) {
            forSQLRetrieverForDB.getWorkBuildSQLWorkTable().getDbTable().getDbFs().forEach(p -> selectFields.add(new SQLFieldFromPairOfTableField(p)));
        } else {
            selectFields.addAll(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getSqlUserSelections());
        }
        if (CollectionUtils.isEmpty(selectFields)) return;

        List<String> selectedFieldsForSQL = Lists.newArrayList();
        for (SqlUserSelection selectedField : selectFields) {
            String selectedFieldForSQL = selectedField.getResolveObjectForSQL(forSQLRetrieverForDB);
            if (StringUtils.isNotBlank(selectedFieldForSQL)) {
                selectedFieldsForSQL.add(selectedFieldForSQL);
                if (StringUtils.isNotBlank(selectedField.getAsAlias())) {
                    forSQLRetrieverForDB.addFieldMapper(LinSQLCommons.fixAsAliasName(selectedField.getAsAlias()));
                }
            }
        }

        this.selectedFieldsString = StringUtils.join(selectedFieldsForSQL, ", ");
        super.setStringForSQL(CommonMethods.stringsConcat(false, "SELECT ", (forSQLRetrieverForDB.getWorkLInSQLBuilderParams().isSelectDistinct() ? "DISTINCT " : StringUtils.EMPTY), this.selectedFieldsString));
    }
}
