package j2q.core.creator.sqlbuilder;


import j2q.commons.CommonMethods;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2q.core.creator.resolvers.filters.IWhere;
import j2q.core.creator.resolvers.SqlUserSelection;
import j2q.core.retrievers.SQLRetrieverForDBs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;
import java.util.Objects;

public final class BuildSQLGroupByHavingValues extends BuildSQLCore {

    public static BuildSQLGroupByHavingValues createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLGroupByHavingValues(forSQLRetrieverForDB); }
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
        super.setStringForSQL(CommonMethods.stringsConcat(false, "GROUP BY ", Joiner.on(", ").join(groupByList)));

        List<IWhere> havingValues = groupBySelectionsHavingValues.right;
        if (CollectionUtils.isEmpty(havingValues)) return;
        List<String> havingValuesForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, havingValues, true);
        if (CollectionUtils.isEmpty(havingValuesForSQL)) return;
        super.setStringForSQL(CommonMethods.stringsConcat(false, super.getStringForSQL(), " HAVING ", Joiner.on(", ").join(havingValuesForSQL)));
    }
}
