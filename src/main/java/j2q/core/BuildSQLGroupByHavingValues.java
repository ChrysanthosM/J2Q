package j2q.core;


import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class BuildSQLGroupByHavingValues extends BuildSQLCore {

    static BuildSQLGroupByHavingValues createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLGroupByHavingValues(forSQLRetrieverForDB); }
    private BuildSQLGroupByHavingValues(SQLRetrieverForDBs forSQLRetrieverForDB) {
        MutablePair<List<SqlUserSelection>, List<IWhere>> groupBySelectionsHavingValues = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getGroupBySelectionsHavingValues();
        if (groupBySelectionsHavingValues == null) return;
        List<SqlUserSelection> groupByFields = groupBySelectionsHavingValues.left;
        if (CollectionUtils.isEmpty(groupByFields)) return;

        List<String> groupByList = Lists.newArrayList();
        groupByFields.stream().filter(Objects::nonNull).forEach(g -> {
            g.setIgnoreTableAsAlias();
            groupByList.add(g.getResolveObjectForSQL(forSQLRetrieverForDB));
        });

        if (CollectionUtils.isEmpty(groupByList)) return;
        super.setStringForSQL(groupByList.stream().collect(Collectors.joining(", ", "GROUP BY ", StringUtils.EMPTY)));

        List<IWhere> havingValues = groupBySelectionsHavingValues.right;
        if (CollectionUtils.isEmpty(havingValues)) return;
        List<String> havingValuesForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, havingValues, true);
        if (CollectionUtils.isEmpty(havingValuesForSQL)) return;
        super.setStringForSQL(super.getStringForSQL() + " HAVING " + String.join(", ", havingValuesForSQL));
    }
}
