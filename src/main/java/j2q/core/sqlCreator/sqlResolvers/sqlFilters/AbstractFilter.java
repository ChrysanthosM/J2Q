package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.linSQL.LinSQL;
import j2q.core.sqlCreator.sqlResolvers.IResolveObjectForSQL;
import j2q.core.sqlRetriever.SQLRetrieverForDBs;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractFilter implements IWhere, IResolveObjectForSQL, IFilter {
    @Override public abstract String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB);

    private LinSQL.TypeOfLogicalOperator typeOfLogicalOperator = null;
    @Override public LinSQL.TypeOfLogicalOperator getTypeOfLogicalOperator() { return this.typeOfLogicalOperator; }
    @Override public void setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator typeOfLogicalOperator) { this.typeOfLogicalOperator = typeOfLogicalOperator; }

    private boolean invertSelection;
    @Override public boolean isInvertSelection() { return this.invertSelection; }
    @Override public void setInvertSelection(boolean invertSelection) { this.invertSelection = invertSelection; }

    @Getter private int parenthesisLeft = 0;
    @Getter private int parenthesisRight = 0;
    @Override public void addParenthesisLeft() { this.parenthesisLeft += 1; }
    @Override public void addParenthesisRight() { this.parenthesisRight += 1; }

    private IWhere attachedFilter = null;
    @Override public IWhere getAttachedFilters() { return this.attachedFilter; }
    public String resolveAttachedFilters(SQLRetrieverForDBs forSQLRetrieverForDB) {
        if (this.attachedFilter == null) return StringUtils.EMPTY;
        return StringUtils.SPACE.concat(((IResolveObjectForSQL) this.attachedFilter).getResolveObjectForSQL(forSQLRetrieverForDB));
    }


    @Override
    public IWhere and(IWhere attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.AND);
        this.attachedFilter = attachFilter;
        return this;
    }
    @Override
    public IWhere or(IWhere attachFilter) {
        ((AbstractFilter) attachFilter).setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator.OR);
        this.attachedFilter = attachFilter;
        return this;
    }

}
