package j2q.core;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

final class BuildSQLOrderBy extends BuildSQLCore {

    static BuildSQLOrderBy createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLOrderBy(forSQLRetrieverForDB); }
    private BuildSQLOrderBy(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<MutablePair<SqlUserSelection, SortOrder>> orderByFields = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) return;

        List<String> orderByList = Lists.newArrayList();
        for (MutablePair<SqlUserSelection, SortOrder> pair : orderByFields) {
            if (pair.left != null) {
                pair.left.setIgnoreTableAsAlias();
                String selectedFieldForSQL = pair.left.getResolveObjectForSQL(forSQLRetrieverForDB);
                if (!Strings.isNullOrEmpty(selectedFieldForSQL)) {
                    if (pair.right != null) {
                        selectedFieldForSQL = selectedFieldForSQL.concat(StringUtils.SPACE)
                                .concat(forSQLRetrieverForDB.getOrderByType().get(pair.right));
                    }
                    orderByList.add(selectedFieldForSQL);
                }
            }
        }
        super.setStringForSQL("ORDER BY " + String.join(", ", orderByList));
    }
}
