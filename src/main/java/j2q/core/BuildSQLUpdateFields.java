package j2q.core;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

final class BuildSQLUpdateFields extends BuildSQLCore {

    static BuildSQLUpdateFields createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLUpdateFields(forSQLRetrieverForDB); }
    private BuildSQLUpdateFields(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<MutablePair<SqlUserSelection, SqlUserSelection>> updateFieldsSetValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getUpdateFieldsSetValues();
        if (CollectionUtils.isEmpty(updateFieldsSetValues)) return;

        List<Pair<String, String>> updateFieldsSetValueForSQL = Lists.newArrayList();
        for (MutablePair<SqlUserSelection, SqlUserSelection> pair : updateFieldsSetValues) {
            pair.getLeft().setAsAlias(null);
            pair.getLeft().setIgnoreTableAsAlias();
            pair.getRight().setAsAlias(null);
            pair.getRight().setIgnoreTableAsAlias();
            String updFieldForSQL = pair.getLeft().getResolveObjectForSQL(forSQLRetrieverForDB);
            String setValueForSQL = pair.getRight().getResolveObjectForSQL(forSQLRetrieverForDB);
            updateFieldsSetValueForSQL.add(Pair.of(updFieldForSQL, setValueForSQL));
        }
        StringBuilder StringForSQLBuilder = new StringBuilder("SET");
        for (int i = 0; i < updateFieldsSetValueForSQL.size(); i++) {
            StringForSQLBuilder.append(StringUtils.SPACE).append(updateFieldsSetValueForSQL.get(i).getLeft());
            StringForSQLBuilder.append(StringUtils.SPACE).append("=");
            StringForSQLBuilder.append(StringUtils.SPACE).append(updateFieldsSetValueForSQL.get(i).getRight());
            if (i != updateFieldsSetValueForSQL.size() - 1) { StringForSQLBuilder.append(","); }
        }

        super.setStringForSQL(StringForSQLBuilder.toString());
    }
}
