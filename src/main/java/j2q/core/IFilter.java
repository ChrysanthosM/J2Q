package j2q.core;

sealed interface IFilter permits AbstractFilter {
    LinSQL.TypeOfLogicalOperator getTypeOfLogicalOperator();
    void setTypeOfLogicalOperator(LinSQL.TypeOfLogicalOperator typeOfLogicalOperator);
    boolean isInvertSelection();
    void setInvertSelection(boolean invertSelection);

    void addParenthesisLeft();
    void addParenthesisRight();

}
