package j2q.core;

import j2q.db.definition.GlobalFieldModelDefinition;
import lombok.Getter;

sealed interface IDeploySQLFunctions extends IDefaultsSQLRetrieverForDBs
        permits SQLRetrieverCore {
    @Getter
    enum TypeOfSQLFunction {
        TRIM(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        LTRIM(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        RTRIM(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        UPPER(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        LOWER(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        INITCAP(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        CONCAT(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        SUBSTR(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        REPLACE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        TRANSLATE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        SPACE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        LPAD(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        RPAD(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        CASE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        LEFT(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        RIGHT(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        REPEAT(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        INSTR(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),
        LENGTH(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),

        AVG(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),
        COUNT(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),
        COUNT_BIG(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),
        SUM(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC),
        MIN(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),
        MAX(GlobalFieldModelDefinition.DataTypeForSQL.TEXT),

        ;

        private final GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL;
        TypeOfSQLFunction(GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL) { this.dataTypeForSQL = dataTypeForSQL; }
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
