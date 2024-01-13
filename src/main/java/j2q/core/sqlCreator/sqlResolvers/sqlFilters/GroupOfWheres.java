package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.linSQL.LinSQL;
import j2q.core.sqlCreator.sqlBuilder.BuildSQLWhereFilters;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import jdk.jfr.Description;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class GroupOfWheres extends AbstractFilter {
    private final List<IWhere> whereFilters;

    public GroupOfWheres(@Nonnull List<IWhere> whereFilters) {
        this.whereFilters = whereFilters;
    }

    @Override public void addParenthesisLeft() { ((IFilter) this.whereFilters.getFirst()).addParenthesisLeft(); }
    @Override public void addParenthesisRight() { ((IFilter) this.whereFilters.getLast()).addParenthesisRight(); }

    @Override
    public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        addParenthesisLeft();
        addParenthesisRight();
        List<String> whereFiltersForSQL = BuildSQLWhereFilters.getResolveFiltersForSQL(forSQLRetrieverForDB, this.whereFilters, true);
        if (CollectionUtils.isEmpty(whereFiltersForSQL)) return null;

        StringBuilder returnValue = new StringBuilder();
        if (super.getTypeOfLogicalOperator() != null) returnValue.append(super.getTypeOfLogicalOperator().name()).append(StringUtils.SPACE);
        if (super.isInvertSelection()) returnValue.append("NOT").append(StringUtils.SPACE);
        returnValue.append(Joiner.on(StringUtils.SPACE).join(whereFiltersForSQL));
        return returnValue.toString();
    }


    @Description("create Filters (enclosed in Parenthesis)")
    public static IWhere getGroupOfFilters(@Nullable LinSQL.TypeOfLogicalOperator typeOfLogicalOperator, boolean invertSelection, @Nonnull IWhere... filters) {
        List<IWhere> whereList = Lists.newArrayList();
        Stream.of(filters).filter(Objects::nonNull).forEach(whereList::add);
        if (CollectionUtils.isEmpty(whereList)) return null;
        GroupOfWheres wheres = new GroupOfWheres(whereList);
        if (typeOfLogicalOperator != null) wheres.setTypeOfLogicalOperator(typeOfLogicalOperator);
        wheres.setInvertSelection(invertSelection);
        return wheres;
    }
}
