package j2q.core;

import com.google.common.base.Strings;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

sealed abstract class AbstractWhere extends AbstractFilter
        permits BetweenValuesWhere, ExistsWhere, InSubSelectWhere, InValuesWhere, LikeValueWhere, ValueWhere {
    abstract TypeOfWhere getTypeOfWhere();

    private J2SQLShared.PFX wherePrefix = null;
    private final Object whereObject;
    private Boolean inQuotesRequirement = null;


    protected AbstractWhere(Object whereObject) {
        this.whereObject = whereObject;
        if (this.whereObject != null) {
//            if (this.whereObject instanceof GlobalFieldsDefinition.DbF) this.dataTypeForSQL = ((GlobalFieldsDefinition.DbF) this.whereObject).getFieldDataType().getDataTypeForSQL();
//            if (this.whereObject instanceof PairOfTableField) this.dataTypeForSQL = ((PairOfTableField) this.whereObject).getDbf().getFieldDataType().getDataTypeForSQL();
//            if (this.whereObject instanceof J2SQLShared.SQLFunctionObject) this.dataTypeForSQL = ((SQLFunction) ((J2SQLShared.SQLFunctionObject) this.whereObject).getSqlFunction()).getTypeOfSQLFunction().getDataTypeForSQL();
            if (this.whereObject instanceof IProvideDataTypeForSQL) this.inQuotesRequirement = ((IProvideDataTypeForSQL) this.whereObject).getInQuotesRequirement();
        }
    }

    protected void setWherePrefix(J2SQLShared.PFX wherePrefix) { this.wherePrefix = wherePrefix; }
    protected Object getWhereObject() { return whereObject; }
    protected Boolean getInQuotesRequirement() { return this.inQuotesRequirement; }

    protected String resolveParenthesisLeft() { return (super.getParenthesisLeft() > 0) ? Strings.repeat("(", super.getParenthesisLeft()) : StringUtils.EMPTY; }
    protected String resolveParenthesisRight() { return (super.getParenthesisRight() > 0) ? Strings.repeat(")", super.getParenthesisRight()) : StringUtils.EMPTY; }


    protected String whereObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) { return whereObjectForSQL(forSQLRetrieverForDB, null); }
    protected String whereObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable String putComparisonClause) {
        StringBuilder returnValue = new StringBuilder();
        if (super.getTypeOfLogicalOperator() != null) returnValue.append(super.getTypeOfLogicalOperator().name().concat(StringUtils.SPACE));
        returnValue.append(resolveParenthesisLeft());

        if (this.whereObject != null) {
//            if (this.whereObject instanceof SQLFieldFromTable) ((SQLFieldFromTable) this.whereObject).setIgnoreTableAsAlias();
//            if (this.whereObject instanceof SQLFieldFromPairOfTableField) ((SQLFieldFromPairOfTableField) this.whereObject).setIgnoreTableAsAlias();
            SqlUserSelection userSelection = LInSQLBuilderShared.getSqlUserSelection(this.whereObject);
            userSelection.setIgnoreTableAsAlias();
            returnValue.append(userSelection.getResolveObjectForSQL(forSQLRetrieverForDB)).append(StringUtils.SPACE);
        }
        if (super.isInvertSelection()) returnValue.append("NOT").append(StringUtils.SPACE);
        returnValue.append(StringUtils.defaultIfBlank(getTypeOfWhere().getPutClause(), Strings.nullToEmpty(putComparisonClause)));

        return returnValue.append(StringUtils.SPACE).toString();
    }
}
