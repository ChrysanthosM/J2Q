package j2q.core.sqlCreator.sqlBuilder;

import j2q.commons.CommonMethods;
import j2q.core.linSQL.LinSQL;
import j2q.core.sqlCreator.sqlResolvers.IResolveObjectForSQL;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.IFilter;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.IWhere;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public final class BuildSQLWhereFilters extends BuildSQLCore {

    public static BuildSQLWhereFilters createFor(SQLRetrieverForDBs forSQLRetrieverForDB) { return new BuildSQLWhereFilters(forSQLRetrieverForDB); }
    private BuildSQLWhereFilters(SQLRetrieverForDBs forSQLRetrieverForDB) {
        List<IWhere> whereFilters = forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWhereClauses().stream().dropWhile(Objects::isNull).toList();
        if (CollectionUtils.isEmpty(whereFilters)) return;

        List<String> whereFiltersForSQL = getResolveFiltersForSQL(forSQLRetrieverForDB, whereFilters, true);
        if (CollectionUtils.isNotEmpty(whereFiltersForSQL)) super.setStringForSQL(CommonMethods.stringsConcat(false, Joiner.on(StringUtils.SPACE).join(whereFiltersForSQL)));
    }

    public static String getWhereFilters(String basicFilters) { return getWhereFiltersWithJoins(basicFilters, null); }
    public static String getWhereFiltersWithJoins(String basicFilters, @Nullable String joinFilters) {
        if (StringUtils.isBlank(basicFilters) && StringUtils.isBlank(joinFilters)) return StringUtils.EMPTY;
        StringBuilder builder = new StringBuilder("WHERE ");
        if (StringUtils.isBlank(basicFilters)) {
            builder.append(joinFilters);
        } else {
            builder.append(basicFilters);
            if (StringUtils.isNotBlank(joinFilters)) builder.append("AND (").append(joinFilters.trim()).append(")");
        }
        return builder.toString();
    }


    public static String getResolveFilterForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, IWhere whereFilter, boolean resetFirstOperator) { return getResolveFiltersForSQL(forSQLRetrieverForDB, List.of(whereFilter), resetFirstOperator).get(0); }
    public static List<String> getResolveFiltersForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, List<IWhere> whereFilters, boolean resetFirstOperator) {
        whereFilters.stream().filter(w -> ((IFilter) w).getTypeOfLogicalOperator() == null).forEach(w -> ((IFilter) w).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.AND));
        if (resetFirstOperator) ((IFilter) whereFilters.get(0)).setTypeOfLogicalOperator(null);

        List<String> whereFiltersForSQL = Lists.newArrayList();
        for (IWhere where : whereFilters) {
            String whereForSQL = ((IResolveObjectForSQL) where).getResolveObjectForSQL(forSQLRetrieverForDB);
            if (StringUtils.isNotBlank(whereForSQL)) {
                whereFiltersForSQL.add(whereForSQL);
            }
        }
        return whereFiltersForSQL;
    }

}
