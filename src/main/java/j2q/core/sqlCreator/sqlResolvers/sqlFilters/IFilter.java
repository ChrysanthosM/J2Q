package j2q.core.sqlCreator.sqlResolvers.sqlFilters;

import j2q.core.linSQL.LinSQL;

public sealed interface IFilter permits AbstractFilter {
    LinSQL.TypeOfLogicalOperator getTypeOfLogicalOperator();
    void setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator typeOfLogicalOperator);
    boolean isInvertSelection();
    void setInvertSelection(boolean invertSelection);

    void addParenthesisLeft();
    void addParenthesisRight();

}
