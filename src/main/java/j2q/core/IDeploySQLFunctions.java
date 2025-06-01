package j2q.core;

import lombok.Getter;

sealed interface IDeploySQLFunctions extends IDefaultsSQLRetrieverForDBs
        permits SQLRetrieverCore {

    @Getter
    enum TypeOfSQLFunction {
        TRIM(true),
        LTRIM(true),
        RTRIM(true),
        UPPER(true),
        LOWER(true),
        INITCAP(true),
        CONCAT(true),
        SUBSTR(true),
        REPLACE(true),
        TRANSLATE(true),
        SPACE(true),
        LPAD(true),
        RPAD(true),
        CASE(true),
        LEFT(true),
        RIGHT(true),
        REPEAT(true),
        INSTR(false),
        LENGTH(false),

        AVG(false),
        COUNT(false),
        COUNT_BIG(false),
        SUM(false),
        MIN(true),
        MAX(true),

        ;

        private final Boolean inQuotesRequirement;
        TypeOfSQLFunction(Boolean inQuotesRequirement) { this.inQuotesRequirement = inQuotesRequirement; }
    }
    static SQLFunction create(TypeOfSQLFunction typeOfSQLFunction, Object... args) {
        switch (typeOfSQLFunction) {
            case MIN -> { return new SQLFunctionAggregates(typeOfSQLFunction, args); }
            case MAX -> { return new SQLFunctionAggregates(typeOfSQLFunction, args); }
            case AVG -> { return new SQLFunctionAggregates(typeOfSQLFunction, args); }
            case SUM -> { return new SQLFunctionAggregates(typeOfSQLFunction, args); }
            case COUNT -> { return new SQLFunctionAggregatesWithPossibleALL(typeOfSQLFunction, args); }
            case COUNT_BIG -> { return new SQLFunctionAggregatesWithPossibleALL(typeOfSQLFunction, args); }

            case TRIM -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case LTRIM -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case RTRIM -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case LENGTH -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case UPPER -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case LOWER -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case INITCAP -> { return new SQLFunction1Param(typeOfSQLFunction, args); }
            case SPACE -> { return new SQLFunction1Param(typeOfSQLFunction, args); }

            case INSTR -> { return new SQLFunction2Params(typeOfSQLFunction, args); }
            case LEFT -> { return new SQLFunction2Params(typeOfSQLFunction, args); }
            case RIGHT -> { return new SQLFunction2Params(typeOfSQLFunction, args); }
            case REPEAT -> { return new SQLFunction2Params(typeOfSQLFunction, args); }

            case REPLACE -> { return new SQLFunction3Params(typeOfSQLFunction, args); }
            case SUBSTR -> { return new SQLFunction3Params(typeOfSQLFunction, args); }
            case LPAD -> { return new SQLFunction3Params(typeOfSQLFunction, args); }
            case RPAD -> { return new SQLFunction3Params(typeOfSQLFunction, args); }

            case CONCAT -> { return new SQLFunctionCONCAT(args); }
            case TRANSLATE -> { return new SQLFunctionTRANSLATE(args); }
            case CASE -> { return new SQLFunctionCASE(args); }

            default -> { return null; }
        }
    }
    default String resolveSQLStringsFunction(SQLFunction sqlFunction) { return sqlFunction.defaultResolver((SQLRetrieverForDBs) this); }
}
